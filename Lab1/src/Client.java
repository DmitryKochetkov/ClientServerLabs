import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    final static int port = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 12345);
        System.out.println("Connection to server established!");

        System.out.println("Input:");
        char request = new Scanner(System.in).nextLine().charAt(0);

        socket.getOutputStream().write(request);
        System.out.println("Request has been sent to server.");

        char response = (char) socket.getInputStream().read();
        System.out.println("Response from server: " + response);
    }
}
