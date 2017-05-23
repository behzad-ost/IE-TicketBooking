package service;

import static org.junit.Assert.assertEquals;

import data.Reservation;
import org.junit.Ignore;
import org.junit.Test;
import query.ClientReserveQuery;
import query.CommandHandler;

import java.io.IOException;

/**
 * Created by ali on 2/11/17.
 */
@Ignore
public class ManagerTest {
    CommandHandler ch;
    ClientReserveQuery crq;
    Reservation rs;

    @Test
    public void priceCalculateTest1() throws IOException {
        ch = new CommandHandler("reserve THR MHD 05Feb IR 452 Y 1 0 0");
        ch.parseCommand();
        crq = ch.createReserveQuery();
//        crq.addPerson(new String[]{"ali", "x", "123"}, "adult");

        rs = new Reservation(crq);
        rs.parseHelperResponse("token^__^ 1000 2000 3000", 1, 0,0);
        assertEquals("Must be 1000!", rs.getTotalPrice(), 1000);
    }

    @Test
    public void priceCalculateTest2() throws IOException {
        ch = new CommandHandler("reserve THR MHD 05Feb IR 452 Y 2 4 3");
        ch.parseCommand();
        crq = ch.createReserveQuery();
//        crq.addPerson(new String[]{"ali", "boom","123"}, "adult");
//        crq.addPerson(new String[]{"ali", "boom","123"}, "adult");
//
//        crq.addPerson(new String[]{"behx", "x", "456"}, "child");
//        crq.addPerson(new String[]{"behx", "x", "456"}, "child");
//        crq.addPerson(new String[]{"behx", "x", "456"}, "child");
//        crq.addPerson(new String[]{"behx", "x", "456"}, "child");


//        crq.addPerson(new String[]{"vahid" ,"asd" ,"789"}, "infant");
//        crq.addPerson(new String[]{"vahid" ,"asd" ,"789"}, "infant");
//        crq.addPerson(new String[]{"vahid" ,"asd" ,"789"}, "infant");

        rs = new Reservation(crq);
        rs.parseHelperResponse("token^__^ 1000 2000 3000", 2, 4,3);
        assertEquals("Must be 19000!", rs.getTotalPrice(), 19000);
    }

}
