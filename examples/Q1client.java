import java.net.*;
import java.io.*;
import java.util.*;

public class Q1client {
    public static void main(String args[]) throws Exception {
        Socket s = new Socket("localhost", 5000);
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter size of array: ");
        int n = sc.nextInt();
        out.writeInt(n);
        out.flush();
        int[] arr = new int[n];
        System.out.println("Enter the elements.....");
        for ( int i =0; i< n ; i++){
            int temp = sc.nextInt();
            arr[i]=temp;
        }
        for (int i : arr){
            out.writeInt(i);
            out.flush();
        }
        float sum, avg, min , max;
        sum=in.readFloat();
        avg = in.readFloat();
        max = in.readFloat();
        min = in.readFloat();
        System.out.printf("sum: %f, avg: %f, max: %f, min: %f",sum, avg , max, min);
        in.close();
        out.close();
        s.close();
        sc.close();
    }
}
