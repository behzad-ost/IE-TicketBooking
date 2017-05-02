package controller;

/**
 * Created by ali on 5/2/17.
 */
public class SearchAllInfo {
    String src;
    String dest;
    String startDate;
    String endDate;
    int numOfInfants;
    int numOfChildren;
    int numOfAdults;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

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
