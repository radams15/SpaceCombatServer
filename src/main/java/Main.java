import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    private final int port = 8000;

    private final ArrayList<Client> clients = new ArrayList<>();

    public void acceptLoop(ServerSocket server){
        while(true){
            try {
                Socket sock = server.accept();
                Client client = new Client(sock);
                client.start();

                clients.add(client);

            }catch (IOException ignored){ignored.printStackTrace();} // just ignore any failures, they can just connect again
        }
    }

    public Main() {
        try {
            ServerSocket server = new ServerSocket(port);

            acceptLoop(server);

            server.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args){
        new Main();
    }
}
