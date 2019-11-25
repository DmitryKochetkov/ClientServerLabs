import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int port = 12345;

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(port, 1);
        System.out.println("Server started!");

        Socket socket = server.accept();
        System.out.println("Connection established!");

        char c = 'c';

        socket.getOutputStream().write(c);

    }
}
