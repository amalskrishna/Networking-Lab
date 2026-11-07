# Multi-Client Chat Application (Java Sockets)

A simple TCP-based group chat system. A central **Server** accepts multiple **Clients**; any message sent by one client is broadcast to all connected clients.

---

## 1. Algorithm

### Server

1. Start a `ServerSocket` listening on port **5000**.
2. Create a shared list `clients` to store the output stream of every connected client.
3. Loop forever:
   1. Wait for a client to connect (`accept()`).
   2. Create input and output data streams for that client's socket.
   3. Read the client's username (first message sent).
   4. Print "`<username>` connected..." and add the client's output stream to `clients`.
   5. Start a new thread for this client:
      1. Loop forever: read a message from the client.
      2. Prefix the message with the username.
      3. Send it to **every** client in the `clients` list (broadcast).
      4. If an error occurs (client left), print "disconnected", remove the client's stream from `clients`, and end the thread.

### Client

1. Connect to the server at `localhost:5000`.
2. Create input and output data streams.
3. Ask the user for a username and send it to the server.
4. Start a **receiver thread**:
   1. Loop forever: read a message from the server and print it.
   2. If an error occurs, print "Disconnected." and end the thread.
5. In the **main thread**, loop:
   1. Read a line typed by the user.
   2. If it is `exit`, break out of the loop.
   3. Otherwise, send the message to the server.
6. Close all streams, the socket, and the scanner.

---

## 2. Pseudocode

### Server

```text
BEGIN Server
    clients <- empty list of output streams
    serverSocket <- listen on port 5000
    PRINT "server started........"

    WHILE true DO
        socket <- serverSocket.accept()
        in  <- input stream of socket
        out <- output stream of socket

        username <- in.readUTF()
        PRINT username + " connected..."
        ADD out TO clients

        START new thread:
            TRY
                WHILE true DO
                    msg <- in.readUTF()
                    FOR EACH client IN clients DO
                        client.writeUTF(username + " : " + msg)
                        client.flush()
                    END FOR
                END WHILE
            CATCH exception
                PRINT username + " disconnected...."
                REMOVE out FROM clients
            END TRY
        END thread
    END WHILE
END Server
```

### Client

```text
BEGIN Client
    socket <- connect to "localhost", port 5000
    in  <- input stream of socket
    out <- output stream of socket

    PRINT "Enter username : "
    user <- read line from keyboard
    out.writeUTF(user)
    out.flush()

    START receiver thread:
        TRY
            WHILE true DO
                msg <- in.readUTF()
                PRINT newline + msg
            END WHILE
        CATCH exception
            PRINT "Disconnected."
        END TRY
    END thread

    WHILE true DO
        msg <- read line from keyboard
        IF msg == "exit" THEN
            BREAK
        END IF
        out.writeUTF(msg)
        out.flush()
    END WHILE

    CLOSE in, out, socket, scanner
END Client
```

---

## 3. Line-by-Line Explanation

### 3.1 Server.java

| Line / Code | Explanation |
|---|---|
| `import java.io.*;` | Imports I/O classes such as `DataInputStream` and `DataOutputStream`. |
| `import java.net.*;` | Imports networking classes such as `Socket` and `ServerSocket`. |
| `import java.util.*;` | Imports utility classes such as `ArrayList`. |
| `public class Server {` | Declares the main server class. |
| `static ArrayList<DataOutputStream> clients = new ArrayList<>();` | A shared static list holding the output stream of every connected client, used to broadcast messages. |
| `public static void main(String args[]) throws Exception {` | Entry point of the server. `throws Exception` avoids writing try-catch for checked exceptions. |
| `ServerSocket ss = new ServerSocket(5000);` | Creates a server socket that listens for connections on port 5000. |
| `System.out.println("server started........");` | Confirms the server is running. |
| `while(true){` | Infinite loop so the server keeps accepting new clients. |
| `Socket s = ss.accept();` | Blocks until a client connects, then returns a `Socket` for that client. |
| `DataInputStream in = new DataInputStream(s.getInputStream());` | Stream for **receiving** data from this client. |
| `DataOutputStream out = new DataOutputStream(s.getOutputStream());` | Stream for **sending** data to this client. |
| `String username = in.readUTF();` | Reads the first message from the client, which is its username. |
| `System.out.println(username + " connected...");` | Logs the new connection on the server console. |
| `clients.add(out);` | Registers the client's output stream so it receives broadcasts. |
| `Thread t = new Thread(()->{` | Creates a new thread (using a lambda) so each client is handled independently. |
| `try{` | Begins error handling for the client's session. |
| `while(true){` | Keeps listening for messages from this client. |
| `String msg = in.readUTF();` | Blocks until the client sends a message. |
| `for( DataOutputStream i : clients){` | Iterates over all connected clients' output streams. |
| `i.writeUTF(username + " : " + msg);` | Sends the message, prefixed with the sender's name, to one client. |
| `i.flush();` | Forces buffered data to be sent immediately. |
| `}` (end for) | End of the broadcast loop. |
| `}` (end while) | End of the message-reading loop. |
| `}catch(Exception e){` | Runs when reading fails, typically because the client disconnected. |
| `System.out.println(username + " disconnected....");` | Logs the disconnection. |
| `clients.remove(out);` | Removes the client's stream so no further messages are sent to it. |
| `}` | End of catch block. |
| `});` | End of the thread's lambda body. |
| `t.start();` | Starts the thread, which runs concurrently with the main accept loop. |
| `}` (end while) | Back to waiting for the next client. |

### 3.2 Client.java

| Line / Code | Explanation |
|---|---|
| `import java.io.*;` | Imports I/O stream classes. |
| `import java.net.*;` | Imports `Socket`. |
| `import java.util.Scanner;` | Imports `Scanner` for reading keyboard input. |
| `public class Client {` | Declares the client class. |
| `public static void main(String args[]) throws Exception{` | Entry point of the client. |
| `Socket s = new Socket("localhost",5000);` | Connects to the server on the same machine at port 5000. |
| `DataInputStream in = new DataInputStream(s.getInputStream());` | Stream for receiving messages from the server. |
| `DataOutputStream out = new DataOutputStream(s.getOutputStream());` | Stream for sending messages to the server. |
| `Scanner sc = new Scanner(System.in);` | Reads user input from the keyboard. |
| `System.out.print("Enter username : ");` | Prompts the user for a name. |
| `String user = sc.nextLine();` | Reads the username typed by the user. |
| `out.writeUTF(user);` | Sends the username to the server as the first message. |
| `out.flush();` | Ensures the username is actually sent right away. |
| `Thread t = new Thread(()->{` | Creates a separate thread to receive messages, so receiving doesn't block typing. |
| `try{` | Begins error handling for the receiver. |
| `while(true){` | Continuously listens for incoming messages. |
| `String msg = in.readUTF();` | Blocks until the server sends a message. |
| `System.out.println("\n" + msg);` | Prints the message on a new line. |
| `}` | End of the loop. |
| `}catch(Exception e){` | Triggered when the connection closes or fails. |
| `System.out.println("Disconnected.");` | Informs the user the connection ended. |
| `}` | End of catch block. |
| `});` | End of the thread's lambda. |
| `t.start();` | Starts the receiver thread. |
| `while(true){` | Main-thread loop for sending messages. |
| `String msg = sc.nextLine();` | Reads a line typed by the user. |
| `if (msg.equals("exit")){ break; }` | If the user types `exit`, leave the loop to shut down. |
| `out.writeUTF(msg);` | Sends the message to the server. |
| `out.flush();` | Pushes the message out immediately. |
| `}` | End of the sending loop. |
| `in.close(); out.close(); s.close(); sc.close();` | Releases all resources. Closing the socket causes the server's `readUTF()` to throw, triggering its "disconnected" handling. |

---

## 4. How to Run

```bash
javac Server.java Client.java
java Server          # Terminal 1
java Client          # Terminal 2, 3, ... (one per user)
```

Type messages in any client; they appear in all connected clients. Type `exit` to leave.

---

## 5. Notes / Possible Improvements

- `clients` is an `ArrayList` accessed by multiple threads. It is not thread-safe, so concurrent add/remove during a broadcast can throw `ConcurrentModificationException`. Use `CopyOnWriteArrayList` or `Collections.synchronizedList(...)` instead.
- If writing to one dead client fails during a broadcast, the exception is caught by the *sender's* thread and wrongly treated as the sender disconnecting. Wrap each write in its own try-catch.
- The server never closes the client's socket after disconnect; add `s.close()` in the catch block.
- Hardcoded `localhost` means clients must run on the same machine as the server. Replace it with the server's IP for a LAN chat.