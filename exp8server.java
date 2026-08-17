import java.net.*;
import java.util.Date;

public class exp8server {
    public static void main(String[] args) throws Exception{
            DatagramSocket socket = new DatagramSocket(7000);
            byte[] buffer = new byte[1024];

            System.out.println("Time Server is running...");

            while (true) {
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                socket.receive(dp);

                String request = new String(dp.getData(), 0, dp.getLength());
                System.out.println("Request received: " + request);

                String currentTime = new Date().toString();
                byte[] sendData = currentTime.getBytes();

                InetAddress clientAddress = dp.getAddress();
                int clientPort = dp.getPort();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                socket.send(sendPacket);
            }
        
    }
}
