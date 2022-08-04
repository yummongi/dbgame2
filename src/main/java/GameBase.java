import java.util.Scanner;

public class GameBase {
    public int mylife, yourlife; //보유 구슬
    private int myball, yourball; //배팅 구슬
    private String myanswer, answer;

    GameDao dao = new GameDaoImpl();
    GameDto dto = new GameDto();

    //생성자
    GameBase() {
        this.mylife = 10;
        this.yourlife = 10;
        this.myball = 0;
        this.yourball = 0;
        this.myanswer = "";
        this.answer = "";
    }



    //인트로 메세지
    private void intro() {
        System.out.println("오징어 게임에 오신것을 환영합니다.");
        System.out.println("이번 게임은 구슬 게임입니다.");
        System.out.println("당신과 나는 각각 10개의 구슬을 가지고 있습니다.");
        System.out.println("10개의 구슬을 모두 잃으면 죽습니다.");
        System.out.println("시작합니다.");
        System.out.println("배팅 하세요");
        System.out.println("=======================================");
    }

    //승리, 패배 시 구슬 설정
    private void setBall() {
        if (myanswer.equals(answer)) {
            mylife += myball;
            yourlife -= myball;
            dto.setMarble(mylife);
            System.out.println("승리하였습니다.");
        } else if (!(myanswer.equals(answer))) {
            yourlife += yourball;
            mylife -= yourball;
            System.out.println("상대방한테 졌습니다.");
            dto.setMarble(mylife);
        } else {
            System.out.println("비겼습니다.");
            dto.setMarble(mylife);
        }
    }

    public void isSave(){


        System.out.print("저장하시겠습니까? (예 : y / 아니오: n) : ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();

        if(answer.equalsIgnoreCase("y")){
            dao.update(dto.getId(), mylife);
            System.out.println(mylife + "개를 저장하였습니다.");
        }else{
            dao.delete(dto.getId());
        }

    }

    //승리 시 메세지
    private void victory() {
        if (mylife <= 0) {
            System.out.println("=======================================");
            System.out.println("나의 구슬을 모두 잃었습니다.");
            System.out.println("상대방 승리!");
        } else {
            System.out.println("=======================================");
            System.out.println("상대방 구슬을 모두 잃었습니다.");
            System.out.println("내가 승리!");
        }
    }

    //구슬 설정
    public void setLife() {
        mylife = dto.getMarble();
        System.out.println("구슬 동기화: " + mylife);
    }

    public void login() {

        String id = null;
        String pw = null;

        Scanner scanner = new Scanner(System.in);

        System.out.println("오징어 게임에 오신 것을 환영합니다.");
        System.out.print("아이디를 가지고 계신가요? y/n: ");
        answer = scanner.next(); //아이디가 맞다면


        if (answer.equalsIgnoreCase("y")) { //y라면
            System.out.print("아이디 입력: ");
            id = scanner.next();
            System.out.print("패스워드 입력: ");
            pw = scanner.next();

            dto = dao.findIdPw(id, pw);


            if (id.equalsIgnoreCase(dto.getUserId())) { //아이디가 맞다면
                System.out.println("아이디가 있습니다. 성공");
                if (id.equalsIgnoreCase(dto.getUserId()) && pw.equalsIgnoreCase(dto.getUserPw())) {
                    System.out.println("로그인 성공!");
                    System.out.println("==================");
                    System.out.println("게임 고유 번호: " + dto.getId());
                    System.out.println("당신의 아이디: " + dto.getUserId());
                    System.out.println("당신의 패스워드: " + dto.getUserPw());
                    System.out.println("당신의 이름: " + dto.getName());
                    System.out.println("당신의 구슬 : " + dto.getMarble());
                    setLife();
                    gameStart();

                } else {
                    System.out.println("비밀번호가 틀렸습니다!");
                }
            } else { //회원가입
                System.out.println("아이디가 없습니다. 회원가입으로 이동합니다.");
                register();
            }
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("아이디가 없으므로, 회원가입으로 이동합니다.");
            register();
        } else {
            System.out.println("로그인 실패!");
        }

    }

    public void register() {

        GameDto rdto = new GameDto();
        Scanner scanner = new Scanner(System.in);
        System.out.print("가입할 아이디를 입력하세요.");
        rdto.setUserId(scanner.next());
        System.out.print("가입할 비밀번호를 입력하세요.");
        rdto.setUserPw(scanner.next());
        System.out.print("가입할 이름을 입력하세요.");
        rdto.setName(scanner.next());

        dao.save(rdto);
        System.out.println("회원가입 완료");
        login();
    }

    //게임 시작
    public void gameStart() {
        Scanner scanner = new Scanner(System.in);
        intro();
        try {

            do {
                do {
                    System.out.print("자신이 베팅할 구슬 갯수를 입력하세요: (1~10 입력)");
                    myball = scanner.nextInt();
                } while (myball < 1 || myball > mylife); //do

                System.out.println("=======================================");
                System.out.println("시작합니다.");

                yourball = (int) (Math.random() * 10) + 1; // 1 ~ 10 까지

                System.out.print("상대방의 구슬을 홀, 짝을 입력하여 추리하세요.");
                myanswer = scanner.next();

                if (myanswer.equals("홀") || myanswer.equals("짝")) {

                    if (yourball % 2 == 0)
                        answer = "짝";
                    else
                        answer = "홀";

                    System.out.println("=======================================");
                    System.out.println("내가 낸 구슬 갯수 : " + myball);
                    System.out.println("상대가 낸 구슬 갯수 : " + yourball);
                    System.out.println("=======================================");

                    setBall();

                    System.out.println("=======================================");
                    System.out.println("현재 나의 구슬 갯수: " + mylife);
                    System.out.println("현재 상대방 구슬 갯수: " + yourlife);
                } else {
                    System.out.println("홀 또는 짝만 입력해주세요.");
                }

            } while (mylife > 0 && yourlife > 0); //do

            victory();
            isSave();

        } catch (Exception e) {
            System.out.println("값을 똑바로 입력해주세요.");
        }
    }
}