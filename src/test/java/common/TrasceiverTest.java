package common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

//import static jdk.nashorn.internal.parser.TokenKind.IR;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.TestCase.assertEquals;

/**
 * Created by ali on 2/11/17.
 */
public class TrasceiverTest {
    private Transceiver ts;
    private String helperResponse;

    @Before
    public void setUp() throws IOException {
        ts = new Transceiver("188.166.78.119", 8081);
    }

    @Test
    public void sendCorrectData() throws IOException {
        ts.send("AV THR MHD 05Feb\n");
        helperResponse = ts.receive();
        assertNotSame("Message Must Not Be: ", "Bad input!\n", helperResponse);
    }

    @Test
    public void sendWrongData() throws IOException {
        ts.send("mock!\n");
        helperResponse = ts.receive();
        assertEquals("Message Must Be: ", "Bad input!\n", helperResponse);
    }

    @After
    public void cleanUp() throws IOException {
        ts.close();
    }
}