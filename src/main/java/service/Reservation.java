package service;

import common.Person;
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
    public int totalPrice;
    public boolean validity;

    public String getOriginCode() {
        return originCode;
    }

    public String getDestCode() {
        return destCode;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

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
        this.totalPrice = 0;
    }

    public void verify() {
        this.verified = true;
    }

    public void setToken(String t) {
        this.token = t;
    }

    public void parseHelperResponse(String helperResponse, int adults, int childs, int infants) {
        String[] args = helperResponse.split("\\s+");
        setToken(args[0]);
        this.totalPrice = Integer.parseInt(args[1]) * adults + Integer.parseInt(args[2]) * childs + Integer.parseInt(args[3]) * infants;
    }

    public void printReservation() {
        System.out.println(this.originCode + " -> " + this.destCode +
                " | Airline: " + airlineCode + " flight: " + flightNumber  + " class: " + this.seatClass +  "\n" +
                "totalPrice: " + this.totalPrice + "\n");
        for (Person p: people) {
            System.out.println("name: " + p.getFirstName() + "\n" +
                    "family name: " + p.getSurName() + "\n" +
                    "national id: " + p.getNationalId());
        }
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public String getToken() {
        return token;
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setClass(String seatClass) {
        this.seatClass = seatClass;
    }
}
