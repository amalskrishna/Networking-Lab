import java.io.*;
import java.net.*;
import java.util.*;

public class exp7server {

    static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected.");

            ClientHandler client = new ClientHandler(socket);
            clients.add(client);
            client.start();
        }
    }

    static class ClientHandler extends Thread {
        Socket socket;
        BufferedReader in;
        PrintWriter out;
        String name;

        ClientHandler(Socket socket) throws IOException {
            this.socket = socket;
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        }

        public void run() {
            try {
                out.println("Enter your name:");
                name = in.readLine();

                broadcast(name + " joined the chat.");

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("exit"))
                        break;

                    broadcast(name + ": " + message);
                }

                broadcast(name + " left the chat.");

                clients.remove(this);
                socket.close();

            } catch (Exception e) {
                System.out.println("Client disconnected.");
            }
        }

        void broadcast(String message) {
            for (ClientHandler client : clients) {
                client.out.println(message);
            }
        }
    }
}
