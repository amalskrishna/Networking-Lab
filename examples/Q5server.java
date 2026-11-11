import java.net.*;
public class Q5server {
    public static void main(String args[]) throws Exception{
        DatagramSocket ds = new DatagramSocket(5000);
        System.out.println("Server started......");
        while(true){
            byte buffer[] = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
            ds.receive(dp);
            Thread t = new Thread(()->{
                try{
                    String req = new String(dp.getData(), 0, dp.getLength());
                    System.out.println(req);
                    String words[] = req.split(" ");
                    String res = "";
                    
                    for(int i = words.length-1 ; i>=0; i--){
                        String temp = words[i];
                        
                        for ( int j =0 ; j<temp.length() ; j++){
                            char ch = temp.charAt(j);
                            if (Character.isUpperCase(ch)){
                                res += Character.toLowerCase(ch);
                            }
                            else if(Character.isLowerCase(ch))
                                res += Character.toUpperCase(ch);
                        }
                        res += " ";
                    }
                    System.out.println(res);

                    byte send[] = res.getBytes();
                    DatagramPacket dp2 = new DatagramPacket(send, send.length, dp.getAddress(), dp.getPort());
                    ds.send(dp2);
                }
                catch(Exception e){
                    System.out.println(e);
                }


            });
            t.start();
        }
    }
}
