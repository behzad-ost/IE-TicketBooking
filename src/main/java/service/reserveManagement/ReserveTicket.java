package service.reserveManagement;

import org.apache.log4j.Logger;
import data.Flight;
import data.Manager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ali on 5/2/17.
 */

@Path("/reserve")
public class ReserveTicket {
    final static Logger logger = Logger.getLogger(ReserveTicket.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ReserveResult reserveTicket(ReserveInfo ri) {
        ReserveResult response = new ReserveResult();
        Manager manager = Manager.getInstance();

        Flight flight = manager.searchFlight(ri.getFlightNumber(), ri.getOrigin(), ri.getDest(), ri.getDate());
        if (flight == null) {
            response.setErrorMessage("پروازی یافت نشد!");
            response.setSuccess(false);
            return response;
        }

        ArrayList<Flight.Seat> seats = flight.getSeats();
        Flight.Seat seat = null;
        for (int i = 0 ; i < seats.size() ; i++){
            if(Objects.equals(ri.getSeatClassName(), seats.get(i).getClassName())) {
                seat = seats.get(i);
                break;
            }
        }
        if (seat == null) {
            response.setErrorMessage("صندلی یافت نشد!");
            response.setSuccess(false);
            return response;
        }

        int adultFee = seat.getAdultPrice();
        int childFee = seat.getChildPrice();
        int infantFee = seat.getInfantPrice();
        int adultSum = adultFee * ri.getNumOfAdults();
        int childSum = childFee * ri.getNumOfChildren();
        int infantSum = infantFee * ri.getNumOfInfants();

        response.setAdultFee(adultFee);
        response.setChildFee(childFee);
        response.setInfantFee(infantFee);
        response.setAdultSum(adultSum);
        response.setChildSum(childSum);
        response.setInfantSum(infantSum);
        response.setTotalSum(adultSum + childSum + infantSum);
        return response;
    }
}
