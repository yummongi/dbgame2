public class GameDto {
    private int num;
    private String userId;
    private String userPw;
    private String name;
    private int marble;

    public int getId() {
        return num;
    }

    public void setId(int num) {
        this.num = num;
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

    public int getMarble() {
        return marble;
    }

    public void setMarble(int marble) {
        this.marble = marble;
    }
}
