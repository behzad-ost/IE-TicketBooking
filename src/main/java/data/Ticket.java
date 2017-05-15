package data;

/**
 * Created by behzad on 2/12/17.
 */
public class Ticket {
    private Person person;
    private String refCode;
    private String number;

    public Ticket(String refCode, String number, Person person){
        this.refCode = refCode;
        this.number = number;
        this.person = person;
    }

    public String getRefCode() {
        return refCode;
    }

    public String getNumber() {
        return number;
    }

    public Person getPerson() {
        return person;
    }
}
