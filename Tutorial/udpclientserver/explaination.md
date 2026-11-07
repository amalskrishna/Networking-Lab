# UDP Request-Reply Client-Server (Java Datagram Sockets)

A connectionless **UDP** client-server program. The **Client** sends one text message to the **Server** as a datagram packet. The server replies with a greeting if the message is `hi`, the current date and time if the message is `time`, or a "sorry" message otherwise. Each received packet is handled in its own thread.

---

## 1. Algorithm

### Server

1. Create a `DatagramSocket` bound to port **5000**.
2. Print "server started udp.......l".
3. Loop forever:
   1. Create a fresh 1024-byte buffer and a `DatagramPacket` to hold incoming data.
   2. Wait for a packet from a client (`receive()`).
   3. Start a new thread to handle this packet:
      1. Convert the received bytes into a string `req`.
      2. If `req` equals `"hi"`, set the reply to the greeting.
      3. Else if `req` equals `"time"`, set the reply to the current date and time.
      4. Otherwise, set the reply to the "sorry" message.
      5. Convert the reply to bytes.
      6. Build a reply packet addressed to the sender's IP and port.
      7. Send the reply packet.
      8. If any exception occurs, print it.

### Client

1. Create a `DatagramSocket` (the OS picks a free local port).
2. Read a line typed by the user.
3. Convert the message to bytes.
4. Resolve the server address (`localhost`).
5. Build a `DatagramPacket` with the data, server IP, and port 5000, then send it.
6. Create a 1024-byte buffer and a packet to receive the reply.
7. Wait for the reply (`receive()`).
8. Convert the received bytes to a string and print it.
9. Close the scanner and the socket.

---

## 2. Pseudocode

### Server

```text
BEGIN Server
    socket <- create UDP socket on port 5000
    PRINT "server started udp.......l"

    WHILE true DO
        buffer <- new byte array of size 1024
        packet <- new packet using buffer
        socket.receive(packet)

        START new thread:
            TRY
                req <- string from packet data (0 to packet length)

                IF req == "hi" THEN
                    reply <- "SERVER: HIII, HOW CAN I HELP YOU ...."
                ELSE IF req == "time" THEN
                    reply <- current date and time as string
                ELSE
                    reply <- "SERVER: SORRY I DIDNT UNDERSTAND YOUR REQUEST...."
                END IF

                send <- bytes of reply
                replyPacket <- packet(send, packet.address, packet.port)
                socket.send(replyPacket)
            CATCH exception
                PRINT exception
            END TRY
        END thread
    END WHILE
END Server
```

### Client

```text
BEGIN Client
    socket <- create UDP socket (any free port)
    msg <- read line from keyboard

    send <- bytes of msg
    ip <- address of "localhost"
    packet <- packet(send, length, ip, 5000)
    socket.send(packet)

    buffer <- new byte array of size 1024
    recvPacket <- new packet using buffer
    socket.receive(recvPacket)

    output <- string from recvPacket data (0 to length)
    PRINT output

    CLOSE scanner, socket
END Client
```

---

## 3. Line-by-Line Explanation

### 3.1 Server.java

| Line / Code | Explanation |
|---|---|
| `import java.net.DatagramPacket;` | Imports the class representing a UDP packet. |
| `import java.net.DatagramSocket;` | Imports the class used to send and receive UDP packets. |
| `import java.util.*;` | Imports utility classes; `Date` is used here. |
| `public class Server {` | Declares the server class. |
| `public static void main(String args[]) throws Exception{` | Entry point. `throws Exception` avoids handling checked exceptions in `main`. |
| `DatagramSocket ds = new DatagramSocket(5000);` | Creates a UDP socket bound to port 5000 to receive requests. |
| `System.out.println("server started udp.......l");` | Confirms the server is running. |
| `while(true){` | Infinite loop so the server keeps receiving packets. |
| `byte[] buffer = new byte[1024];` | Allocates a new buffer for each packet so threads do not share and overwrite data. |
| `DatagramPacket dp = new DatagramPacket(buffer, buffer.length);` | Creates an empty packet that will hold the incoming data. |
| `ds.receive(dp);` | Blocks until a datagram arrives; fills `dp` with data, sender address, and sender port. |
| `Thread t = new Thread(()->{` | Creates a thread (via lambda) to process this request concurrently. |
| `try{` | Begins error handling for this request. |
| `String req = new String(dp.getData(),0,dp.getLength());` | Converts the received bytes into a string, using only the actual length of data received. |
| `String reply;` | Variable to hold the response text. |
| `if (req.equals("hi")){` | Checks whether the request is exactly `hi`. |
| `reply = "SERVER: HIII, HOW CAN I HELP YOU ....";` | Greeting reply. |
| `}else if (req.equals("time")){` | Checks whether the request is `time`. |
| `reply = new Date().toString();` | Replies with the current date and time. |
| `}else{` | Runs for any other request. |
| `reply = "SERVER: SORRY I DIDNT UNDERSTAND YOUR REQUEST....";` | Fallback reply. |
| `}` | End of if-else chain. |
| `byte[] send = reply.getBytes();` | Converts the reply string into bytes for transmission. |
| `DatagramPacket rep = new DatagramPacket(send, send.length, dp.getAddress(), dp.getPort());` | Builds the reply packet, addressed to the client's IP and port taken from the request. |
| `ds.send(rep);` | Sends the reply packet to the client. |
| `}catch (Exception e){` | Catches any error while processing or sending. |
| `System.out.println(e);` | Prints the exception on the server console. |
| `}` | End of catch block. |
| `});` | End of the thread's lambda body. |
| `t.start();` | Starts the thread; the main loop goes back to waiting for the next packet. |
| `}` | End of the `while(true)` loop. |

### 3.2 Client.java

| Line / Code | Explanation |
|---|---|
| `import java.io.*;` | Imports I/O classes (not strictly needed here). |
| `import java.net.*;` | Imports `DatagramSocket`, `DatagramPacket`, `InetAddress`. |
| `import java.util.*;` | Imports `Scanner`. |
| `public class Client {` | Declares the client class. |
| `public static void main(String args[]) throws Exception{` | Entry point of the client. |
| `DatagramSocket ds = new DatagramSocket();` | Creates a UDP socket on any free local port. No connection is made, since UDP is connectionless. |
| `Scanner sc = new Scanner(System.in);` | Reads user input from the keyboard. |
| `String msg = sc.nextLine();` | Reads the message typed by the user. |
| `byte[] send = msg.getBytes();` | Converts the message into bytes, since UDP sends raw bytes. |
| `InetAddress ip = InetAddress.getByName("localhost");` | Resolves the server's address (the same machine). |
| `DatagramPacket dp = new DatagramPacket(send, send.length, ip , 5000);` | Builds the packet with the data, length, destination IP, and destination port. |
| `ds.send(dp);` | Sends the packet to the server. |
| `byte[] buffer = new byte[1024];` | Buffer to hold the server's reply (maximum 1024 bytes). |
| `DatagramPacket rec = new DatagramPacket(buffer, buffer.length);` | Empty packet that will receive the reply. |
| `ds.receive(rec);` | Blocks until the reply arrives. |
| `String output = new String(rec.getData(),0,rec.getLength());` | Converts only the received bytes into a string. |
| `System.out.println(output);` | Displays the reply. |
| `sc.close();` | Closes the scanner. |
| `ds.close();` | Closes the UDP socket and releases the local port. |
| `}` | End of `main`; the program ends. |

---

## 4. How to Run

```bash
javac Server.java Client.java
java Server          # Terminal 1
java Client          # Terminal 2, then type: hi  (or: time)
```

### Sample Output

| Client input | Client output |
|---|---|
| `hi` | `SERVER: HIII, HOW CAN I HELP YOU ....` |
| `time` | e.g. `Sat Nov 07 23:02:15 IST 2026` |
| `hello` | `SERVER: SORRY I DIDNT UNDERSTAND YOUR REQUEST....` |

---

## 5. TCP vs UDP (Quick Comparison)

| Feature | TCP (previous programs) | UDP (this program) |
|---|---|---|
| Classes used | `Socket`, `ServerSocket` | `DatagramSocket`, `DatagramPacket` |
| Connection | Connection-oriented (`accept()` per client) | Connectionless (no handshake) |
| Reliability | Guaranteed, ordered delivery | No guarantee; packets may be lost or reordered |
| Data unit | Continuous stream | Individual packets (datagrams) |
| Client address | Known through the connected socket | Taken from each packet (`getAddress()`, `getPort()`) |

---

## 6. Notes / Possible Improvements

- **Packet loss:** UDP gives no delivery guarantee. If the request or reply is lost, the client's `receive()` blocks forever. Use `ds.setSoTimeout(ms)` on the client to avoid hanging.
- **Case sensitivity:** `"Hi"` or `"TIME"` will not match. Use `equalsIgnoreCase`.
- **Fresh buffer per packet:** the server allocates a new buffer inside the loop so a running thread's data is not overwritten by the next packet. Reusing one buffer would cause a race condition.
- **Shared socket:** multiple threads call `ds.send()` on the same socket. This is safe, since `DatagramSocket` methods are thread-safe.
- **Message size limit:** messages longer than 1024 bytes are truncated. Increase the buffer size if needed.
- **Hardcoded `localhost`:** the client must run on the same machine as the server. Use the server's IP for a LAN setup.
- **Typo in server banner:** `"server started udp.......l"` has a stray `l` at the end.