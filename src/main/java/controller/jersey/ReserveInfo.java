package controller.jersey;

/**
 * Created by ali on 5/2/17.
 */
public class ReserveInfo extends FlightInfo {
    private int numOfInfants;
    private int numOfChildren;
    private int numOfAdults;

    public int getNumOfInfants() {
        return numOfInfants;
    }

    public void setNumOfInfants(int numOfInfants) {
        this.numOfInfants = numOfInfants;
    }

    public int getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(int numOfChildren) {
        this.numOfChildren = numOfChildren;
    }

    public int getNumOfAdults() {
        return numOfAdults;
    }

    public void setNumOfAdults(int numOfAdults) {
        this.numOfAdults = numOfAdults;
    }
}
