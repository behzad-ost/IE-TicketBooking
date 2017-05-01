package provider;

import common.Transceiver;
import query.ClientReserveQuery;
import service.Flight;
import service.Reservation;
import service.Ticket;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by behzad on 3/5/17.
 */
public class Provider {
    private Transceiver transceiver;
    public Provider() throws IOException {
        transceiver = new Transceiver("178.62.207.47", 8081);
    }
    public ArrayList<Flight> getFlights(String originCode, String destCode, String date) throws IOException {
        String requestToHelper = "AV ";
        requestToHelper += originCode + " " + destCode + " " + date + "\n";

        String helperResponse = transceive(requestToHelper);
        System.out.println(helperResponse);

        String[] responseLines = helperResponse.split("\\n");
        ArrayList<Flight> flights = parseFlights(responseLines);

        return flights;
    }
    private ArrayList<Flight> parseFlights(String[] lines) throws IOException {
        ArrayList<Flight> flights = new ArrayList<>();
        for(int i = 0 ; i < lines.length-1 ; i=i+2){
            Flight newFlight = new Flight(lines[i], lines[i+1]);
            setPrices(newFlight);
            flights.add(newFlight);
        }
        return flights;
    }
    private void setPrices(Flight newFlight) throws IOException {
        ArrayList<Flight.Seat> seats = newFlight.getSeats();
        for(int i = 0 ; i < seats.size() ; i++){
            String requestToHelper = "PRICE ";
            requestToHelper += newFlight.getOrigin() + " " +
                    newFlight.getDestination() + " " + newFlight.getAirlineCode() + " " +
                    seats.get(i).getClassName()+ "\n";
            String response = transceive(requestToHelper);
            newFlight.addPrice(seats.get(i), response);
        }
    }

    public Reservation makeReservation(ClientReserveQuery crq) throws IOException {
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

        String helperResponse;
        helperResponse = transceiver.receive();
        newReserve.parseHelperResponse(helperResponse, crq.adults, crq.childs, crq.infants);
        return newReserve;
    }
    public String transceive(String data) throws IOException {
        String res;
        transceiver.send(data);
        res = transceiver.receive();
        return res;
    }

    public String[] getTickets(Reservation reservation) throws IOException {
        String requestToHelper = "FIN ";
        requestToHelper += reservation.getToken() + "\n";
        String response = transceive(requestToHelper);
//        FileWriter fw = new FileWriter("/home/behzad/outیبلات.txt");
//        fw.append(response);
//        fw.close();
        String[] args = response.split("\\n");

        return args;
    }
}
