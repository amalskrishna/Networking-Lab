import java.io.*;
import java.net.*;

public class exp7client {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 5000);

        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Thread to receive messages
        Thread receive = new Thread(() -> {
            try {
                String msg;
                while ((msg = serverInput.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from server.");
            }
        });

        receive.start();

        // Send messages
        while (true) {
            String msg = keyboard.readLine();
            out.println(msg);

            if (msg.equalsIgnoreCase("exit")) {
                socket.close();
                break;
            }
        }
    }
}
