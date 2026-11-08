import java.io.*;
import java.net.*;
public class Q1server{
    public static void main(String args[]) throws Exception {
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("Server started...");

        while (true){
            Socket s = ss.accept();
            Thread t = new Thread(()->{
                try{
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    int n = in.readInt();
                    int[] arr = new int[n];
                    float sum, avg, min, max;
                    sum=0;
                    
                    for(int i=0; i<n; i++){
                        arr[i]=in.readInt();
                        sum+=arr[i];
                    }
                    avg = sum/n;
                    min = arr[0];
                    max = arr[0];
                    for(int i=1; i<n; i++){
                        if ( arr[i] > max)
                            max= arr[i];
                        if (arr[i] < min)
                            min = arr[i];
                    }

                    out.writeFloat(sum);
                    out.writeFloat(avg);
                    out.writeFloat(max);
                    out.writeFloat(min);

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