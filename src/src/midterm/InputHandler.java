package midterm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class InputHandler implements Runnable {

    private Socket socket;
    private InputStream inStream;
    private InputStreamReader r;
    private BufferedReader br;
    private Client client;
    private ObjectInputStream objInStream;

    public InputHandler(Socket inSocket, Client inClient) {
        socket = inSocket;
        client = inClient;
    }
    
    @Override
    public void run() {
        while (true) {
            try {
                while (true) {
                    inStream = socket.getInputStream();
                    objInStream = new ObjectInputStream(socket.getInputStream());
                    r = new InputStreamReader(inStream);
                    br = new BufferedReader(r);
                    while (br.ready()) {
                        
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
