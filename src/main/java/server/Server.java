package server;
import common.Transceiver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by ali on 2/9/17.
 */
public class Server {
    private static String receive(Socket socket) throws IOException {
        String res = "";
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while((line = input.readLine()) != null) {

//            System.out.println("data from client: " + line);
            res+= line +'\n';
            if(!input.ready())
                break;
        }
        return res;
    }
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8081);
        Transceiver transceiver = new Transceiver("188.166.78.119", 8081);
        System.out.println("Server is running");
        Socket clientSocket = server.accept();
        String request = receive(clientSocket);
        System.out.println("request: " + request);
        transceiver.send(request);
        String helperResponse = transceiver.receive();
        System.out.println("helper : " + helperResponse);

//        PrintStream output = new PrintStream(clientSocket.getOutputStream());
//        output.println("behx");
//        output.flush();
//        output.close();

        server.close();
    }
}