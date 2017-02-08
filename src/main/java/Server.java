/**
 * Created by behzad on 2/8/17.
 */

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[]args) throws IOException, ClassNotFoundException {
        ServerSocket server = new ServerSocket(8081);
        System.out.println("Server Listening on port 8081 ...");
        Socket socket = server.accept();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println(ois.readObject());
        ois.close();
        socket.close();
        server.close();
    }
}
