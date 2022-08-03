import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("헬로 오징어 게임");
        GameDao dao = new GameDaoImpl();
        // 회원가입 -> 저장
        Scanner sc = new Scanner(System.in);
        System.out.print("아이디입력: ");
        String userId = sc.next();
        System.out.print("패스워드입력: ");
        String userPw = sc.next();
        System.out.print("이름입력: ");
        String name = sc.next();

        GameDto dto = new GameDto();
        dto.setUserId(userId);
        dto.setUserPw(userPw);
        dto.setName(name);

        dao.save(dto);
        // 로그인 -> 아이디 패스워드 찾기
        // 구슬의 정보를 수정 -> 업데이트
        // 유저를 삭제 -> 삭제
    }
}
