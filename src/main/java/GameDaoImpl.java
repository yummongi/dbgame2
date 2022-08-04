import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class GameDaoImpl implements GameDao {
    static ResultSet rs = null;
    static PreparedStatement pstmt = null;
    static Connection conn = null;

    static Statement stmt = null;

    public static Connection dbConn() {
        final String driver = "org.mariadb.jdbc.Driver";
        final String DB_IP = "localhost";
        final String DB_PORT = "3306";
        final String DB_NAME = "dbdb";
        final String DB_URL =
                "jdbc:mariadb://" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(DB_URL, "root", "1234");
            if (conn != null) {
                System.out.println("DB 접속 성공");
            }

        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로드 실패");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }
        return conn;
    }

    public static void dbClose(){
        try {
            //쿼리 닫기
            if (rs != null) {
                rs.close();
            }
            //데이터 닫기
            if (pstmt != null) {
                pstmt.close();
            }
            //sql 닫기
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(GameDto dto) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            String sql = "INSERT INTO `game` (`user_id`, `user_pw`, `user_name`) VALUES (?, ?, ?)";
            conn = dbConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dto.getUserId());
            pstmt.setString(2, dto.getUserPw());
            pstmt.setString(3, dto.getName());


            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("데이터 넣기 실패");
            }else {
                System.out.println("데이터 넣기 성공");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            dbClose();
        }
    }

    @Override
    public GameDto findIdPw(String id, String pw) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        conn = dbConn();
        GameDto dto = null;
        try {
            String sql = "SELECT * FROM `game` WHERE user_id = ? AND user_pw = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            rs = pstmt.executeQuery();

            if (rs.next()) {   // 만약에 rs.next()에 값이 있으면
                dto = new GameDto();
                dto.setId(rs.getInt("num"));
                dto.setUserId(rs.getString("user_id"));
                dto.setUserPw(rs.getString("user_pw"));
                dto.setName(rs.getString("user_name"));
                dto.setMarble(rs.getInt("marble"));
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            dbClose();
        }
        return dto;
    }

    @Override
    public List<GameDto> findAll() {

        return null;
    }

    @Override
    public void update(int num, int marble) {
        try {
            String sql = "UPDATE `game` SET `marble`= ?, update_at = NOW() WHERE  `num`= ?";
            conn = dbConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, marble);
            pstmt.setInt(2, num);

            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("데이터 넣기 실패");
            }else {
                System.out.println("데이터 넣기 성공");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            dbClose();
        }
    }

    @Override
    public void delete(int num) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            String sql = "DELETE FROM `game` WHERE  `num`=?;";
            conn = dbConn();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);


            int result = pstmt.executeUpdate();
            if(result == 0){
                System.out.println("데이터 삭제 실패");
            }else {
                System.out.println(num+ "번 데이터 삭제 성공");
            }
        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            dbClose();
        }
    }
}
