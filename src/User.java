import java.util.Objects;

public class User {
    //initialized variable called size
    private String username;
    //initialized variable called size
    private String password;

    //argumentative constructor passing username,password
    public User( String username,String password) {
        this.username = username;
        this.password = password;
    }
    //getter for User
    public String getUsername() {
        return username;
    }

    //getter for Password
    public String getPassword() {
        return password;
    }


    //setter for UserName
    public void setUsername(String username) {
        this.username = username;
    }

    //setter for Password
    public void setPassword(String password) {
        this.password = password;
    }


}
