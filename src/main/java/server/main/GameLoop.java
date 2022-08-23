package server.main;

import server.Start;
import server.socket.Client;
import server.socket.Server;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class GameLoop extends Thread {

    public void run(){
        while (true){
            try {
                loop();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void loop() throws IOException {
        Integer index = 0;
        while (index < Server.clients.size()) {
            Client client = Server.clients.get(index);
            Integer index2 = 0;
            while (index2 < client.requests.size()) {
                String request = client.requests.get(index2);
                String[] command = request.split("!");
                if (Objects.equals(command[0], "cd")) {
                    if (command.length > 1){
                        String path = client.current_path + command[1];
                        File directoryPath = new File(path);
                        String contents[] = directoryPath.list();
                        if (!(contents == null)) {
                            client.current_path = path + "/";
                            client.sendMessage("sendPrintPath!" + client.current_path);
                        }else{
                            client.sendMessage("sendPrintln!Path is not correct.");
                            client.sendMessage("sendPrintPath!" + client.current_path);
                        }

                    }else{
                        client.sendMessage("sendPrintPath!" + client.current_path);
                    }
                }
                if (Objects.equals(command[0], "setPath")) {
                    if (command.length > 1){
                        String path = command[1];
                        File directoryPath = new File(path);
                        String contents[] = directoryPath.list();
                        if (!(contents == null)) {
                            client.current_path = path;
                            client.sendMessage("sendPrintPath!" + client.current_path);
                        }else{
                            client.sendMessage("sendPrintln!Path is not correct.");
                            client.sendMessage("sendPrintPath!" + client.current_path);
                        }

                    }else{
                        client.sendMessage("sendPrintPath!" + client.current_path);
                    }
                }
                if (Objects.equals(command[0], "rm")) {
                    if (command.length > 1){
                        String path = client.current_path + command[1];
                        File filePath = new File(path);
                            if (filePath.delete()){
                                client.sendMessage("sendPrintln!Command sent.");
                                client.sendMessage("sendPrintPath!" + client.current_path);
                            }else{
                                client.sendMessage("sendPrintln!Could not remove.");
                                client.sendMessage("sendPrintPath!" + client.current_path);
                            }
                    }else{
                        client.sendMessage("sendPrintPath!" + client.current_path);
                    }
                }
                if (Objects.equals(command[0], "ls")) {
                    String path = "";
                    if (command.length > 1){
                        path = command[1];
                    }else{
                        path = client.current_path;
                    }
                    File directoryPath = new File(path);
                    String contents[] = directoryPath.list();
                    if (!(contents == null)) {
                        client.sendMessage("startSendLs");
                        for (int i = 0; i < contents.length; i++) {
                            client.sendMessage("sendLs!" + contents[i]);
                        }
                        client.sendMessage("sendLsDone");
                        client.sendMessage("sendPrintPath!" + client.current_path);
                    }else{
                        client.sendMessage("sendPrintPath!" + client.current_path);
                    }
                }
                index2 = index2 + 1;
            }
            client.requests.clear();
            index = index + 1;
        }
        }
    }