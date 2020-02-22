package ahmet.com.pwl.Model;

public class Users {

    private String name, username, password, userType, userId;

    public Users() {
    }

    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Users(String name, String username, String password, String userType) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
