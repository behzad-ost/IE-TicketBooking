package service;

import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import query.ClientSearchQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class Manager {
    private provider.Provider provider;
    private FlightRepo flightRepo;
    private ReserveRepo reserveRepo;
    private ArrayList<Reservation> reservations;
    private ArrayList<Flight> flights;
    private ArrayList<Ticket> tickets;
    private static Manager manager = new Manager();

    private Manager() {
        try {
            provider = new provider.Provider();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reservations = new ArrayList<>();
        tickets = new ArrayList<>();
        flightRepo = new FlightRepo();
        reserveRepo = new ReserveRepo();
    }
    public static Manager getInstance() {
        return manager;
    }
    public String search (ClientSearchQuery csq) throws IOException {
        flights = provider.getFlights(csq.originCode, csq.destCode, csq.date);
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
        flights = provider.getFlights(crq.originCode, crq.destCode, crq.date);
        Reservation newReserve ;
        newReserve = provider.makeReservation(crq);
        String response;
        response = newReserve.getToken() + " " + newReserve.getTotalPrice() +"\n";
        this.reservations.add(newReserve);
        return response;
    }

    public String finalizeReservation(ClientFinalizeQuery cfq) throws IOException {
        String[] tickets ;
        String response = null;
        for(int i = 0 ; i < reservations.size() ; i++){
            if(Objects.equals(reservations.get(i).getToken(), cfq.token)){
                reservations.get(i).verify();
                tickets = provider.getTickets(reservations.get(i));
                response = printTickets(reservations.get(i),tickets);
            }
        }
        return response;
    }

    private String printTickets(Reservation reservation, String[] args) {
        String res="";
        String[] DAM = findPlane(reservation.getFlightNumber(), reservation.getOriginCode() ,reservation.getDestCode());
        for(int i = 1 ; i < args.length ; i++){
            Ticket newTicket = new Ticket(args[0],args[i],reservation);
            tickets.add(newTicket);
            res += reservation.getPeople().get(i-1).getFirstName() + " " + reservation.getPeople().get(i-1).getSurName() + " "+ newTicket.getRefCode()+
                    " " + newTicket.getNumber() + " " + reservation.getOriginCode() + " " + reservation.getDestCode() + " " +
                    reservation.getAirlineCode() + " " + reservation.getFlightNumber() + " " +
                    reservation.getSeatClass() + " " + DAM[0] + " " + DAM[1] + " " + DAM[2] + "\n";
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

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public Flight searchFlight(String number, String origin, String dest, String date) {
        for(int i = 0 ; i < flights.size() ; i++){
            if(Objects.equals(flights.get(i).getOrigin(), origin) &&
                    Objects.equals(flights.get(i).getNumber(), number) &&
                    Objects.equals(flights.get(i).getDestination(), dest) &&
                    Objects.equals(flights.get(i).getDate(), date)
                    ){
            return flights.get(i);
            }
        }
        return null;
    }
}
