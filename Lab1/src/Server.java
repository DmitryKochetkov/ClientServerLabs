import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int port = 12345;

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(port, 1);
        System.out.println("Server started!");

        Socket socket = server.accept();
        System.out.println("Connection to client established!");

        String request = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
        System.out.println("Request received. Evaluating response...");

        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())).write(Double.toString(new Calculator(request).getResult()));
    }
}
