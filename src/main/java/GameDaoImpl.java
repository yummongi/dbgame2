import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GameDaoImpl implements GameDao {

    public Connection dbConn() {
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
    @Override
    public void save(GameDto dto) {
        PreparedStatement pstmt = null;
        Connection conn = null;
        try {
            String sql = "INSERT INTO `game` (`userid`, `userpw`, `name`) VALUES (?, ?, ?)";
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
            try {
                if (pstmt != null) {
                    pstmt.close();
                }

                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public GameDto findIdPw(String userId, String userPw) {
        return null;
    }

    @Override
    public void update(int id, int gusl) {

    }

    @Override
    public void delete(int id) {

    }
}
