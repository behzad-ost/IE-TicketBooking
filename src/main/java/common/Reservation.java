package common;

import java.util.ArrayList;

/**
 * Created by behzad on 2/10/17.
 */
public class Reservation {
    private String originCode;
    private String destCode;
    private String airlineCode;
    private String flightNumber;
    private String seatClass;
    private int adults;
    private int childs;
    private int infants;
    private ArrayList<Person> people;

    private String token;
    private boolean verified;

    public void verify() {
        this.verified = true;
    }


}
