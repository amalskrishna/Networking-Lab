import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String args[]) throws Exception{
        Socket s = new Socket("localhost",5000);
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter username : ");
        String user = sc.nextLine();
        out.writeUTF(user);
        out.flush();

        Thread t = new Thread(()->{
            try{
                while(true){
                    String msg = in.readUTF();
                    System.out.println("\n" + msg);
                }
            }
            catch(Exception e){
                System.out.println("Disconnected.");
            }
        });
        t.start();
        while(true){
            String msg = sc.nextLine();
            if (msg.equals("exit")){
                break;
            }
            out.writeUTF(msg);
            out.flush();
        }
        in.close();
        out.close();
        s.close();
        sc.close();

    }
}
