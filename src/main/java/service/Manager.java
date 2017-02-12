package service;

import common.Person;
import common.Transceiver;
import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import query.ClientSearchQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by behzad on 2/10/17.
 */
public class Manager {
    private Transceiver transceiver;
    private ArrayList<Reservation> reservations;
    private ArrayList<Flight> flights;
    private int ticketNumber;

    public Manager() throws IOException {
        transceiver = new Transceiver("188.166.78.119", 8081);
        reservations = new ArrayList<Reservation>();
        ticketNumber = 1;
    }

    public String makeReservation (ClientReserveQuery crq) throws IOException {
        Reservation newReserve = new Reservation(crq);

        String requestToHelper = "RES " + crq.originCode + " " + crq.destCode + " " +
                crq.date + " " + crq.airlineCode +
                " " + crq.flightNumber + " " + crq.seatClass +
                " " + crq.adults + " " + crq.childs + " " + crq.infants + "\n";
        newReserve.setClass(crq.seatClass);
        transceiver.send(requestToHelper);

        for(int i = 0 ; i < crq.people.size(); i++) {
            requestToHelper = "";
            requestToHelper += crq.people.get(i).getFirstName()+" "
                            +crq.people.get(i).getSurName()+" "
                            +crq.people.get(i).getNationalId()+"\n";
            transceiver.send(requestToHelper);
        }

        String helperResponse = transceiver.receive();
//        System.out.println("response: " + helperResponse);
        String token = helperResponse.split("\\s+")[0];
        String response = token ;
        newReserve.setToken(token);
        newReserve.parseHelperResponse(helperResponse, crq.adults, crq.childs, crq.infants);
        response += " " + newReserve.getTotalPrice() +"\n";

        this.reservations.add(newReserve);
        this.reservations.get(0).printReservation();
//        System.out.println("num of reserves: " + reservations.size());
        return response;
    }

    public String transceive(String data) throws IOException {
        String res;
        transceiver.send(data);
        res = transceiver.receive();
        return res;
    }

    public String search (ClientSearchQuery csq) throws IOException {
        String requestToHelper = "AV ";
        requestToHelper += csq.originCode + " " + csq.destCode + " " + csq.date + "\n";

        String helperResponse = transceive(requestToHelper);
        System.out.println(helperResponse);

        String[] lines = helperResponse.split("\\n");
        flights = new ArrayList<Flight>();
        setFlights(lines);

        String response = "";
        for(int i = 0 ; i < flights.size() ; i++){
            response += "Flight: " + flights.get(i).getCode() + " " + flights.get(i).getNumber() + " " +
                    "Departure: " + flights.get(i).getdTime() + " Arrival: " + flights.get(i).getaTime()+ " "+
                    "Airplane: "+ flights.get(i).getPlaneModel() + "\n";
            response+= calcPrices(flights.get(i), csq.adults, csq.childs , csq.infants);
            if(i != flights.size()-1)
                response += "***\n";
        }
        return response;

    }

    private String calcPrices(Flight flight, int adults, int childs, int infants) {
        String res = "";
        for(int j = 0 ; j < flight.getSeats().size() ; j++ ){
            int tPrice = adults * flight.getSeats().get(j).getAdultPrice() +
                            childs * flight.getSeats().get(j).getChildPrice() +
                            infants * flight.getSeats().get(j).getInfantPrice();
            res+= "Class: " + flight.getSeats().get(j).getClassName() + " Price: " + tPrice + "\n";
        }
        return res;
    }

    private void setFlights(String[] lines) throws IOException {
        for(int i = 0 ; i < lines.length-1 ; i=i+2){
            Flight newFlight = new Flight(lines[i], lines[i+1]);
            setPrices(newFlight);
            flights.add(newFlight);
        }
    }

    private void setPrices(Flight newFlight) throws IOException {
        ArrayList<Flight.Seat> seats = newFlight.getSeats();
        for(int i = 0 ; i < seats.size() ; i++){
            String requestToHelper = "PRICE ";
            requestToHelper += newFlight.getOrigin() + " " +
                    newFlight.getDestination() + " " + newFlight.getCode() + " " +
                    seats.get(i).getClassName()+ "\n";
            String response = transceive(requestToHelper);
            newFlight.addPrice(seats.get(i), response);
        }
    }

    public String finalizeReservation(ClientFinalizeQuery cfq){
        String response = "";
        for(int i = 0 ; i < reservations.size() ; i++){
            if(Objects.equals(reservations.get(i).getToken(), cfq.getToken())){
                reservations.get(i).verify();
                response += getTickets(reservations.get(i));
            }
        }

        return response;
    }


    private String getTickets(Reservation reservation) {
        String res = "";
        ArrayList<Person> people = reservation.getPeople();
        String[] DAM = findPlane(reservation.getFlightNumber(), reservation.getOriginCode() ,reservation.getDestCode());
        for(int i = 0 ; i < people.size() ; i++){
            res += people.get(i).getFirstName() + " " + people.get(i).getSurName() + " " + people.get(i).getAgeType() + " " + "XXXX " +
                    ticketNumber + " " + reservation.getOriginCode() + " " + reservation.getDestCode() + " " +
                    reservation.getAirlineCode() + " " + reservation.getFlightNumber() + " " +
                    reservation.getSeatClass() + " " + DAM[0] + " " + DAM[1] + " " + DAM[2] + "\n";
            ticketNumber++;
        }
        return res;
    }

    private String[] findPlane(String flightNumber, String originCode, String destCode) {
        String[] res = new String[3];
        for(int i = 0 ; i < flights.size() ; i++){
            if(Objects.equals(flights.get(i).getNumber(), flightNumber) &&
               Objects.equals(flights.get(i).getOrigin(), originCode) &&
               Objects.equals(flights.get(i).getDestination(), destCode)
              ){
                res[0] = flights.get(i).getdTime();
                res[1] = flights.get(i).getaTime();
                res[2] = flights.get(i).getPlaneModel();
            }
        }
        return res;
    }
}
