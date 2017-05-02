package controller.jersey;

import org.apache.log4j.Logger;
import service.Manager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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
    public ReserveResult reserveTicket(ReserveInfo reserveInfo) {
        ReserveResult rr = new ReserveResult();
        Manager manager = Manager.getInstance();

        return rr;
    }
}
