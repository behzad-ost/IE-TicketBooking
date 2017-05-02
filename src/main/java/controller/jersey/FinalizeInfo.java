package controller.jersey;

import common.Person;

import java.util.ArrayList;

/**
 * Created by ali on 5/2/17.
 */
public class FinalizeInfo extends ReserveInfo {
    private ArrayList<Person> people;

    public FinalizeInfo() {
        this.people = new ArrayList<>();
    }

    public ArrayList<Person> getPeople() {
        return people;
    }

    public void setPeople(ArrayList<Person> people) {
        this.people = people;
    }
}
