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
                    //method = request.substring(0, request.indexOf(" "));
                    method = matcher.group(1);
                    param = matcher.group(2);
                    //param = matcher.group(1);
                }

                System.out.println("Method: " + method);
                if (!param.equals(""))
                    System.out.println("Param: " + param);
                else System.out.println("Param is empty.");

                String responseHeader;

                if (method.equals("GET"))
                {
                    switch (param)
                    {
                        case "":
                            String responseDoc = "";
                            String path = "E:\\Programming\\ClientServerLabs\\Lab2\\index.html";
                            try (FileReader fileReader = new FileReader(path))
                            {
                                int c = 0;
                                while ((c = fileReader.read()) != -1)
                                    responseDoc += (char)c;
                            }
                            catch (IOException e)
                            {
                                System.out.println(e.getMessage());
                            }

                            byte[] b = responseDoc.getBytes();

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

                        default: //нужно?
                            responseHeader =
                                    "HTTP/1.1 403 Bad Request\r\n";
                            socket.getOutputStream().write(responseHeader.getBytes());
                            System.out.println("Response header:\n\t" + responseHeader + "\n");
                            break;
                    }
                }
                else
                {
                    responseHeader =
                            "HTTP/1.1 403 Bad Request\r\n";
                    socket.getOutputStream().write(responseHeader.getBytes());
                    System.out.println("Response header:\n\t" + responseHeader + "\n");
                }

                socket.close();
                System.out.println("Connection closed.\n");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
