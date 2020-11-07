import java.io.*;
import java.net.Socket;

public class Client {

    private Socket sock;

    private final BufferedReader reader;
    private final PrintWriter writer;

    public Client(Socket sock) throws IOException { // handle exceptions outside of the class
        this.sock = sock;

        writer = new PrintWriter(sock.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
    }

    public void send(String message) {
        writer.write(message+"\n");
    }

    public String receive() {
        try{
            String data = reader.readLine();
            return data;
        }catch(IOException e){
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
