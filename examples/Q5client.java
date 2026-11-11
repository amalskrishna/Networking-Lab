import java.net.*;
import java.util.*;

public class Q5client {
    public static void main(String args[]) throws Exception{
        DatagramSocket ds = new DatagramSocket();
        System.out.print("Enter a sentence: ");
        Scanner sc = new Scanner(System.in);
        String sentence = sc.nextLine();

        byte send[] = sentence.getBytes();
        InetAddress ip = InetAddress.getByName("localhost");
        DatagramPacket dp = new DatagramPacket(send, send.length, ip, 5000);
        ds.send(dp);

        byte rec[] = new byte[1024];
        DatagramPacket dp2 = new DatagramPacket(rec, rec.length);
        ds.receive(dp2);

        String result = new String(dp2.getData(), 0, dp2.getLength());
        System.out.println(result);
        sc.close();
        ds.close();
    }
}
