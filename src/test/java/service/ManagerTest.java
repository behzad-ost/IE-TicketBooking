package service;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import query.ClientReserveQuery;
import query.CommandHandler;

import java.io.IOException;

/**
 * Created by ali on 2/11/17.
 */
public class ManagerTest {
    CommandHandler ch;
    ClientReserveQuery crq;
    Reservation rs;

    @Test
    public void priceCalculateTest1() throws IOException {
        ch = new CommandHandler("reserve THR MHD 05Feb IR 452 Y 1 0 0");
        ch.parseCommand();
        crq = ch.createReserveQuery();
        crq.addPerson("ali x 123");
        crq.addPerson("behx x 456");
        crq.addPerson("vahid boom 789");

        rs = new Reservation(crq);
        rs.parseHelperResponse("token^__^ 1000 2000 3000", 1, 0,0);
        assertEquals("Must be 6000!", rs.getTotalPrice(), 6000);
    }

    @Test
    public void priceCalculateTest2() throws IOException {
        ch = new CommandHandler("reserve THR MHD 05Feb IR 452 Y 2 4 3");
        ch.parseCommand();
        crq = ch.createReserveQuery();
        crq.addPerson("ali x 123");
        crq.addPerson("behx x 456");
        crq.addPerson("vahid boom 789");

        rs = new Reservation(crq);
        rs.parseHelperResponse("token^__^ 1000 2000 3000", 2, 4,3);
        assertEquals("Must be 6000!", rs.getTotalPrice(), 19000);
    }

}
