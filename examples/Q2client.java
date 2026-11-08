import java.net.*;
import java.io.*;
import java.util.*;

public class Q2client {
    public static void main(String args[]) throws Exception{
        Socket s = new Socket("localhost",5000);
        DataInputStream in = new DataInputStream(s.getInputStream());
        DataOutputStream out = new DataOutputStream(s.getOutputStream());
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the size of the array: ");
        int n = sc.nextInt();
        out.writeInt(n);
        out.flush();
        int arr[] = new int[n];
        for(int i=0 ; i<n ; i++){
            arr[i] = sc.nextInt();
        }
        for(int i : arr){
            out.writeInt(i);
            out.flush();
        }

        int sarr[] = new int[n];
        for(int i=0 ; i<n ; i++){
            sarr[i] = in.readInt();
        }

        System.out.println("Sorted array : " + Arrays.toString(sarr));

        in.close();
        out.close();
        s.close();
        sc.close();
    }
}
