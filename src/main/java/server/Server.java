package server;
import common.ServerTransceiver;
import org.apache.log4j.*;
import query.ClientFinalizeQuery;
import query.ClientReserveQuery;
import query.ClientSearchQuery;
import query.CommandHandler;
import data.Manager;

import java.io.*;

/**
 * Created by ali on 2/9/17.
 */
public class Server {
    static Logger logger = Logger.getLogger(Server.class);
    public static void main(String[] args) throws IOException {
        logger.debug("Server Started!");

        ServerTransceiver server = new ServerTransceiver(8083);
//        ServerTransceiver server = new ServerTransceiver(portNumber);
        System.out.println("Server is running");
        server.accept();
        Manager manager =  Manager.getInstance();
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
                            String[] person = personInfo.split("\\s+");
                            crq.addPerson(person, "adult");
                        }
                        for (int i = 0; i < crq.childs; i++) {
                            String personInfo = server.receive();
                            String[] person = personInfo.split("\\s+");
                            crq.addPerson(person, "child");
                        }
                        for (int i = 0; i < crq.infants; i++) {
                            String personInfo = server.receive();
                            String[] person = personInfo.split("\\s+");
                            crq.addPerson(person, "infant");
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