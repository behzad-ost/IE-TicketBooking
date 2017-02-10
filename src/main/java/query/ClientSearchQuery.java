package query;

import java.util.Date;

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
        adults = Integer.parseInt(args[4]);
        childs = Integer.parseInt(args[5]);
        infants = Integer.parseInt(args[6]);
    }
}
