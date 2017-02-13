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
        try {
//            int portNumber = Integer.parseInt(args[0]);
//            String helperIp = args[1];
//            int helperPort = Integer.parseInt(args[2]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
            return;
        }


        ServerTransceiver server = new ServerTransceiver(8083);
//        ServerTransceiver server = new ServerTransceiver(portNumber);
        System.out.println("Server is running");
        server.accept();
        Manager manager = new Manager();
//        Manager manager = new Manager(helperIp, helperPort);
        while(true) {
            String request = server.receive();
            if (request == null || request == "" || request == "\n")
                break;
            System.out.println("request: " + request);

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
                    try {
                        ClientSearchQuery csq = ch.createSearchQuery();
                        response = manager.search(csq);
                        server.send(response);
                    } catch (Exception ex) {
                        server.send("Error Occured!");
                    }
                    break;
                case "reserve":
                    ClientReserveQuery crq;
                    try {
                        crq = ch.createReserveQuery();

                        int numOfPeople;
                        numOfPeople = crq.getNumOfPeople();
                        if (numOfPeople != crq.adults + crq.childs + crq.infants) {
                            break;
                        }

                        for (int i = 0; i < crq.adults; i++) {
                            String personInfo = server.receive();
                            crq.addPerson(personInfo, "adult");
                        }
                        for (int i = 0; i < crq.childs; i++) {
                            String personInfo = server.receive();
                            crq.addPerson(personInfo, "child");
                        }
                        for (int i = 0; i < crq.infants; i++) {
                            String personInfo = server.receive();
                            crq.addPerson(personInfo, "infant");
                        }
                        response = manager.makeReservation(crq);
                        server.send(response);
                    } catch (Exception ex) {
//                        System.out.println("Error Occured!");
                        server.send("Error Occured!");
                        break;
                    }
                    break;
                case "finalize":
                    try {
                        ClientFinalizeQuery cfq = ch.createFinalizeQuery();
                        response = manager.finalizeReservation(cfq);
                        server.send(response);
                    } catch (Exception ex) {
                        server.send("Error Occured!");
//                        System.out.println("Error Occured!");
                    }
                    break;
                default:
                    server.send("Haroomi Dorost Type Kon!");
            }
        }
        server.close();
    }
}