package midterm;

import blackjack.message.ChatMessage;
import blackjack.message.Message;
import blackjack.message.StatusMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.InputStream;
import java.io.ObjectInputStream;
import blackjack.message.Message.MessageType;

public class InputHandler implements Runnable {

    private Socket socket;
    private InputStream inStream;
    private InputStreamReader r;
    private BufferedReader br;
    private final Client client;
    private ObjectInputStream objInStream;
    private Object obj;
    private Message message;

    public InputHandler(Socket inSocket, Client inClient) {
        socket = inSocket;
        client = inClient;
    }

    @Override
    public void run() {
        while (true) {
            try {
                /*
                The BufferedReader is to check if the stream has something in it.
                The ObjectInputStream is what actually accepts things from the stream.
                 */
                objInStream = new ObjectInputStream(socket.getInputStream());
                while (true) {
                    inStream = socket.getInputStream();
                    r = new InputStreamReader(inStream);
                    br = new BufferedReader(r);
                    while (br.ready()) {
                        try {
                            obj = objInStream.readObject();
                        } catch (ClassNotFoundException cnf) {
                            //Shouldn't reach here, only reads stream with there's something.
                        }
                        message = (Message) obj;
                        System.out.println(message.getType());
                        if (message.getType() == MessageType.DENY){
                            client.setUser((StatusMessage) message);
                        }
                        if (message.getType() == MessageType.CHAT){
                            client.updateChat((ChatMessage) message);
                        }
                        if (message.getType() == MessageType.CARD){
                            
                        }
                        if (message.getType() == MessageType.GAME_ACTION){
                            
                        }
                        if (message.getType() == MessageType.GAME_STATE){
                            
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
