import java.io.*;
import java.net.*;

public class Server{
    public static void main(String[] args) throws Exception{
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("Server started........");
        //server waiting for connection
        while(true){
            Socket s = ss.accept();
            Thread t = new Thread(()->{
                try{
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());
                    String msg = in.readUTF();
                    if (msg.equals("hi")){
                        out.writeUTF("SERVER: HIII HOW CAN I HELP YOU......");
                    }
                    else{
                        out.writeUTF("SERVER: SORRY I DONT UNDERSTAND YOUR REQUEST......");
                    }
                    in.close();
                    out.close();
                    s.close();
                }
                catch( Exception e){
                    System.out.println(e);
                }
            });
            t.start();
        }
        
    }
}