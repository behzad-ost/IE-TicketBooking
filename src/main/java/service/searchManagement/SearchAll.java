package service.searchManagement;

import org.apache.log4j.Logger;
import query.ClientSearchQuery;
import data.Flight;
import data.Manager;
import service.common.DBQuery;
import service.common.FlightInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by ali on 5/1/17.
 */

@Path("/searchAll")
public class SearchAll {
    final static Logger logger = Logger.getLogger(SearchAll.class);
    private DBQuery query = new DBQuery();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResult parseSearchAndSendResponse(SearchAllInfo searchAllInfo) throws IOException, SQLException {
        SearchResult gg = new SearchResult();
        boolean shouldRefillDB = false;

        try {
            Connection connection = query.setupDB();
            ResultSet resultSet = query.searchForFlights(connection, searchAllInfo.getStartDate(), searchAllInfo.getSrc(),
                    searchAllInfo.getDest());

            if (!resultSet.next()) {
                logger.info("Could Not Find it in DB");
                shouldRefillDB = true;
            }
            // TODO: 5/12/17 check time
//            else if (dataIsOutDated(resultSet)) {
//                shouldRefillDB = true;
//            }

            if (shouldRefillDB) {
                logger.info("refill");
                resultSet.close();
                gg = refillDatabase(gg, searchAllInfo);
                // TODO: 5/12/17 refillDB
            } else {
                readFromDatabase(resultSet, connection, gg, searchAllInfo);
            }

            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage());
            gg.setSuccess(false);
            gg.setErrorMessage("Error in DB");
        }
        return gg;
    }


    private SearchResult readFromDatabase(ResultSet resultSet, Connection connection, SearchResult gg, SearchAllInfo searchAllInfo) throws SQLException {
        logger.debug("Start reading from db");
        int numOfFlights = 0;
        resultSet.beforeFirst();
        while (resultSet.next()) {
            ResultSet rs = query.searchForSeatsInFlight(connection, resultSet.getInt("FID"));

            while (rs.next()) {
                int seatId = rs.getInt("SID");
                ResultSet finalRs = query.getSeatData(connection, seatId);

                while (finalRs.next()) {
                    if (seatId == finalRs.getInt("SID")) {

                    }

                    numOfFlights++;
                    FlightInfo fi = new FlightInfo();
                    fi.setAirlineCode(resultSet.getString("AIRLINE_CODE"));
                    fi.setFlightNumber(resultSet.getString("FLIGHT_NUM"));
                    fi.setDate(resultSet.getString("FLIGHT_DATE"));
                    fi.setOrigin(resultSet.getString("ORIGIN"));
                    fi.setDest(resultSet.getString("DEST"));
                    fi.setDepartureTime(resultSet.getString("DEP_TIME"));
                    fi.setArrivalTime(resultSet.getString("ARR_TIME"));
                    fi.setAirlineCode(resultSet.getString("AIRLINE_CODE"));
                    fi.setPlaneModel(resultSet.getString("AIRPLANE_MODEL"));


                    fi.setSeatClassName(finalRs.getString("CLASS_NAME"));

                    int totalPrice = 0;
                    totalPrice += Integer.parseInt(searchAllInfo.getNumOfAdults()) * finalRs.getInt("ADULT_PRICE");
                    totalPrice += Integer.parseInt(searchAllInfo.getNumOfChildren()) * finalRs.getInt("CHILD_PRICE");
                    totalPrice += Integer.parseInt(searchAllInfo.getNumOfInfants()) * finalRs.getInt("INFANT_PRICE");
                    fi.setTotalPrice(totalPrice);

                    gg.getFlights().add(fi);
                }
                finalRs.close();
            }
            rs.close();
        }
        gg.setNumOfFlights(numOfFlights);
        resultSet.close();
        return gg;
    }

    private boolean fillFlightsTable(Connection connection, SearchAllInfo searchAllInfo) throws SQLException {
        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String sql;
        sql = "INSERT INTO flight " +
                "( AIRLINE_CODE, FLIGHT_NUM, FLIGHT_DATE, ORIGIN, DEST, DEP_TIME, ARR_TIME, AIRPLANE_MODEL )" +
                "(?, ?, ?, ?, ?, ?, ?, ?)";

        ResultSet resultSet = statement.executeQuery(sql);

        return true;
    }

    private SearchResult refillDatabase(SearchResult gg, SearchAllInfo searchAllInfo) throws IOException {
        String[] params = new String[7];

        params[1] = searchAllInfo.getSrc();
        params[2] = searchAllInfo.getDest();
        params[3] = searchAllInfo.getStartDate();
        params[4] = searchAllInfo.getNumOfAdults();
        params[5] = searchAllInfo.getNumOfChildren();
        params[6] = searchAllInfo.getNumOfInfants();

        ClientSearchQuery csq = new ClientSearchQuery(params);
        Manager manager = Manager.getInstance();

        manager.search(csq);

        ArrayList<Flight> flights = manager.getFlights();

        if (flights.size() < 1) {
            gg.setSuccess(false);
            gg.setErrorMessage("No Flights Found!");
            return gg;
        }

        int numOfFlights = 0;
        for(int i = 0 ; i < flights.size() ; i++) {
            FlightInfo fi = new FlightInfo();
            Flight f = flights.get(i);

//            fi.setAirlineCode(airlineCodeToPersian(f.getAirlineCode()));
            fi.setAirlineCode(f.getAirlineCode());
            fi.setFlightNumber(f.getNumber());
//            fi.setDate(dateToPersian(f.getDate()));
            fi.setDate(f.getDate());

//            fi.setOrigin(locationConvertToPersian(f.getOrigin()));
            fi.setOrigin(f.getOrigin());
//            fi.setDest(locationConvertToPersian(f.getDestination()));
            fi.setDest(f.getDestination());

            fi.setDepartureTime(f.getdTime());
            fi.setArrivalTime(f.getaTime());
            fi.setPlaneModel(f.getPlaneModel());

            for (int j = 0; j < f.getSeats().size(); j++) {
                if (f.getSeats().get(j).getAvailable() - (Integer.parseInt(params[4]) + Integer.parseInt(params[5]) + Integer.parseInt(params[6])) > -1) {
                    logger.debug("Seat No. " + j + "Flight: " + f.getAirlineCode() + " Class: " + f.getSeats().get(j).getClassName());

                    numOfFlights++;
                    int totalPrice = csq.adults * f.getSeats().get(j).getAdultPrice() +
                            csq.childs * f.getSeats().get(j).getChildPrice() +
                            csq.infants * f.getSeats().get(j).getInfantPrice();

                    fi.setTotalPrice(totalPrice);
//                    fi.setSeatClassName(seatClassToPersian(f.getSeats().get(j).getClassName()));
                    fi.setSeatClassName(f.getSeats().get(j).getClassName());
                    fi.setNumOfAvailableSeats(f.getSeats().get(j).getAvailable());
                    gg.getFlights().add(fi);
                }
            }

            gg.setNumOfFlights(numOfFlights);
        }
        return gg;
    }




    //  persian converters
    private String locationConvertToPersian(String input) {
        if (Objects.equals(input, "THR")) {
            return "تهران";
        } else if (Objects.equals(input, "MHD")) {
            return "مشهد";
        } else {
            return input;
        }
    }

    private String airlineCodeToPersian(String input) {
        if (Objects.equals(input, "IR")) {
            return "ایران ایر";
        } else if (Objects.equals(input, "W5")) {
            return "ماهان";
        } else {
            return input;
        }
    }

    private String seatClassToPersian(String input) {
        if (Objects.equals(input, "Y")) {
            return "اقتصادی";
        } else if (Objects.equals(input, "F")) {
            return "درجه ۱";
        } else if (Objects.equals(input, "M")) {
            return "ایزی";
        } else {
            return input;
        }
    }

    private String dateToPersian(String input) {
        if (Objects.equals(input, "05Feb")) {
            return "۱۱ بهمن";
        } else if (Objects.equals(input, "06Feb")) {
            return "۱۲ بهمن";
        } else if (Objects.equals(input, "04Feb")) {
            return "۱۰ بهمن";
        } else {
            return input;
        }
    }
}
