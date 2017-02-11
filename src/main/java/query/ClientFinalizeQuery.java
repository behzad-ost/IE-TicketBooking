package query;

/**
 * Created by ali on 2/10/17.
 */
public class ClientFinalizeQuery {
    private String token;

    public ClientFinalizeQuery(String[] commandArgs) {
        token = commandArgs[1];
    }

    public String getToken() {
        return token;
    }
}
