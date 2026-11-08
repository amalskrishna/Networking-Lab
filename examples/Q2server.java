import java.net.*;
import java.io.*;
import java.util.*;

public class Q2server {
    public static void main(String args[]) throws Exception{
        ServerSocket ss = new ServerSocket(5000);

        while (true){
            Socket s = ss.accept();
            Thread t = new Thread(()->{
                try{
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    int n = in.readInt();
                    int arr[] = new int[n];

                    for (int i=0; i<n ; i++){
                        arr[i]=in.readInt();
                    }

                    Arrays.sort(arr);

                    for (int i : arr){
                        out.writeInt(i);
                        out.flush();
                    }

                    in.close();
                    out.close();
                    s.close();
                }
                catch(Exception e){
                    System.out.println(e);
                }
            });
            t.start();
            

            
        }
    }
}
