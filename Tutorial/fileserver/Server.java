import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String args[]) throws Exception{
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("server started.....");
        while(true){
            Socket s = ss.accept();
            System.out.println("new client connected...");
            Thread t = new Thread(()->{
                try{
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    String filename = in.readUTF();
                    long pid = ProcessHandle.current().pid();
                    out.writeLong(pid);

                    File file = new File(filename);
                    if (file.exists() && file.isFile()){
                        out.writeBoolean(true);
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        while((line = br.readLine())!= null){
                            out.writeUTF(line);
                        }
                        out.writeUTF("END");
                        br.close();
                    }
                    else{
                        out.writeBoolean(false);
                    }
                    out.flush();

                }
                catch(Exception e){
                    System.out.println(e);
                }
            });
            t.start();
        }
    }
}
