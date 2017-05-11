package service.searchManagement;

import service.common.FlightInfo;

import java.util.ArrayList;

/**
 * Created by ali on 5/2/17.
 */
public class SearchResult {
    private boolean success;
    private String errorMessage;
    private int numOfFlights;
    private ArrayList<FlightInfo> flights;

    public SearchResult() {
        success = true;
        errorMessage = "";
        this.flights = new ArrayList<>();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getNumOfFlights() {
        return numOfFlights;
    }

    public void setNumOfFlights(int numOfFlights) {
        this.numOfFlights = numOfFlights;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<FlightInfo> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<FlightInfo> flights) {
        this.flights = flights;
    }
}
