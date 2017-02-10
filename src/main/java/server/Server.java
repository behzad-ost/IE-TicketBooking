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
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintStream output = new PrintStream(clientSocket.getOutputStream());
        String line;
        while((line = input.readLine()) != null) {
            output.println("behx");
            System.out.println("data from client: " + line);
            output.flush();
        }
        input.close();
        output.close();
        server.close();
    }
}
