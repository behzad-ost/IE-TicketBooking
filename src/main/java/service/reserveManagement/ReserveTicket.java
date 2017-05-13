package service.reserveManagement;

import org.apache.log4j.Logger;
import data.Flight;
import data.Manager;
import service.common.DBQuery;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ali on 5/2/17.
 */

@Path("/reserve")
public class ReserveTicket {
    final static Logger logger = Logger.getLogger(ReserveTicket.class);
    DBQuery query = new DBQuery();
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ReserveResult reserveTicket(ReserveInfo ri) {
        ReserveResult response = new ReserveResult();

        try {
            Connection connection = query.setupDB();
            response = getDataFromDB(connection, ri);
            // TODO: 5/13/17 insert persons
            // TODO: 5/13/17 create tickets
        }  catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage());
            response.setSuccess(false);
            response.setErrorMessage("Error in DB");
            return response;
        }
//        Manager manager = Manager.getInstance();
//        Flight flight = manager.searchFlight(ri.getFlightNumber(), ri.getOrigin(), ri.getDest(), ri.getDate());
//        if (flight == null) {
//            response.setErrorMessage("پروازی یافت نشد!");
//            response.setSuccess(false);
//            return response;
//        }
//
//        ArrayList<Flight.Seat> seats = flight.getSeats();
//        Flight.Seat seat = null;
//        for (int i = 0 ; i < seats.size() ; i++){
//            if(Objects.equals(ri.getSeatClassName(), seats.get(i).getClassName())) {
//                seat = seats.get(i);
//                break;
//            }
//        }
//        if (seat == null) {
//            response.setErrorMessage("صندلی یافت نشد!");
//            response.setSuccess(false);
//            return response;
//        }

//        int adultFee = seat.getAdultPrice();
//        int childFee = seat.getChildPrice();
//        int infantFee = seat.getInfantPrice();
//        int adultSum = adultFee * ri.getNumOfAdults();
//        int childSum = childFee * ri.getNumOfChildren();
//        int infantSum = infantFee * ri.getNumOfInfants();
//
//        response.setAdultFee(adultFee);
//        response.setChildFee(childFee);
//        response.setInfantFee(infantFee);
//        response.setAdultSum(adultSum);
//        response.setChildSum(childSum);
//        response.setInfantSum(infantSum);
//        response.setTotalSum(adultSum + childSum + infantSum);
        return response;
    }


    private ReserveResult getDataFromDB(Connection connection, ReserveInfo ri) throws SQLException {
        ReserveResult response = new ReserveResult();
        int sid;
        boolean found = false;

        ResultSet flightsRs = query.getFlight(connection, ri.getFlightNumber(), ri.getOrigin(), ri.getDest(), ri.getDate());
        while (flightsRs.next()) {
            // Flight
            logger.debug("FID: " + flightsRs.getInt("FID"));

            int fid = flightsRs.getInt("FID");

            ResultSet flightSeatsRs = query.searchForSeatsInFlight(connection, fid);
            while (flightSeatsRs.next()) {
                // Flight_Seat
                logger.debug("SID: " + flightSeatsRs.getInt("SID"));

                sid = flightSeatsRs.getInt("SID");
                ResultSet seatRs = query.getSeatData(connection, sid);
                while (seatRs.next()) {
                    // Seat
                    logger.debug("SEAT: " + seatRs.getString("CLASS_NAME"));

                    if (Objects.equals(ri.getSeatClassName(), seatRs.getString("CLASS_NAME"))) {
                        found = true;
                        int adultFee = seatRs.getInt("adult_price");
                        int childFee = seatRs.getInt("child_price");
                        int infantFee = seatRs.getInt("infant_price");
                        logger.debug("adult: " + adultFee);
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
                        break;
                    }
                }
                seatRs.close();
            }
            flightSeatsRs.close();
        }
        flightsRs.close();

        if (!found) {
            response.setErrorMessage("صندلی یافت نشد!");
            response.setSuccess(false);
        }
        return response;
    }
}