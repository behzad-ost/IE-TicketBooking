package server;
import common.ServerTransceiver;
import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import query.ClientSearchQuery;
import query.CommandHandler;
import service.Manager;

import java.io.*;

/**
 * Created by ali on 2/9/17.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        ServerTransceiver server = new ServerTransceiver(8083);
        System.out.println("Server is running");
        server.accept();
        Manager manager = new Manager();
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
            String response;
        switch (commandType) {
            case "search":
                System.out.println("search!!");
                ClientSearchQuery csq = ch.createSearchQuery();
                response = manager.search(csq);
                server.send(response);
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
                response = manager.makeReservation(crq);
                server.send(response);
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