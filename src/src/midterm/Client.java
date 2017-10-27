package midterm;

import java.io.IOException;
import java.net.Socket;

public class Client {
    
    private Socket socket;
    
    public Client(){
        try{
        socket = new Socket("ec2-54-91-0-253.compute-1.amazonaws.com", 8989);
        } catch (IOException e){
            e.printStackTrace();
        }
        
        if (socket.isConnected()){
            System.out.println("connected");
        }
    }
    
    public static void main(String[] args) {
        new Client();
    }
}
