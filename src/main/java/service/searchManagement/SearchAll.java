package service.searchManagement;

import org.apache.log4j.Logger;
import query.ClientSearchQuery;
import data.Flight;
import data.Manager;
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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResult parseSearchAndSendResponse(SearchAllInfo searchAllInfo) throws IOException, SQLException {
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


        String url = "jdbc:hsqldb:hsql://localhost/testdb";
        String username = "SA";
        String password = "";
        logger.info("Connecting to Database");
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver" );
        } catch (Exception e) {
            logger.error("ERROR: failed to load HSQLDB JDBC driver.");
        }
        Connection connection = DriverManager.getConnection(url, username, password);
        logger.info("Connected To Database!");

        Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

        String sql;
        sql = "SELECT * from flight";
        ResultSet resultSet = statement.executeQuery(sql);

        if (!resultSet.next()) {
            logger.info("DB is empty");
            // TODO: 5/12/17 fillTheDatabase();
        } else {
            logger.info("DB is not empty");
            resultSet.first();
//            readFromDatabase(resultSet);
            // TODO: 5/12/17 readFromDatabase();
            while(resultSet.next()) {
                logger.debug(resultSet.getInt("fid"));
            }
        }

        resultSet.close();
        statement.close();
        connection.close();

        /*
        manager.search(csq);

        ArrayList<Flight> flights = manager.getFlights();

        if (flights.size() < 1) {
            gg.setSuccess(false);
            gg.setErrorMessage("No Flights Found!");
            return gg;
        }


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
                if (f.getSeats().get(j).getAvailable() - (Integer.parseInt(params[4])+Integer.parseInt(params[5])+ Integer.parseInt(params[6]))>-1) {
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
        */
        return gg;
    }

//    private boolean updateFlightsDB(Connection connection) {
//
//    }

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
