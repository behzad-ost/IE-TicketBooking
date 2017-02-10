package client;

import common.Transceiver;

import java.io.*;
import java.net.Socket;

/**
 * Created by ali on 2/9/17.
 */

public class Client {
    public static void main(String[] args) throws IOException {
//        Transceiver transceiver = new Transceiver("188.166.78.119", 8081);
        Transceiver transceiver = new Transceiver("localhost", 8081);

        String cmd;
        BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in));
        cmd= inFromUser.readLine() + '\n';
        transceiver.send(cmd);

        String answer = transceiver.receive();
        System.out.println(answer);

        transceiver.close();
    }
}
