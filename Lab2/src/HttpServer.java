import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpServer {
    final static int port = 8080;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started!\n");

            while (true) {
                Socket socket = server.accept();
                System.out.println("Connection established!");

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String request = reader.readLine();
                System.out.println("Request: " + request);

                if (request == null)
                {
                    String responseHeader =
                            "HTTP/1.1 403 Bad Request\r\n";
                    socket.getOutputStream().write(responseHeader.getBytes());
                    System.out.println("Response header:\n" + responseHeader + " (null request)" + "\n");
                    break;
                }

                Pattern pattern = Pattern.compile("(.*) \\/(.*) HTTP\\/1\\.1");
                Matcher matcher = pattern.matcher(request);

                String method = "";
                String param = "";

                if (matcher.find()) {
                    method = matcher.group(1);
                    param = matcher.group(2);
                }

                System.out.println("Method: " + method);
                if (!param.equals(""))
                    System.out.println("Param: " + param);
                else System.out.println("Param is empty.");

                String responseHeader;
                String responseDoc;
                byte[] b;

                if (method.equals("GET"))
                {
                    String response;
                    switch (param)
                    {
                        case "":
                            response = "";
                            responseDoc =
                                    "<head><meta charset=\"UTF-8\"></head>" +
                                            "<html><body>" +
                                            "<p>Кочетков Дмитрий Андреевич, ИКБО-02-17</p>" + response +
                                            "</body></html>";
                            b = responseDoc.getBytes();

                            responseHeader =
                                    "HTTP/1.1 200 OK\r\n\t" +
                                            "Content-Type: text/html; charset=UTF-8\r\n\t" +
                                            "Content-Length: " + b.length +
                                            "\r\n\r\n";


                            socket.getOutputStream().write(responseHeader.getBytes());
                            socket.getOutputStream().write(b);

                            System.out.println("Response header:\n\t" + responseHeader + "\n");
                            System.out.println("Response body is HTML document.");
                            break;

                        case "calculate/*": //parsing implementation required
                            response = "<p>Response: calculation result</p>";
                            responseDoc =
                                    "<head><meta charset=\"UTF-8\"></head>" +
                                    "<html><body>" +
                                    "<p>Кочетков Дмитрий Андреевич, ИКБО-02-17</p>" + response +
                                    "</body></html>";
                            b = responseDoc.getBytes();

                            responseHeader =
                                    "HTTP/1.1 200 OK\r\n\t" +
                                            "Content-Type: text/html; charset=UTF-8\r\n\t" +
                                            "Content-Length: " + b.length +
                                            "\r\n\r\n";


                            socket.getOutputStream().write(responseHeader.getBytes());
                            socket.getOutputStream().write(b);

                            System.out.println("Response header:\n\t" + responseHeader + "\n");
                            break;

                        case "favicon.ico":
                            responseHeader =
                                    "HTTP/1.1 200 OK\r\n\t" +
                                            "Content-Type: text/html; charset=UTF-8\r\n\t" +
                                            "Content-Length: " + 0 +
                                            "\r\n\r\n";

                            socket.getOutputStream().write(responseHeader.getBytes());
                            System.out.println("Response header:\n\t" + responseHeader + "\n");
                            break;

                        default:
                            responseHeader =
                                    "HTTP/1.1 400 Bad Request\r\n";
                            socket.getOutputStream().write(responseHeader.getBytes());
                            System.out.println("Response header:\n\t" + responseHeader + "\n");
                            break;
                    }
                }
                else
                {
                    responseHeader =
                            "HTTP/1.1 400 Bad Request\r\n";
                    socket.getOutputStream().write(responseHeader.getBytes());
                    System.out.println("Response header:\n\t" + responseHeader + "\n");
                }

                socket.close();
                System.out.println("Connection closed.\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Server stopped.");
    }
}
