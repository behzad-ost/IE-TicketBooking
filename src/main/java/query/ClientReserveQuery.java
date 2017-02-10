package query;

import common.Person;

import java.util.ArrayList;

/**
 * Created by ali on 2/10/17.
 */
public class ClientReserveQuery {

    public String originCode;
    public String destCode;
    public String airlineCode;
    public String flightNumber;
    public String seatClass;
    public int adults;
    public int childs;
    public int infants;
    public ArrayList<Person> people;

    public ClientReserveQuery(String[] args) {
        this.originCode = args[1];
        this.destCode = args[2];
        this.airlineCode = args[3];
        this.flightNumber = args[4];
        this.seatClass = args[5];
        this.adults = Integer.parseInt(args[6]);
        this.childs = Integer.parseInt(args[7]);
        this.infants = Integer.parseInt(args[8]);
        this.people = new ArrayList<>();
    }


    public void addPerson(String command) {
        String[] person = command.split("\\s+");
        for (int j = 0; j < person.length; j++) {
            System.out.println(person[j]);
        }
        Person newPerson = new Person(person[0], person[1], person[2]);
        this.people.add(newPerson);
    }

    public int getNumOfPeople() {
        return adults + childs + infants;
    }
}
