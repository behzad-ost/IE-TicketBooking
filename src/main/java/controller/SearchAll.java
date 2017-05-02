package controller;

import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by ali on 5/1/17.
 */

@Path("/searchAll")
public class SearchAll {
    final static Logger logger = Logger.getLogger(SearchAll.class);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sayHelloWorld(SearchAllInfo searchAllInfo) {
        logger.info(searchAllInfo.getNumOfAdults());
        String gg = Integer.toString(searchAllInfo.getNumOfAdults());
        return Response.status(200).entity(gg).build();
    }


    @GET
    @Path("/hello")
    @Produces(MediaType.TEXT_PLAIN)
    public String gg() {
        return "sag";
    }
}
