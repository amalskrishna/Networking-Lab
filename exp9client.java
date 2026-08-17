import java.io.*;
import java.net.*;

public class exp9client {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader keyboard = new BufferedReader(
                    new InputStreamReader(System.in));

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter file name: ");
            String fileName = keyboard.readLine();

            out.println(fileName);

            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
