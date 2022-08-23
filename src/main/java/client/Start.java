package client;

import client.socket.Client;

import java.io.IOException;
import java.util.Scanner;

public class Start {
    public static String text = "ddd";

    public static String state = "connect to server";
    public static Integer id = 0;
    public static Client client = new Client();
    public static String ip = "";
    public static String port = "";


    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        System.out.println("ip");
        String answer = input.nextLine();
        ip = answer;
        input = new Scanner(System.in);
        System.out.println("port");
        answer = input.nextLine();
        port = answer;

        client.startConnection(ip, Integer.parseInt(port));

        while(true){
            input = new Scanner(System.in);
            System.out.println(">");
            answer = input.nextLine();
            String[] commands = answer.split(" ");
            if (commands[0].equals("ls")){
                if (commands.length > 1){
                    client.sendMessage("ls!" + commands[1]);
                }else{
                    client.sendMessage("ls");
                }
            }
            if (commands[0].equals("cd")){
                if (commands.length > 1){
                    client.sendMessage("cd!" + commands[1]);
                }else{
                    client.sendMessage("cd");
                }
            }
            if (commands[0].equals("rm")){
                if (commands.length > 1){
                    client.sendMessage("rm!" + commands[1]);
                }else{
                    client.sendMessage("rm");
                }
            }
            if (commands[0].equals("setPath")){
                if (commands.length > 1){
                    client.sendMessage("setPath!" + commands[1]);
                }else{
                    client.sendMessage("setPath");
                }
            }
        }
    }
    public static void log(String log){
        System.out.println(log);
    }
}
