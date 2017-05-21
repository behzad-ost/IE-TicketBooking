package service.common;

/**
 * Created by behzad on 5/21/17.
 */
public class TicketInfo {

    private String personId;
    private String refCode;
    private String number;

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {

        return personId;
    }

    public String getRefCode() {
        return refCode;
    }

    public String getNumber() {
        return number;
    }
}
