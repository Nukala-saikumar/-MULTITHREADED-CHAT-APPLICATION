import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true)) {

            // Thread to read messages from server
            new Thread(() -> {
                String msgFromServer;
                try {
                    while ((msgFromServer = input.readLine()) != null) {
                        System.out.println("Server: " + msgFromServer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Read messages from user and send to server
            String userMsg;
            while ((userMsg = userInput.readLine()) != null) {
                output.println(userMsg);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
