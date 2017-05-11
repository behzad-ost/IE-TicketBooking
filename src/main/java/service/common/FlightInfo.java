package service.common;

/**
 * Created by ali on 5/2/17.
 */
public class FlightInfo {
    private String airlineCode;
    private String flightNumber;
    private String date;
    private String origin;
    private String dest;
    private String departureTime;
    private String arrivalTime;
    private String planeModel;
    private int totalPrice;
    private String seatClassName;

    public String getSeatClassName() {
        return seatClassName;
    }

    public void setSeatClassName(String seatClassName) {
        this.seatClassName = seatClassName;
    }

    public int getNumOfAvailableSeats() {
        return numOfAvailableSeats;
    }

    public void setNumOfAvailableSeats(int numOfAvailableSeats) {
        this.numOfAvailableSeats = numOfAvailableSeats;
    }

    private int numOfAvailableSeats;


    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPlaneModel() {
        return planeModel;
    }

    public void setPlaneModel(String planeModel) {
        this.planeModel = planeModel;
    }

//    public ArrayList<Flight.Seat> getSeats() {
//        return seats;
//    }
//
//    public void setSeats(ArrayList<Flight.Seat> seats) {
//        this.seats = seats;
//    }
}
