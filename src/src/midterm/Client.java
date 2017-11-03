package midterm;

import blackjack.message.ChatMessage;
import blackjack.message.LoginMessage;
import blackjack.message.Message;
import blackjack.message.MessageFactory;
import blackjack.message.StatusMessage;
import java.io.IOException;
import java.net.Socket;

public class Client {
    
    private Socket socket;
    private UIWindow ui;
    private String user;
    private OutputHandler messenger;
    private LoginMessage myUsername;
    private ChatMessage chatOut;
    
    public Client(){
        try{
        socket = new Socket("ec2-54-172-123-164.compute-1.amazonaws.com", 8989);
        } catch (IOException e){
            e.printStackTrace();
        }
        
        if (socket.isConnected()){
            System.out.println("connected");
            new Thread(new InputHandler(socket, this)).start();
            ui = new UIWindow(this);
            messenger = new OutputHandler(socket);
        }
    }
    
    public void setUsername(){
        user = ui.getUsername();
        myUsername = MessageFactory.getLoginMessage(user);
        messenger.outputUser(myUsername);
    }
    
    public void setUser(StatusMessage deny){
        user = ui.retryUsername();
        myUsername = MessageFactory.getLoginMessage(user);
        messenger.outputUser(myUsername);
    }
    
    void sendChat(String chat){
        chatOut = MessageFactory.getChatMessage(chat, user);
        messenger.outputChat(chatOut);
    }
    
    void updateChat(ChatMessage message) {
        ui.appendChat(message);
    }
    
    public static void main(String[] args) {
        Client client = new Client();
        client.setUsername();
    }

    
}
