package client;

import java.io.*;
import java.net.Socket;

/**
 * Created by ali on 2/9/17.
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("188.166.78.119", 8081);
//        Socket clientSocket = new Socket("localhost", 8081);

//        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
//        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter output = new PrintWriter(clientSocket.getOutputStream());

        output.write("AV THR MHD 05Feb\n");
        System.out.println("Wrote!");
        output.flush();

        String response;
        while ((response = input.readLine()) != null) {
            System.out.println(response);
        }

        System.out.println("Done!");
        output.close();
        input.close();
        clientSocket.close();
    }
}
