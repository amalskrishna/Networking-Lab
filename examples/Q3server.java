import java.net.*;
import java.io.*;
import java.util.*;
public class Q3server {
    static int[] bsearch(int arr[], int key){
        int l=0;
        int r=arr.length - 1;
        int cnt= 0;
        while(l<=r){
            int mid = (l+r)/2;
            cnt = cnt+1;
            if (arr[mid]== key){
                return new int[]{mid,cnt};
            }
            else if( key > arr[mid]){
                cnt++;
                l=mid+1;
            }
            else{
                cnt++;
                r=mid-1;
            }
            
        }
        return new int[] {-1,cnt};
    }
    public static void main(String args[]) throws Exception {
        ServerSocket ss = new ServerSocket(5000);
        while (true){
            Socket s = ss.accept();
            Thread t = new Thread(()->{
                try{
                    DataInputStream in = new DataInputStream(s.getInputStream());
                    DataOutputStream out = new DataOutputStream(s.getOutputStream());

                    int n = in.readInt();
                    int arr[] = new int[n];
                    for ( int i = 0; i<n; i++)
                        arr[i]= in.readInt();
                    Arrays.sort(arr);
                    int key = in.readInt();

                    int res[] = bsearch(arr, key);
                    out.writeInt(res[0]);
                    out.writeInt(res[1]);

                    in.close();
                    out.close();
                    s.close();
                    
                }
                catch (Exception e){
                    System.out.println(e);
                }
            });
            t.start();
        }
    }
}
