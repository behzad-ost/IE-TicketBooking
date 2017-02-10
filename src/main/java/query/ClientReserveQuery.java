package query;

import java.util.ArrayList;

/**
 * Created by ali on 2/10/17.
 */
public class ClientReserveQuery {
    private class Person{
        protected String firstName;
        protected String sureName;
        protected String nationalId;
    }

    private String originCode;
    private String destCode;
    private String airlineCode;
    private String flightNumber;
    private String seatClass;
    private int adults;
    private int childs;
    private int infants;
    private ArrayList<Person> people;
}
