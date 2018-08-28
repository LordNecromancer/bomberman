import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sun on 08/06/2018.
 */
public class Server {
    int port;
    boolean isStopped = false;

    public Server(int port) throws IOException {

        this.port = port;
        startServer();

    }

    private void startServer() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        // while (!isStopped){
        Socket socket = serverSocket.accept();
        System.out.println("jjjj");

        PrintStream outputStream = new PrintStream(socket.getOutputStream());
        outputStream.write("Hi".getBytes());
        outputStream.flush();
        socket.close();
        serverSocket.close();
        //  }
    }
}
