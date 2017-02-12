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

            CommandHandler ch = new CommandHandler(request);
            ch.parseCommand();
            String commandType;
            try {
                commandType = ch.getCommandType();
            } catch (ArrayIndexOutOfBoundsException ex) {
                server.send("Haroomi Dorost Type Kon!");
                continue;
            }

            switch (commandType) {
                case "search":
//                    System.out.println("search!!");
                    ClientSearchQuery csq = ch.createSearchQuery();
                    String response = manager.search(csq);
                    server.send(response);
                    break;
                case "reserve":
//                    System.out.println("reserve!");
                    ClientReserveQuery crq = ch.createReserveQuery();

                    int numOfPeople;
                    numOfPeople = crq.getNumOfPeople();

                    if (numOfPeople == crq.adults + crq.childs + crq.infants) {
                        System.out.println("sag: " + numOfPeople);
                    } else
                        break;

                    System.out.println("adults: " + crq.adults);
                    System.out.println("childs: " + crq.childs);
                    System.out.println("infants: " + crq.infants);

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

                    crq.printPeople();
                    System.out.println("people!");
                    response = manager.makeReservation(crq);
                    server.send(response);
                    break;
                case "finalize":
//                    System.out.println("finalize!");
                    ClientFinalizeQuery cfq = ch.createFinalizeQuery();
                    response = manager.finalizeReservation(cfq);
                    server.send(response);
                    break;
                default:
                    server.send("Haroomi Dorost Type Kon!");
            }
        }
        server.close();
    }
}