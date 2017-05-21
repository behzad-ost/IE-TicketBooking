package service.tickets;

import data.Ticket;
import service.common.DBQuery;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * Created by behzad on 5/21/17.
 */

@Path("/alltickets")

public class Tickets {
//    private DBQuery query = new DBQuery();

    @GET
//    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)

    public TicketResult getAllTickets(){
        //TODO : Find in DB
        TicketResult result = new TicketResult(true ,"All tickets are here");
        return result;
    }

}
