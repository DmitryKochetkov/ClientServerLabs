import java.io.IOException;

public class Lab2 {
    public static void main(String[] args) {
        try {
            HttpServer httpServer = new HttpServer(8000);
            new Thread(httpServer).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
