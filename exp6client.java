import java.io.*;
import java.util.*;
import java.net.*;

class exp6client{
	public static void main(String[] args) throws Exception{
		DatagramSocket ds = new DatagramSocket();
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter a sentence : ");
		String msg = sc.nextLine();
		byte send[] = msg.getBytes();
		
		InetAddress ip = InetAddress.getByName("localhost");
		DatagramPacket dp = new DatagramPacket(send, send.length, ip, 5000);
		ds.send(dp);
		
		byte[] buffer = new byte[1024];
		DatagramPacket rep = new DatagramPacket(buffer,buffer.length);
		ds.receive(rep);
		
		String res = new String(rep.getData(),0,rep.getLength());
		System.out.printf("\nTranslated formal english below ........\n\n");
		System.out.println(res);
	}
}
