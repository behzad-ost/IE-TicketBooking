package service;

import common.Pair;
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
        reservations = new ArrayList<Reservation>();
    }

    public String makeReservation (ClientReserveQuery crq) throws IOException {
        Reservation newReserve = new Reservation(crq);

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
//        System.out.println("response: " + helperResponse);
        String response = helperResponse.split("\\s+")[0];
        newReserve.parseHelperResponse(helperResponse, crq.adults, crq.childs, crq.infants);
        response += " " + newReserve.getTotalPrice() +"\n";
        this.reservations.add(newReserve);
        this.reservations.get(0).printReservation();
        System.out.println("num of reserves: " + reservations.size());
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
        for(int j = 0 ; j < flight.getPrices().size() ; j++ ){
            double tPrice = adults * Double.parseDouble(flight.getPrices().get(j).getSecond()[0]) +
                            childs * Double.parseDouble(flight.getPrices().get(j).getSecond()[1]) +
                            infants * Double.parseDouble(flight.getPrices().get(j).getSecond()[2]);
            res+= "Class: " + flight.getPrices().get(j).getFirst() + " Price: " + tPrice + "\n";
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
        ArrayList<Pair<String,Integer>> classes = newFlight.getClasses();
        for(int i = 0 ; i < classes.size() ; i++){
            String requestToHelper = "PRICE ";
            requestToHelper += newFlight.getOrigin() + " " +
                    newFlight.getDestination() + " " + newFlight.getCode() + " " +
                    classes.get(i).getFirst()+ "\n";
            String response = transceive(requestToHelper);
            newFlight.addPrice(classes.get(i).getFirst(), response);
        }
    }
}
