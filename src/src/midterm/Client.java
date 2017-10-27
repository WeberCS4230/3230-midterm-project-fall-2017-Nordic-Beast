package midterm;

import blackjack.message.LoginMessage;
import blackjack.message.MessageFactory;
import java.io.IOException;
import java.net.Socket;

public class Client {
    
    private Socket socket;
    private UIWindow ui;
    private String user;
    private OutputHandler messenger;
    private LoginMessage myUsername;
    
    public Client(){
        try{
        socket = new Socket("ec2-54-91-0-253.compute-1.amazonaws.com", 8989);
        } catch (IOException e){
            e.printStackTrace();
        }
        
        if (socket.isConnected()){
            System.out.println("connected");
            new Thread(new InputHandler(socket, this)).start();
            ui = new UIWindow();
            messenger = new OutputHandler(socket);
        }
    }
    
    public void setUsername(){
        System.out.println("getting username");
        user = ui.getUsername();
        System.out.println("setting username object");
        myUsername = MessageFactory.getLoginMessage(user);
        System.out.println("sending usernemt to server");
        messenger.outputUser(myUsername);
    }
    
    public static void main(String[] args) {
        Client client = new Client();
        client.setUsername();
    }
}
