package controller.jersey;

import org.apache.log4j.Logger;
import query.ClientSearchQuery;
import service.Flight;
import service.Manager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ali on 5/1/17.
 */

@Path("/searchAll")
public class SearchAll {
    final static Logger logger = Logger.getLogger(SearchAll.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResult parseSearchAndSendResponse(SearchAllInfo searchAllInfo) throws IOException {
        SearchResult gg = new SearchResult();
        String[] params = new String[7];
        int numOfFlights = 0;

        Manager manager = Manager.getInstance();

        params[1] = searchAllInfo.getSrc();
        params[2] = searchAllInfo.getDest();
        params[3] = searchAllInfo.getStartDate();
        params[4] = searchAllInfo.getNumOfAdults();
        params[5] = searchAllInfo.getNumOfChildren();
        params[6] = searchAllInfo.getNumOfInfants();

        ClientSearchQuery csq = new ClientSearchQuery(params);
        String res = manager.search(csq);

        ArrayList<Flight> flights = manager.getFlights();

//        gg.setNumOfFlights(flights.size());
        if (flights.size() < 1) {
            gg.setSuccess(false);
            gg.setErrorMessage("No Flights Found!");
            return gg;
        }


        for(int i = 0 ; i < flights.size() ; i++) {
            FlightInfo fi = new FlightInfo();
            Flight f = flights.get(i);

            fi.setAirlineCode(f.getAirlineCode());
            fi.setFlightNumber(f.getNumber());
            fi.setDate(f.getDate());
            fi.setOrigin(f.getOrigin());
            fi.setDest(f.getDestination());
            fi.setDepartureTime(f.getdTime());
            fi.setArrivalTime(f.getaTime());
            fi.setPlaneModel(f.getPlaneModel());

            for (int j = 0; j < f.getSeats().size(); j++) {
                if (f.getSeats().get(j).getAvailable() - (Integer.parseInt(params[4])+Integer.parseInt(params[5])+ Integer.parseInt(params[6]))>-1) {
                    logger.debug("Seat No. " + j + "Flight: " + f.getAirlineCode() + " Class: " + f.getSeats().get(j).getClassName());
                    numOfFlights++;
                    int totalPrice = csq.adults * f.getSeats().get(j).getAdultPrice() +
                            csq.childs * f.getSeats().get(j).getChildPrice() +
                            csq.infants * f.getSeats().get(j).getInfantPrice();

                    fi.setTotalPrice(totalPrice);
                    fi.setSeatClassName(f.getSeats().get(j).getClassName());
                    fi.setNumOfAvailableSeats(f.getSeats().get(j).getAvailable());
                    gg.getFlights().add(fi);
                }
            }

            gg.setNumOfFlights(numOfFlights);
        }

        return gg;
    }
}
