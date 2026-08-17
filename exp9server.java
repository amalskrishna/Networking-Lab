import java.io.*;
import java.net.*;

public class exp9server {

    public static void main(String[] args) throws Exception{
            ServerSocket ss = new ServerSocket(5000);
            System.out.println("File Server is Running...");

            while (true) {
                Socket socket = ss.accept();
                new ClientHandler(socket).start();   // Concurrent
            }
        
    }
}

class ClientHandler extends Thread {
    Socket socket;

    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            String fileName = in.readLine();

            long pid = ProcessHandle.current().pid();
            out.println("Server PID: " + pid);

            File file = new File(fileName);

            if (file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String line;

                out.println("File Contents:");
                while ((line = fileReader.readLine()) != null) {
                    out.println(line);
                }
                fileReader.close();
            } else {
                out.println("File not found!");
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
