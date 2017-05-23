package service.ticket;

import org.apache.log4j.Logger;
import service.common.DBQuery;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by ali on 5/22/17.
 */
@Path("/ticket")
public class GetTicket {
    final static Logger logger = Logger.getLogger(GetTicket.class);
    private DBQuery query = new DBQuery();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public TicketResult getTicket(@QueryParam(value = "id") String id,
                                  @HeaderParam(value = "token") String token ,
                                  @HeaderParam(value = "role") String urole,
                                  @HeaderParam(value = "id") String uid) {
        TicketResult response = new TicketResult();
        logger.info("Token on /ticket : " + token);
        logger.info("Role on /ticket : " + urole);
        logger.info("Id on /ticket : " + uid);
        // TODO: 5/22/17 get national id and user role from token
        String nationalId = uid;
        String role = urole;

        try {
            Connection connection = query.setupDB();
            ResultSet tickets = query.getTicket(connection, id);
            if (tickets.next()) {
                // TODO: 5/22/17 return ticket
                String refCode = tickets.getString("tid");
                String reserveToken = tickets.getString("rid");

                if (Objects.equals(role, "admin")) {
                    response.setRefCode(refCode);
                    response.setReserveToken(reserveToken);
                    return response;
                } else {
                    ResultSet people = query.getPerson(connection, nationalId);
                    if (people.next()) {
                        String pid = people.getString("pid");
                        if (pid == id) {
                            response.setRefCode(refCode);
                            response.setReserveToken(reserveToken);
                            return response;
                        }
                    } else {
                        response.setSuccess(false);
                        response.setErrorMessage("No person with your national id found!");
                        return response;
                    }
                }

            } else {
                response.setSuccess(false);
                response.setErrorMessage("No ticket found!");
                return response;
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return response;
    }
}
