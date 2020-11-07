import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket sock;

    private final BufferedReader reader;
    private final PrintWriter writer;

    private Gson gson = new Gson();

    public Client(Socket sock) throws IOException { // handle exceptions outside of the class
        this.sock = sock;

        writer = new PrintWriter(sock.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));

        System.out.println("Connected to by "+sock.getInetAddress());

        while(true){
            State s = getState();
            if(s == null){
                break;
            }
            System.out.println(s);
        }
    }

    private State getState(){
        String raw = receive();

        if(raw == null){
            return null;
        }

        return gson.fromJson(raw, State.class);
    }

    private void send(String message) {
        writer.write(message+"\n");
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
