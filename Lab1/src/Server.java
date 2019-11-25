import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    final static int port = 12345;

    public static void main(String[] args) throws IOException {

        ServerSocket server = new ServerSocket(port, 1);
        System.out.println("Server started!");

        //Character request = new BufferedReader(new InputStreamReader(socket.getInputStream())).readLine().charAt(0);
        try {
            Socket socket = server.accept();
            System.out.println("Connection to client established!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String request = reader.readLine();
            System.out.println("Request: " + request);
            System.out.println("Evaluating response...");

            String response = Double.toString(new Calculator(request).getResult());

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write(response);
            writer.newLine();
            writer.flush();
            System.out.println("Response " + response + " sent to client.");
            //new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())).write(request);
            //new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())).write(Double.toString(new Calculator(request).getResult()));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
