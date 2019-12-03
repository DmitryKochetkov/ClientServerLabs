import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer implements Runnable {
    private int port;
    private ServerSocket server;
    private Socket socket;
    private OutputStream writer;
    private String request;

    private Thread thread;

    public HttpServer(int port) throws IOException {
        this.port = port;
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
            response400();
            return false;
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

            if (param.matches("calculate/.*")) {
                arg = param.substring("calculate/".length());
                param = "calculate";
            }

            System.out.println("Method: " + method);
            if (!param.equals(""))
                System.out.println("Param: " + param);
            else System.out.println("Param is empty.");

            String responseDoc;

            if (method.equals("GET")) {
                String response;
                switch (param) {
                    case "":
                        response = "";
                        String myHTMLDoc =
                                "<head><meta charset=\"UTF-8\"></head>" +
                                        "<html><body>" +
                                        "<p>Кочетков Дмитрий Андреевич, ИКБО-02-17</p>" + response +
                                        "</body></html>";
                        response200(myHTMLDoc);
                        break;

                    case "calculate":
                        if (!arg.equals("")) {
                            try {
                                response = "<p>Response: " + new Calculator(arg).getResult() + "</p>";
                                responseDoc =
                                        "<head><meta charset=\"UTF-8\"></head>" +
                                                "<html><body>" +
                                                "<p>Кочетков Дмитрий Андреевич, ИКБО-02-17</p>" + response +
                                                "</body></html>";
                                response200(responseDoc);
                            }
                            catch (RuntimeException e) {
                                response500();
                            }
                        } else {
                            response400();
                            break;
                        }
                        break;

                    case "favicon.ico":
                        response200();
                        break;

                    default:
                        response400();;
                        break;
                }
            } else {
                response400();
            }

            socket.close();
            System.out.println("Connection closed.\n");
            return true;
        }
    }
}
