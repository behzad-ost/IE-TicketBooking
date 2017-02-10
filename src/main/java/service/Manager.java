package service;

import common.Flight;
import common.Reservation;
import common.Transceiver;
import query.ClientReserveQuery;
import query.ClientSearchQuery;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by behzad on 2/10/17.
 */
public class Manager {
    private Transceiver transceiver;
    private ArrayList<Reservation> reservations;
    private ArrayList<Flight> flights;

    public Manager() throws IOException {
        transceiver = new Transceiver("188.166.78.119", 8081);
        flights = new ArrayList<Flight>();
        reservations = new ArrayList<Reservation>();
    }

    public void makeReservation (ClientReserveQuery crq) throws IOException {
        Reservation tmp = new Reservation(crq);
        String requestToHelper = "RES " + crq.originCode + " " + crq.destCode + " " +
                crq.date + " " + crq.airlineCode +
                " " + crq.flightNumber + " " + crq.seatClass +
                " " + crq.adults + " " + crq.childs + " " + crq.infants + "\n";
        transceiver.send(requestToHelper);

        for(int i = 0 ; i < crq.people.size() ; i++) {
            requestToHelper = "";
            requestToHelper += crq.people.get(i).getFirstName()+" "
                            +crq.people.get(i).getSurName()+" "
                            +crq.people.get(i).getNationalId()+"\n";
            transceiver.send(requestToHelper);
        }

        String helperResponse = transceiver.receive();
        System.out.println("response: " + helperResponse);
        reservations.add(tmp);
    }

    public void search (ClientSearchQuery csq) throws IOException {
        String requestToHelper = "AV ";
        requestToHelper += csq.originCode + " " + csq.destCode + " " + csq.date + "\n";
        transceiver.send(requestToHelper);
        String helperResponse = transceiver.receive();
        System.out.println(helperResponse);

        String[] lines = helperResponse.split("\\n");
        setFlights(lines);
        System.out.println(flights);
    }

    private void setFlights(String[] lines) {
        for(int i = 0 ; i < lines.length-1 ; i=i+2){
            Flight newFlight = new Flight(lines[i], lines[i+1]);
            flights.add(newFlight);
        }
    }
}
