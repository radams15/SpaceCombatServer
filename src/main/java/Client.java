import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class Client extends Thread{

    private Socket sock;

    private Message currentMessage;

    private final BufferedReader reader;
    private final PrintWriter writer;

    private Gson gson = new Gson();

    public Client(Socket sock) throws IOException { // handle exceptions outside of the class
        this.sock = sock;

        writer = new PrintWriter(sock.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

    }

    public void run(){
        while(true){
            String raw = receive();
            if(raw == null){
                break;
            }

            currentMessage = gson.fromJson(raw, Message.class);


            String toSend = gson.toJson(currentMessage);
            send(toSend);
        }
    }

    private void send(String message) {
        writer.write(message+"\n");
        writer.flush();
    }

    private String receive() {
        try{
            String data = reader.readLine();
            return data;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        try {
            reader.close();
            writer.close();
            sock.close();
        }catch (IOException ignored){} // nothing bad really happens if they are not closed, ignore it, we can't do anything
    }
}
