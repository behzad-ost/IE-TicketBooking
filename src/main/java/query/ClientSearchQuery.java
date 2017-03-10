package query;

import client.Client;

import java.util.Date;
import java.util.logging.Logger;

/**
 * Created by ali on 2/10/17.
 */
public class ClientSearchQuery {
    public String originCode;
    public String destCode;
    public String date;
//    private Date date;
    public int adults;
    public int childs;
    public int infants;

    public ClientSearchQuery(String[] args) {
        originCode = args[1];
        destCode = args[2];
        date = args[3];
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Client.class);
        logger.debug(args[4]+args[5]+args[6]);
        adults = Integer.parseInt(args[4]);
        childs = Integer.parseInt(args[5]);
        infants = Integer.parseInt(args[6]);
    }
}
