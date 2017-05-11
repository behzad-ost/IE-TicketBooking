package service.finalizeManagement;

import org.apache.log4j.Logger;
import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import data.Manager;
import data.Reservation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.Objects;

/**
 * Created by ali on 5/2/17.
 */

@Path("/finalize")
public class FinalizeTickets {
    final static Logger logger = Logger.getLogger(FinalizeTickets.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FinalizeResult finalizeTickets(FinalizeInfo finalizeInfo) throws IOException {
        FinalizeResult response = new FinalizeResult();

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
        String res = Manager.getInstance().makeReservation(crq);

        String[] p = new String[2];
        String[] tp = res.split("\\s+");
        p[1] = tp[0]; // token
        ClientFinalizeQuery cfq = new ClientFinalizeQuery(p);
        Manager.getInstance().finalizeReservation(cfq);

        Reservation reservation = Manager.getInstance().findReserveByToken(tp[0]);
        response.setTickets(reservation.getTickets());
        return response;
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
}
