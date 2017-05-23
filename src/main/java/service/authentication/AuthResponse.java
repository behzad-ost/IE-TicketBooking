package service.authentication;

/**
 * Created by behzad on 5/19/17.
 */
public class AuthResponse {
    private String token;
    private String status;

    public AuthResponse() {
        this.token = "";
        this.status = "";
    }

    public String getToken() {
        return token;
    }

    public String getStatus() {
        return status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
