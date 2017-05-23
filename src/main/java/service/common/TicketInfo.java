package service.common;

/**
 * Created by behzad on 5/21/17.
 */
public class TicketInfo {

    private String tokenId;
    private String refId;

    public TicketInfo(String tokenId, String refId) {
        this.tokenId = tokenId;
        this.refId = refId;
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
}
