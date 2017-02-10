package server;
import common.ServerTransceiver;
import common.Transceiver;
import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import query.ClientSearchQuery;
import query.CommandHandler;
import service.Manager;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by ali on 2/9/17.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerTransceiver server = new ServerTransceiver(8083);
        System.out.println("Server is running");
        server.accept();
        while(true) {
            String request = server.receive();
            if (request == null || request == "" || request == "\n")
                break;
            System.out.println("request: " + request);

//            transceiver.send(request);
//            String helperResponse = transceiver.receive();
//            System.out.println("helper :\n" + helperResponse);
//
//            server.send(helperResponse);

        CommandHandler ch = new CommandHandler(request);
        ch.parseCommand();
        String commandType;
        try {
            commandType = ch.getCommandType();
        } catch (ArrayIndexOutOfBoundsException ex) {
            server.send("Haroomi Dorost Type Kon!");
            continue;
        }

        Manager manager = new Manager();
        switch (commandType) {
            case "search":
                System.out.println("search!!");
                ClientSearchQuery csq = ch.createSearchQuery();
                manager.search(csq);
                break;
            case "reserve":
                System.out.println("reserve!");
                ClientReserveQuery crq = ch.createReserveQuery();

                int numOfPeople;
                numOfPeople = crq.getNumOfPeople();
                for (int i = 0; i < numOfPeople; i++) {
                    String personInfo = server.receive();
                    System.out.println("person: " + personInfo);
                    crq.addPerson(personInfo);
                }
                System.out.println("end!");
                manager.makeReservation(crq);
                break;
            case "finalize":
                System.out.println("finalize!");
                ClientFinalizeQuery cfq = ch.createFinalizeQuery();
                break;
        }


//        PrintStream output = new PrintStream(clientSocket.getOutputStream());
//        output.println("behx");
//        output.flush();
//        output.close();
        }
        server.close();
    }
}