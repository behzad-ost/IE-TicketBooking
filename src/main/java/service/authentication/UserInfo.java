package service.authentication;

/**
 * Created by behzad on 5/23/17.
 */
public class UserInfo {
    private String id;
    private String role;

    public UserInfo(String id, String role) {
        this.id = id;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
