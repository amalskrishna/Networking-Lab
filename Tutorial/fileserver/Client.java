package fileserver;
import java.net.*;
import java.io.*;
import java.util.*;

public class Client {
    public static void main(String args[]) throws Exception{
        Socket s = new Socket("localhost",5000);
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);
        System.out.print("enter filename : ");
        String filename = sc.nextLine();

        out.writeUTF(filename);
        out.flush();

        long pid = in.readLong();
        System.out.println("server pid " + pid);

        boolean found = in.readBoolean();
        if (found){
            System.out.println("contents...:");
            while (true){
                String line = in.readUTF();
                if (line.equals("END"))
                    break;
                System.out.println(line);
            }
        }
        else{
            System.out.println("file not found....");
        }
    }
}
