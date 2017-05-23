package service.common;

/**
 * Created by behzad on 5/21/17.
 */
public class TicketInfo {

    private String tokenId;
    private String refId;
    private String personId;

    public TicketInfo(String tokenId, String refId, String personId) {
        this.tokenId = tokenId;
        this.refId = refId;
        this.personId = personId;
    }

    public String getTokenId() {
        return tokenId;
    }

    public String getRefId() {
        return refId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}
