package Client_Side;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientRunnable implements Runnable{

    private Socket socket;
    private BufferedReader input;

    public ClientRunnable(Socket socket) throws IOException{
        this.socket = socket;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try{
            while(true){
                String response = input.readLine();
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                input.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}