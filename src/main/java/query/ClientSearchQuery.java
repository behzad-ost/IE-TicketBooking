package query;

import java.util.Date;

/**
 * Created by ali on 2/10/17.
 */
public class ClientSearchQuery {
    private String originCode;
    private String destCode;
    private String date;
//    private Date date;
    private int adults;
    private int childs;
    private int infants;

    public ClientSearchQuery(String[] args) {
        originCode = args[1];
        destCode = args[2];
        date = args[3];
        adults = Integer.parseInt(args[4]);
        childs = Integer.parseInt(args[5]);
        infants = Integer.parseInt(args[6]);
    }
}
