package common;

/**
 * Created by behzad on 2/10/17.
 */
public class Person {

    private String firstName;
    private String surName;
    private String nationalId;

    public Person(String firstName, String surName, String nationalId) {
        this.firstName = firstName;
        this.surName = surName;
        this.nationalId = nationalId;
    }
    public String getFirstName(){ return this.firstName; }
    public String getSurName(){ return this.surName; }
    public String getNationalId(){ return this.nationalId; }
}
