package data;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by behzad on 3/5/17.
 */
public class FlightRepo {
    private ArrayList<Flight> flights;
    private int lastId;
    final static Logger logger = Logger.getLogger(FlightRepo.class);


    public FlightRepo(){
        flights = new ArrayList<>();
        lastId = 1;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void setFlights(ArrayList<Flight> flights) {
        for(int i = 0 ; i < flights.size() ; i++)
            logger.info(flights.get(i).getNumber());
        this.flights = flights;
    }

    public void addFlights(ArrayList<Flight> flights) {
        for(int i = 0 ; i < flights.size() ; i++){
            flights.add(flights.get(i));
            lastId++;
        }
    }

    public String[] findFlightTimes(String flightNumber, String originCode, String destCode) {
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

    public int getId() {
        return lastId;
    }
}
