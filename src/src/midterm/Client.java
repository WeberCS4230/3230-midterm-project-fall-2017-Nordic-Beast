package midterm;

import blackjack.message.ChatMessage;
import blackjack.message.LoginMessage;
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

    /*  
    Creates a socket to connect to the server.
    Runs a new InputHandler thread to accept objects from the server.
    Creates an UI object for the user
    Creates an OutputHandler to send objects to the server.
     */
    public Client() {
        try {
            socket = new Socket("ec2-54-172-123-164.compute-1.amazonaws.com", 8989);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (socket.isConnected()) {
            System.out.println("connected");
            new Thread(new InputHandler(socket, this)).start();
            ui = new UIWindow(this);
            messenger = new OutputHandler(socket);
        }
    }

    /*
    Sends a username to the server.
     */
    public void setUsername() {
        user = ui.getUsername();
        myUsername = MessageFactory.getLoginMessage(user);
        messenger.outputUser(myUsername);
    }

    /*
    If the username is denied, asks user for a different username.
     */
    public void setUser(StatusMessage deny) {
        user = ui.retryUsername();
        myUsername = MessageFactory.getLoginMessage(user);
        messenger.outputUser(myUsername);
    }

    /*
    sends a ChatMessage object to the server
     */
    void sendChat(String chat) {
        if (null != chat) {
            chatOut = MessageFactory.getChatMessage(chat, user);
            messenger.outputChat(chatOut);
        }
        else {
            ui.localAppend("You can't send an empty message!");
        }
    }

    /*
    When a ChatMessage is received, appends the object to the chat window.
     */
    void updateChat(ChatMessage message) {
        ui.appendChat(message);
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.setUsername();
    }

}
