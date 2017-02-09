package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ali on 2/9/17.
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081);
        System.out.println("Server is running");

        Socket clientSocket = server.accept();

//        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
//        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream());


        String line;
        while(true) {
            line = input.readLine();
            if (line != null) {
                output.write("kir");
                System.out.println(line);
            }
        }

//        in.close();
//        out.close();
//        server.close();

    }
}
