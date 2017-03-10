package service;

import org.apache.log4j.Logger;
import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import query.ClientSearchQuery;

import java.io.IOException;
import java.util.ArrayList;

public class Manager {
    private provider.Provider provider;
    private FlightRepo flightRepo;
    private ReserveRepo reserveRepo;
    private static Manager manager = new Manager();

    final static Logger logger = Logger.getLogger(Manager.class);

    private Manager() {
        try {
            provider = new provider.Provider();
        } catch (IOException e) {
            e.printStackTrace();
        }
        flightRepo = new FlightRepo();
        reserveRepo = new ReserveRepo();
    }
    public static Manager getInstance() {
        return manager;
    }

    public String search (ClientSearchQuery csq) throws IOException {
        ArrayList<Flight> flights;
        /*  {BULLSHIT caching}
            if(FlightRepo has the flight)
                only update prices and availables
                else ask provider
        */

        flights = provider.getFlights(csq.originCode, csq.destCode, csq.date);
        flightRepo.setFlights(flights);
        String response = "";
        for(int i = 0 ; i < flights.size() ; i++){
            response += "Flight: " + flights.get(i).getAirlineCode() + " " + flights.get(i).getNumber() + " " +
                    "Departure: " + flights.get(i).getdTime() + " Arrival: " + flights.get(i).getaTime()+ " "+
                    "Airplane: "+ flights.get(i).getPlaneModel() + "\n";
            response+= calcPrices(flights.get(i), csq.adults, csq.childs , csq.infants);
            if(i != flights.size()-1)
                response += "#\n";
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

    public String makeReservation (ClientReserveQuery crq) throws IOException {
//        ArrayList flights = provider.getFlights(crq.originCode, crq.destCode, crq.date);
//        flightRepo.setFlights(flights);
        Reservation newReserve ;
        newReserve = provider.makeReservation(crq);
        String response;
        response = newReserve.getToken() + " " + newReserve.getTotalPrice() +"\n";
        reserveRepo.addReservation((newReserve));
        return response;
    }

    public String finalizeReservation(ClientFinalizeQuery cfq) throws IOException {
        String[] tickets ;
        String response ;
        Reservation reservation = reserveRepo.findByToken(cfq.token);
        reservation.verify();
        tickets = provider.getTickets(reservation);
        response = printTickets(reservation,tickets);
        return response;
    }

    public String[] getTimeAndModel(String number, String origin, String dest){
        return flightRepo.findFlightTimes(number, origin ,dest);
    }
    private String printTickets(Reservation reservation, String[] args) {
        String res="";
        String[] DAM = flightRepo.findFlightTimes(reservation.getFlightNumber(), reservation.getOriginCode() ,reservation.getDestCode());
        for(int i = 1 ; i < args.length ; i++){

            String firstName =reservation.getPeople().get(i-1).getFirstName();
            String lastName = reservation.getPeople().get(i-1).getSurName();

            Ticket newTicket = new Ticket(args[0],args[i],firstName, lastName);
            reservation.addTicket(newTicket);
            res += firstName + " " + lastName + " "+ newTicket.getRefCode()+
                    " " + newTicket.getNumber() + " " + reservation.getOriginCode() + " " + reservation.getDestCode() + " " +
                    reservation.getAirlineCode() + " " + reservation.getFlightNumber() + " " +
                    reservation.getSeatClass() + " " + DAM[0] + " " + DAM[1] + " " + DAM[2] + "\n";
        }
        return res;
    }

    public ArrayList<Flight> getFlights() {
        return flightRepo.getFlights();
    }

    public Flight searchFlight(String number, String origin, String dest, String date) {
        return  flightRepo.searchFlight(number, origin, dest, date);
    }

    public ArrayList<Ticket> getTicketsOfReserve(Reservation reservation) {
        return reserveRepo.getTicketsOfReserve(reservation);
    }

    public Reservation findReserveByToken(String token) {
        return reserveRepo.findByToken(token);
    }
}
