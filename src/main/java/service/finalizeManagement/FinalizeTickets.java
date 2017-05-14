package service.finalizeManagement;

import data.Person;
import org.apache.log4j.Logger;
import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import data.Manager;
import data.Reservation;
import service.common.DBQuery;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

/**
 * Created by ali on 5/2/17.
 */

@Path("/finalize")
public class FinalizeTickets {
    final static Logger logger = Logger.getLogger(FinalizeTickets.class);
    private DBQuery query = new DBQuery();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FinalizeResult finalizeTickets(FinalizeInfo finalizeInfo) throws IOException {
        FinalizeResult response = new FinalizeResult();

        try {
            Connection connection = query.setupDB();
            String providerResponse = sendDataToProvider(finalizeInfo);

        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e.getMessage());
            response.setSuccess(false);
            response.setErrorMessage("Error in DB");
        }


//        String[] p = new String[2];
//        String[] tp = res.split("\\s+");
//        p[1] = tp[0]; // token
//        ClientFinalizeQuery cfq = new ClientFinalizeQuery(p);
//        Manager.getInstance().finalizeReservation(cfq);
//
//        Reservation reservation = Manager.getInstance().findReserveByToken(tp[0]);
//        response.setTickets(reservation.getTickets());
        return response;
    }

    private String sendDataToProvider(FinalizeInfo finalizeInfo) throws IOException {
        String[] params = new String[10];
        params[1] = finalizeInfo.getOrigin();
        params[2] = finalizeInfo.getDest();
        params[3] = finalizeInfo.getDate();
        params[4] = finalizeInfo.getAirlineCode();
        params[5] = finalizeInfo.getFlightNumber();
        params[6] = finalizeInfo.getSeatClassName();
        params[7] = Integer.toString(finalizeInfo.getNumOfAdults());
        params[8] = Integer.toString(finalizeInfo.getNumOfChildren());
        params[9] = Integer.toString(finalizeInfo.getNumOfInfants());

        ClientReserveQuery crq = new ClientReserveQuery(params);
        addPersons(finalizeInfo, crq);
        String providerResponse = Manager.getInstance().makeReservation(crq);
        return providerResponse;
    }

    private void addPersons(FinalizeInfo fi, ClientReserveQuery crq) {
        for (int i = 0; i < fi.getPeople().size(); i++) {
            if (Objects.equals(fi.getPeople().get(i).getAgeType(), "adult")) {
                String[] person = new String[3];
                person[0] = fi.getPeople().get(i).getFirstName();
                person[1] = fi.getPeople().get(i).getSurName();
                person[2] = fi.getPeople().get(i).getNationalId();
                crq.addPerson(person, "adult");
            } else if (Objects.equals(fi.getPeople().get(i).getAgeType(), "child")) {
                String[] person = new String[3];
                person[0] = fi.getPeople().get(i).getFirstName();
                person[1] = fi.getPeople().get(i).getSurName();
                person[2] = fi.getPeople().get(i).getNationalId();
                crq.addPerson(person, "child");
            } else if (Objects.equals(fi.getPeople().get(i).getAgeType(), "infant")) {
                String[] person = new String[3];
                person[0] = fi.getPeople().get(i).getFirstName();
                person[1] = fi.getPeople().get(i).getSurName();
                person[2] = fi.getPeople().get(i).getNationalId();
                crq.addPerson(person, "infant");
            }
        }
    }

    private void addPeopleToDB(Connection connection, FinalizeInfo fi) {
        logger.info("Adding People to DB");
        for (int i = 0; i < fi.getPeople().size(); i++) {
            if (Objects.equals(fi.getPeople().get(i).getAgeType(), "adult")) {
                Person person = new Person(fi.getPeople().get(i).getFirstName(),
                        fi.getPeople().get(i).getSurName(),
                        fi.getPeople().get(i).getNationalId(),
                        "adult", "male");
//                query.addPerson(connection, person, fi.getPeople().get(i), );
                // TODO: 5/14/17 get tid
            } else if (Objects.equals(fi.getPeople().get(i).getAgeType(), "child")) {
            } else if (Objects.equals(fi.getPeople().get(i).getAgeType(), "infant")) {

            }
        }
    }
}
