package client;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by ali on 2/8/17.
 */
public class Clinet {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8081);
        socket.getOutputStream().write(123);
        socket.close();
    }
}
