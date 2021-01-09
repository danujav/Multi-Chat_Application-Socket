package Client_Side;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 5000)) {

            //reading the input from server
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            /*returning the output to the server : true statement is to flush the buffer otherwise
            we have to it manually*/
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            //taking the user Input
            Scanner scanner = new Scanner(System.in);
            String userInput;
            String response;
            String clientName = "empty";

            ClientRunnable clientRunnable = new ClientRunnable(socket);

            new Thread(clientRunnable).start();
            //loop closes when user enters exit command

            do{
                if(clientName.equals("empty")){
                    System.out.println("Enter your name ");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                    output.println(userInput);
                    if(userInput.equals("exit")){
                        break;
                    }
                }
                else{
                    String message = ( "(" + clientName + ")" + "message : ");
                    System.out.println(message);
                    userInput = scanner.nextLine();
                    output.println(message + " " + userInput);
                    if(userInput.equals("exit")){
                        //reading the input from server
                        break;
                    }
                }
            } while(!userInput.equals("exit"));

            socket.close();
            input.close();
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}