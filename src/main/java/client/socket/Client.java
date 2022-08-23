package client.socket;

import client.Start;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class Client extends Thread{
    private Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private ArrayList<String> requests = new ArrayList<>();
    private Thread th = this;
    private ArrayList<String> sendLsBuffer = new ArrayList<>();

    public void startConnection(String ip, int port) throws IOException, InterruptedException {
        clientSocket = new Socket(ip, port);
        output = new PrintWriter(clientSocket.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        th.start();
    }
    public void run(){
        while (true){
            try {
                requests.add(listen());
                parse_request();
            } catch (IOException e) {
                e.printStackTrace();
                th.stop();
            }
        }
    }

    public void parse_request() throws IOException {

        int index = 0;
        while (index < requests.size()){
            String str = requests.get(index);
            String[] command = str.split("!");
            if (Objects.equals(command[0], "connection")){
                System.out.println("igemdvl,f; " + str);
                if (Objects.equals(Start.state, "connect to server")){
                    if (Objects.equals(command[1], "allowed")){
                        Start.id = Integer.parseInt(command[2]);
                        Start.state = "start game";
                        Start.log("connected");
                    }
                }
            }
            if (Objects.equals(command[0], "startSendLs")) {
                Start.log(">");
            }
            if (Objects.equals(command[0], "sendLs")) {
                sendLsBuffer.add(command[1]);
            }
            if (Objects.equals(command[0], "sendLsDone")) {
                for (int i = 0; i < sendLsBuffer.size(); i++) {
                    Start.log(sendLsBuffer.get(i));
                }
                sendLsBuffer.clear();
            }
            if (Objects.equals(command[0], "sendPrintPath")) {
                Start.log(">" + command[1]);
                Start.log("");
            }
            if (Objects.equals(command[0], "sendPrintln")) {
                Start.log(command[1]);
            }
            requests.remove(index);
            index = index + 1;
        }

    }

    public void sendMessage(String msg) throws IOException {
        output.println(msg);
    }

    public void stopConnection() throws IOException {
        input.close();
        output.close();
        clientSocket.close();
    }
    public String listen() throws IOException {
        String str = input.readLine();
        return str;
    }
}
