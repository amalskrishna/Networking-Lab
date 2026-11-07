import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String args[]) throws Exception{
        Socket s = new Socket("localhost",5000);
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);

        String str = sc.nextLine();
        out.writeUTF(str);

        String reply = in.readUTF();
        System.out.println(reply);

        in.close();
        out.close();
        s.close();
        sc.close();

    }
}
