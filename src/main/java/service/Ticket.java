package service;

/**
 * Created by behzad on 2/12/17.
 */
public class Ticket {
    private String refCode;
    private String number;
    private Reservation reservation;

    public Ticket(String refCode, String number, Reservation reservation){
        this.refCode = refCode;
        this.number = number;
        this.reservation = reservation;
    };

    public String getRefCode() {
        return refCode;
    }

    public String getNumber() {
        return number;
    }
}
