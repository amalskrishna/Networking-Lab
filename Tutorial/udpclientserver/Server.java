
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

public class Server {
    public static void main(String args[]) throws Exception{
        DatagramSocket ds = new DatagramSocket(5000);
        System.out.println("server started udp.......l");
        while(true){
            byte[] buffer = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            ds.receive(dp);

            Thread t = new Thread(()->{
                try{
                    String req = new String(dp.getData(),0,dp.getLength());
                    String reply;
                    
                    if (req.equals("hi")){
                        reply = "SERVER: HIII, HOW CAN I HELP YOU ....";
                    }
                    else if (req.equals("time")){
                        reply = new Date().toString();
                    }
                    else{
                        reply = "SERVER: SORRY I DIDNT UNDERSTAND YOUR REQUEST....";
                    }
                    byte[] send = reply.getBytes();

                    DatagramPacket rep = new DatagramPacket(send, send.length, dp.getAddress(), dp.getPort());
                    ds.send(rep);
                }
                catch (Exception e){
                    System.out.println(e);
                }
            });
            t.start();
        }
    }
}
