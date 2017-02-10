package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ali on 2/10/17.
 */
public class ServerTransceiver {
    private ServerSocket serverSocket;
    private Socket socket;

    public ServerTransceiver(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();
    }


    public String receive() throws IOException {
        String res = "";
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line;
        while((line = input.readLine()) != null) {
            res+= line +'\n';
            if(!input.ready())
                break;
        }
        return res;
    }

    public void send(String data) throws IOException {
        PrintStream output = new PrintStream(socket.getOutputStream());
        output.println(data);
        output.flush();
    }

    public void close() throws IOException {
        this.socket.close();
        this.serverSocket.close();
    }

}