package midterm;

import blackjack.message.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class OutputHandler {
    
    private PrintWriter stringOutput;
    private ObjectOutputStream objectOutput;
    
    public OutputHandler(Socket inSocket){
        try{
        stringOutput = new PrintWriter(inSocket.getOutputStream());
        objectOutput = new ObjectOutputStream(inSocket.getOutputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void outputUser(LoginMessage inString){
        try{
        objectOutput.writeObject(inString);
        objectOutput.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public void outputChat(ChatMessage inMessage){
        try{
            objectOutput.writeObject(inMessage);
            objectOutput.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
