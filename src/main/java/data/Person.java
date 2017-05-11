package data;

/**
 * Created by behzad on 2/10/17.
 */
public class Person {

    private String firstName;
    private String surName;
    private String nationalId;
    private String ageType;

    public Person() {}

    public Person(String firstName, String surName, String nationalId, String ageType) {
        this.firstName = firstName;
        this.surName = surName;
        this.nationalId = nationalId;
        this.ageType = ageType;
    }

    public String getFirstName(){ return this.firstName; }
    public String getSurName(){ return this.surName; }
    public String getNationalId(){ return this.nationalId; }
    public String getAgeType() {
        return ageType;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }
}
