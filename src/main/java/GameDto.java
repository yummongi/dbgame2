public class GameDto {
    private int id;
    private String userId;
    private String userPw;
    private String name;
    private int gusl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPw() {
        return userPw;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGusl() {
        return gusl;
    }

    public void setGusl(int gusl) {
        this.gusl = gusl;
    }
}
