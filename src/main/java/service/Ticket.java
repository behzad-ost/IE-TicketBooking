package service;

/**
 * Created by behzad on 2/12/17.
 */
public class Ticket {
    private String firstName;
    private String lastName;
    private String refCode;
    private String number;

    public Ticket(String refCode, String number, String firstName, String lastName){
        this.refCode = refCode;
        this.number = number;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getRefCode() {
        return refCode;
    }

    public String getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
