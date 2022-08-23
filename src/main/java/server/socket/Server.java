package server.socket;

import client.Start;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
    public static Thread th = new Server();
    public static ServerSocket serverSocket;
    public static ArrayList<Client> clients = new ArrayList<>();

    public void run(){
        try {
            startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startServer() throws IOException {
            serverSocket = new ServerSocket(6660);
        Start.log("running");

        try {
            while (true){
                Socket c = serverSocket.accept();
                Client client = new Client(c);
                clients.add(client);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
