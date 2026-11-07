import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String args[]) throws Exception{
        DatagramSocket ds = new DatagramSocket();
        Scanner sc = new Scanner(System.in);
        String msg = sc.nextLine();

        byte[] send = msg.getBytes();
        InetAddress ip = InetAddress.getByName("localhost");
        DatagramPacket dp = new DatagramPacket(send, send.length, ip , 5000);
        ds.send(dp);

        byte[] buffer = new byte[1024];
        DatagramPacket rec = new DatagramPacket(buffer, buffer.length);
        ds.receive(rec);
        String output = new String(rec.getData(),0,rec.getLength());
        System.out.println(output);

        sc.close();
        ds.close();
    }
}
