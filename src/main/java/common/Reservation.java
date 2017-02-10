package common;

import query.ClientReserveQuery;

import java.util.ArrayList;

/**
 * Created by behzad on 2/10/17.
 */
public class Reservation{
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

    public boolean validity;

    public Reservation(ClientReserveQuery crq){
        this.originCode = crq.originCode;
        this.destCode = crq.destCode;
        this.airlineCode = crq.airlineCode;
        this.flightNumber = crq.flightNumber;
        this.seatClass = crq.seatClass;
        this.adults = crq.adults;
        this.childs = crq.childs;
        this.infants= crq.infants;
        this.people = crq.people;
        this.validity = true;
    }

    public void verify() {
        this.verified = true;
    }

    public void setToken(String t) {
        this.token = t;
    }


}
