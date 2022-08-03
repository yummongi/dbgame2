import java.sql.*;
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

    //로그인
    public static void login() {
        //DB 변수
        String num = null;
        String user_id = null;
        String user_pw = null;
        String user_name = null;
        int marble = 0;
        String create_at = null;
        String update_at = null;

        Scanner scanner = new Scanner(System.in);
        //Scanner 입력 변수
        String answer = null;
        String name = null;
        String pw , id;

        GameDto dto = null;

        Connection conn = dbConn();

        try {
            System.out.print("아이디 입력: ");
            id = scanner.next();
            System.out.print("패스워드 입력: ");
            pw = scanner.next();

            System.out.println("오징어 게임에 오신 것을 환영합니다.");
            System.out.print("아이디를 가지고 계신가요? y/n: ");
            answer = scanner.next(); //아이디가 맞다면

            if (answer.equalsIgnoreCase("y")) { //y라면


            String sql = "SELECT user_name, user_id, user_pw, update_at FROM `game` WHERE user_id = ? AND user_pw = ?;";
            stmt = conn.createStatement();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, pw);

            rs = pstmt.executeQuery();


                if (rs.next()) {   // 만약에 rs.next()에 값이 있으면
                    user_id = rs.getString("user_id");
                    user_pw = rs.getString("user_pw");
                    user_name = rs.getString("user_name");
                    update_at = rs.getString("update_at");
                    dto = new GameDto();
                    dto.setUserId(user_id);
                    dto.setUserPw(user_pw);
                    dto.setName(user_name);
                }

                if (id.equalsIgnoreCase(user_id)) { //아이디가 맞다면
                    System.out.println("아이디가 있습니다. 성공");
                    if (id.equalsIgnoreCase(dto.getUserId()) && pw.equalsIgnoreCase(dto.getUserPw())) {
                        System.out.println("로그인 성공!");
                        System.out.println("==================");
                        System.out.println("당신의 아이디: " + dto.getUserId());
                        System.out.println("당신의 패스워드: " + dto.getUserPw());
                        System.out.println("당신의 이름: " + dto.getName());
                        System.out.println("마지막 로그인 기록: " + update_at);

                        stmt = conn.createStatement();
                        stmt.execute("UPDATE `game` SET update_at = NOW() WHERE `user_id`= '" + user_id + "';");

                        System.out.println("로그인 기록이 업데이트 되었습니다!!");
                    } else {
                        System.out.println("비밀번호가 틀렸습니다!");
                    }
                } else { //회원가입
                    System.out.println("아이디가 없습니다. 회원가입으로 이동합니다.");
                    register();
                }
            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println("아이디가 없으므로, 회원가입으로 이동합니다.");
            } else {
                System.out.println("로그인 실패!");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally { //데이터 베이스를 연결했으면 항상 닫아야함 (안하면 풀 현상)
            dbClose();
        }
    }
    public static void register() {
        dbConn();
        Connection conn = dbConn();
        GameDto dto = null;

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("가입할 아이디를 입력하세요.");
            String user_id = scanner.next();
            System.out.print("가입할 비밀번호를 입력하세요.");
            String user_pw = scanner.next();
            System.out.print("가입할 이름을 입력하세요.");
            String user_name = scanner.next();

            // Insert문 ?부분은 아래의 입력값이 자동으로 변환이 됩니다.
            String sql = "INSERT INTO `game` (`user_id`, `user_pw`, `user_name`) VALUES (?, ?, ?)";

            //쿼리 준비
            pstmt = conn.prepareStatement(sql);

            // Insert 데이터값
            int index = 1;

            //메소드의 매게변수에 입력 받은 데이터를 넣으면 된다.
            pstmt.setString(index++, user_id);
            pstmt.setString(index++, user_pw);
            pstmt.setString(index++, user_name);

            dto = new GameDto();

            dto.setUserPw(user_id);
            dto.setUserPw(user_pw);
            dto.setUserPw(user_name);

            //SQL 실행
            int result = pstmt.executeUpdate();
            if (result == 0) {
                System.out.println("회원가입에 실패하였습니다.");
            } else {
                System.out.println("회원가입에 성공하였습니다.");
                System.out.println("자동으로 로그인합니다.");
                login();
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally { //데이터 베이스를 연결했으면 항상 닫아야함 (안하면 풀 현상)
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

//            Scanner scanner = new Scanner(System.in);
//            System.out.println("아이디를 입력하세요");
//            id  = scanner.next();
//            System.out.println("비밀번호를 입력하세요");
//            pw = scanner.next();


            String user_pw = null;
            String user_id = null;
            if (rs.next()) {   // 만약에 rs.next()에 값이 있으면
                user_pw = rs.getString("user_pw");
                user_id = rs.getString("user_id");
                dto = new GameDto();
                dto.setUserId(user_id);
                dto.setUserPw(user_pw);
            }
            if(id.equalsIgnoreCase(user_id) && pw.equalsIgnoreCase(user_pw)){
                System.out.println("아이디:"  + dto.getUserId());
                System.out.println("비밀번호:" + dto.getUserPw());
                System.out.println("해당 아이디 비밀번호 맞음");
            }else{
                System.out.println("로그인 실패");
            }

        } catch (SQLException e) {
            System.out.println("error: " + e);
        } finally {
            dbClose();
        }
        return dto;
    }

    @Override
    public void update(int num, int marble) {
        PreparedStatement pstmt = null;
        Connection conn = null;
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
                GameDto dto = new GameDto();
                dto.setMarble(marble);
                System.out.println(dto.getMarble() + "개 구슬 데이터 넣기 성공");
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
