package server;
import common.ServerTransceiver;
import common.Transceiver;
import query.ClientSearchQuery;
import query.CommandHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by ali on 2/9/17.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        Transceiver transceiver = new Transceiver("188.166.78.119", 8081);
        ServerTransceiver server = new ServerTransceiver(8081);
        System.out.println("Server is running");
        while(true) {
            String request = server.receive();
            System.out.println("request: " + request);

            transceiver.send(request);
            String helperResponse = transceiver.receive();
            System.out.println("helper :\n" + helperResponse);

            server.send(helperResponse);

//        CommandHandler ch = new CommandHandler(request);
//        ch.parseCommand();
//        String commandType = ch.getCommandType();
//        switch (commandType) {
//            case "search":
////                ClientSearchQuery csq = new ClientSearchQuery();
//                System.out.println("search!!");
//                break;
//            case "reserve":
//                System.out.println("reserve!");
//                break;
//            case "finalize":
//                System.out.println("finalize!");
//                break;
//        }


//        PrintStream output = new PrintStream(clientSocket.getOutputStream());
//        output.println("behx");
//        output.flush();
//        output.close();
        }
//        server.close();
    }
}