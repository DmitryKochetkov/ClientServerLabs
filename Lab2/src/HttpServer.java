import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer {
    private int port;
    private ServerSocket server;
    private Socket socket;
    private OutputStream writer;
    private String request;

    public HttpServer(int port) throws IOException {
        this.port = port;
        this.server = new ServerSocket(port);
    }

    private void response400() throws IOException {
        String responseHeader =
                "HTTP/1.1 400 Bad Request\r\n";
        writer.write(responseHeader.getBytes());
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

            String responseHeader;
            String responseDoc;
            byte[] b;

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
                            response = "<p>Response: " + new Calculator(arg).getResult() + "</p>";
                            responseDoc =
                                    "<head><meta charset=\"UTF-8\"></head>" +
                                            "<html><body>" +
                                            "<p>Кочетков Дмитрий Андреевич, ИКБО-02-17</p>" + response +
                                            "</body></html>";
                            response200(responseDoc);
                        } else {
                            response400();
                            break;
                        }
                        break;

                    case "favicon.ico":
                        /*
                        responseHeader =
                                "HTTP/1.1 200 OK\r\n\t" +
                                        "Content-Type: text/html; charset=UTF-8\r\n\t" +
                                        "Content-Length: " + 0 +
                                        "\r\n\r\n";

                        socket.getOutputStream().write(responseHeader.getBytes());
                        System.out.println("Response header:\n\t" + responseHeader + "\n");
                        */
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
