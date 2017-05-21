package service.authentication;

/**
 * Created by behzad on 5/19/17.
 */
public class AuthResponse {
    private String token;
    private String status;

    public String getStatus(){
        return status;
    }

    public void setStatus(String _status){
        status = _status;
    }

    public String getToken(){
        return token;
    }
    public void setToken(String _token){
        token = _token;
    }

}
