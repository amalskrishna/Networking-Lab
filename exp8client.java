import java.net.*;

public class exp8client {
    public static void main(String[] args) throws Exception{
            DatagramSocket socket = new DatagramSocket();

            InetAddress servaddr = InetAddress.getByName("localhost");

            String msg = "TIME";
            byte[] sendData = msg.getBytes();

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, servaddr, 7000);
            socket.send(sendPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String time = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("Current Server Time: " + time);

            socket.close();
    }
}
