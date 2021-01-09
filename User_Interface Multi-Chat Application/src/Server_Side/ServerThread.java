package Server_Side;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;
    private ArrayList<ServerThread> threadArrayList;
    private PrintWriter output;

    public ServerThread(){}

    public ServerThread(Socket socket, ArrayList<ServerThread> threads){
        this.socket = socket;
        this.threadArrayList = threads;
    }

    @Override
    public void run(){
        try{
            //Reading the input from Client
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            /*returning the output to the client : true statement is to flush the buffer otherwise
            we have to do it manually*/
            output = new PrintWriter(socket.getOutputStream(), true);

            //infinite loop for server
            while(true){
                String outputString = input.readLine();

                //if user type exit command then program will terminate
                if(outputString.equals("exit")){
                    break;
                }
                printToAllClients(outputString);
                System.out.println("Server received " + outputString);
            }
        } catch (Exception e){
            System.out.println("Error occurred " + e.getStackTrace());
        }
    }

    private void printToAllClients(String outputString) {
        for (ServerThread sT : threadArrayList) {
            sT.output.println(outputString);
        }
    }
}