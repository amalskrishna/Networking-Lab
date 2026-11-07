import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    static ArrayList<DataOutputStream> clients = new ArrayList<>();
    public static void main (String args[]) throws Exception{
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("server started........");

        while(true){
            Socket s = ss.accept();
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String username = in.readUTF();
            System.out.println(username + " connected...");
            clients.add(out);

            Thread t = new Thread(()->{
                try{
                    while(true){
                        String msg = in.readUTF();
                        for( DataOutputStream i : clients){
                            i.writeUTF(username + " : " + msg);
                            i.flush();
                        }
                    }
                }
                catch(Exception e){
                    System.out.println(username + " disconnected....");
                    clients.remove(out);
                }
            });
            t.start();

        }
    }
}
