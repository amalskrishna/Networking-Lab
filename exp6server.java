import java.io.*;
import java.net.*;
import java.util.*;

public class exp6server{
	public static void main(String[] args) throws Exception{
		DatagramSocket ds = new DatagramSocket(5000);
		HashMap <String,String> dict = new HashMap<>();
		dict.put("tbh","to be honest");
		dict.put("ig","I guess");
		dict.put("tbf","to be fair");
		dict.put("atm","at the moment");
		dict.put("irl","in real life");
		dict.put("lol","laugh out loud");
		dict.put("asap","as soon as possible");
		dict.put("omg","oh my god");
		dict.put("ttyl","talk to you later");
		dict.put("idk","I don't know");
		dict.put("nvm","never mind");
		
		byte buffer[] = new byte[1024];
		System.out.println("Server started....");
		DatagramPacket dp = new DatagramPacket(buffer,buffer.length);
		ds.receive(dp);
		
		String sent = new String(dp.getData(),0,dp.getLength());
		System.out.println("Recieved sentence :"+ sent);
		String word[] = sent.split(" ");
		String res = "";
		for(String i : word){
			if(dict.containsKey(i))
				res+=dict.get(i)+ " ";
			else
				res+=i + " ";
		}
		System.out.printf("\nDecrypting abbreaviationss.........\n\n");
		System.out.println(res);
		byte send[] = res.getBytes();
		DatagramPacket reply = new DatagramPacket(send, send.length, dp.getAddress(), dp.getPort());
		ds.send(reply);
		ds.close();
	}
}
