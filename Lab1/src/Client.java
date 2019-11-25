import org.omg.CORBA_2_3.portable.OutputStream;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    final static int port = 12345;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 12345);
        System.out.println("Connection to server established!");

        System.out.println("Input:");
        String request = new Scanner(System.in).nextLine();

        new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())).write(request);
        System.out.println("Request has been sent to server.");

        String response = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine();
        System.out.println("Response from server: " + response);
    }
}
