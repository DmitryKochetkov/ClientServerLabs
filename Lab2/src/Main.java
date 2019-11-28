import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            HttpServer httpServer = new HttpServer(8080);
            System.out.println("Server started!\n");

            while (httpServer.ClientHandle());
            System.out.println("Server stopped.");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
