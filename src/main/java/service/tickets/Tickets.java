package service.tickets;

import data.Ticket;
import org.apache.log4j.Logger;
import service.common.DBQuery;
import service.common.TicketInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by behzad on 5/21/17.
 */

@Path("/alltickets")
public class Tickets {
    final static Logger logger = Logger.getLogger(Tickets.class);
    private DBQuery query = new DBQuery();

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public TicketsResult getAllTickets() throws SQLException, ClassNotFoundException {
        Connection connection = query.setupDB();
        TicketsResult result = new TicketsResult();
        ResultSet resultSet = query.searchAllTickets(connection);
        if (!resultSet.next()) {
            result.setErrorMessage("No tickets available");
            logger.info("No Ticket in DB");
        }
        else {
            result.setSuccess(true);
            do{
                String tokenId = resultSet.getString("tid");
                String refId = resultSet.getString("rid");
                String pId = resultSet.getString("pid");
                result.getTickets().add(new TicketInfo(tokenId, refId, pId));
            }while (resultSet.next());
        }
        return result;
    }
}
