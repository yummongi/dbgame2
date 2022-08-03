import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {

        GameDaoImpl.login();

        GameDao dao = new GameDaoImpl();

        GameDto dto = new GameDto();


        //dao.save(dto);

    }
}
