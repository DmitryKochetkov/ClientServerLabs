import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer implements Runnable {
    private int port;
    private ServerSocket server;
    private Socket socket;
    private OutputStream writer;
    private String request;

    private Thread thread;

    //Data
    private List<User> data = new ArrayList<>();

    public HttpServer(int port) throws IOException {
        this.port = port;
        data.add(new User("Dmitry", "Kochetkov", 'm', "dimedrol", "228"));
        data.add(new User("Egor", "Kuznetsov", 'm', "heywood_floyd", "Sonya"));
    }

    private void response400() throws IOException {
        String HTMLBody = "<html><h1>400 Bad Request</h1></html>";
        byte[] bytes = HTMLBody.getBytes();
        String responseHeader =
                "HTTP/1.1 400 Bad Request\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n\t" +
                        "Content-Length: " + bytes.length +
                        "\r\n\r\n";
        writer.write(responseHeader.getBytes());
        writer.write(bytes);
        System.out.println("Response header:\n" + responseHeader + "\n");
    }

    private void response200() throws IOException {
        String responseHeader =
                "HTTP/1.1 200 OK\r\n";
        writer.write(responseHeader.getBytes());
        System.out.println("Response header:\n" + responseHeader + "\n");
    }

    private void response200(String HTMLBody) throws IOException {
        byte[] bytes = HTMLBody.getBytes();
        String responseHeader =
                "HTTP/1.1 200 OK\r\n\t" +
                        "Content-Type: text/html; charset=UTF-8\r\n\t" +
                        "Content-Length: " + bytes.length +
                        "\r\n\r\n";
        writer.write(responseHeader.getBytes());
        writer.write(bytes);
        System.out.println("Response header:\n" + responseHeader + "\n");
        System.out.println("Response body is HTML document.");
    }

    private void response200(byte[] bytes) throws IOException {
        String responseHeader =
                "HTTP/1.1 200 OK\r\n\t" +
                        "Content-Type: text/html; charset=UTF-8\r\n\t" +
                        "Content-Length: " + bytes.length +
                        "\r\n\r\n";
        writer.write(responseHeader.getBytes());
        writer.write(bytes);
        System.out.println("Response header:\n" + responseHeader + "\n");
        System.out.println("Response body is HTML document.");
    }

    private void response500() throws IOException {
        String HTMLBody = "<html><h1>500 Internal Server Error</h1></html>";
        byte[] bytes = HTMLBody.getBytes();
        String responseHeader =
                "HTTP/1.1 500 Internal Server Error\r\n" +
                        "Content-Type: text/html; charset=UTF-8\r\n\t" +
                        "Content-Length: " + bytes.length +
                        "\r\n\r\n";
        writer.write(responseHeader.getBytes());
        writer.write(bytes);
        System.out.println("Response header:\n" + responseHeader + "\n");
    }

    @Override
    public void run() {
        synchronized (this) {
            this.thread = Thread.currentThread();
            try {
                this.server = new ServerSocket(port);
            }
            catch (IOException e)
            {
                //????
            }
            System.out.println("Server started!\n");

            try {
                while (ClientHandle()) ;
            }
            catch (IOException e)
            {
                //????
            }
            System.out.println("Server stopped.");
        }
    }

    public boolean ClientHandle() throws IOException {
        this.socket = server.accept();
        System.out.println("Connection established!");
        this.writer = socket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        request = reader.readLine();
        System.out.println("Request: " + request);

        writer = socket.getOutputStream();

        if (request == null) {
            response200();
        }
        else {
            Pattern pattern = Pattern.compile("(.*) \\/(.*) HTTP\\/1\\.1");
            Matcher matcher = pattern.matcher(request);

            String method = "";
            String param = "";
            String arg = "";


            if (matcher.find()) {
                method = matcher.group(1);
                param = matcher.group(2);
            }

            if (param.matches("calculator/.*")) {
                arg = param.substring("calculator/".length());
                param = "calculator";
            }

            if (param.matches("table/.*")) {
                arg = param.substring("table/".length());
                param = "table";
            }

            System.out.println("Method: " + method);
            if (!param.equals(""))
                System.out.println("Param: " + param);
            else System.out.println("Param is empty.");

            if (method.equals("GET")) {
                switch (param) {
                    case "":
                        response200(Files.readAllBytes(Paths.get("E:\\Programming\\ClientServerLabs\\Lab3\\src\\web_content\\index.html")));
                        break;

                    case "calculator":
                        response200(Files.readAllBytes(Paths.get("E:\\Programming\\ClientServerLabs\\Lab3\\src\\web_content\\calculator.html")));
                        break;

                    case "table":
                        response200(Files.readAllBytes(Paths.get("E:\\Programming\\ClientServerLabs\\Lab3\\src\\web_content\\table.html")));
                        break;

                    case "get_table":
                        String response = "";
                        for (User user: data) {
                            response += user.toString() + '\n';
                        }
                        response200(response);
                        break;

                    case "favicon.ico":
                        response200();
                        break;

                    case "style.css":
                        response200(Files.readAllBytes(Paths.get("E:\\Programming\\ClientServerLabs\\Lab3\\src\\web_content\\style.css")));
                        break;

                    case "calc_style.css":
                        response200(Files.readAllBytes(Paths.get("E:\\Programming\\ClientServerLabs\\Lab3\\src\\web_content\\calc_style.css")));
                        break;

                    case "calc.js":
                        response200(Files.readAllBytes(Paths.get("E:\\Programming\\ClientServerLabs\\Lab3\\src\\web_content\\calc.js")));
                        break;

                    case "table.js":
                        response200(Files.readAllBytes(Paths.get("E:\\Programming\\ClientServerLabs\\Lab3\\src\\web_content\\table.js")));
                        break;

                    default:
                        response400();
                        break;
                }
            }
            else if (method.equals("POST")) {
                response200(Double.toString(new Calculator(param).getResult()).getBytes());
            }

            else {
                response400();
            }

            socket.close();
            System.out.println("Connection closed.\n");
        }
        return true;
    }
}
