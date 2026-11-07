# Simple Request-Reply Client-Server (Java Sockets)

A minimal TCP client-server program. The **Client** sends one text message to the **Server**. If the message is `hi`, the server replies with a greeting; for anything else it replies that it does not understand. Each client connection is handled in its own thread.

---

## 1. Algorithm

### Server

1. Start a `ServerSocket` listening on port **5000**.
2. Print "Server started........".
3. Loop forever:
   1. Wait for a client to connect (`accept()`).
   2. Start a new thread to handle this client:
      1. Create input and output data streams for the socket.
      2. Read the message sent by the client.
      3. If the message equals `"hi"`, send the greeting reply.
      4. Otherwise, send the "sorry I don't understand" reply.
      5. Close the input stream, output stream, and socket.
      6. If any exception occurs, print it.

### Client

1. Connect to the server at `localhost:5000`.
2. Create input and output data streams.
3. Read a line typed by the user.
4. Send the line to the server.
5. Receive the server's reply and print it.
6. Close the streams, the socket, and the scanner.

---

## 2. Pseudocode

### Server

```text
BEGIN Server
    serverSocket <- listen on port 5000
    PRINT "Server started........"

    WHILE true DO
        socket <- serverSocket.accept()

        START new thread:
            TRY
                in  <- input stream of socket
                out <- output stream of socket

                msg <- in.readUTF()
                IF msg == "hi" THEN
                    out.writeUTF("SERVER: HIII HOW CAN I HELP YOU......")
                ELSE
                    out.writeUTF("SERVER: SORRY I DONT UNDERSTAND YOUR REQUEST......")
                END IF

                CLOSE in, out, socket
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
    socket <- connect to "localhost", port 5000
    in  <- input stream of socket
    out <- output stream of socket

    str <- read line from keyboard
    out.writeUTF(str)

    reply <- in.readUTF()
    PRINT reply

    CLOSE in, out, socket, scanner
END Client
```

---

## 3. Line-by-Line Explanation

### 3.1 Server.java

| Line / Code | Explanation |
|---|---|
| `import java.io.*;` | Imports I/O classes such as `DataInputStream` and `DataOutputStream`. |
| `import java.net.*;` | Imports `Socket` and `ServerSocket`. |
| `public class Server{` | Declares the server class. |
| `public static void main(String[] args) throws Exception{` | Entry point. `throws Exception` avoids handling checked exceptions in `main`. |
| `ServerSocket ss = new ServerSocket(5000);` | Opens a server socket listening on port 5000. |
| `System.out.println("Server started........");` | Confirms the server is running. |
| `//server waiting for connection` | Comment: the next loop waits for clients. |
| `while(true){` | Infinite loop so the server keeps accepting clients. |
| `Socket s = ss.accept();` | Blocks until a client connects, then returns a `Socket` for that client. |
| `Thread t = new Thread(()->{` | Creates a thread (via lambda) so each client is served concurrently. |
| `try{` | Begins error handling for this client's session. |
| `DataInputStream in = new DataInputStream(s.getInputStream());` | Stream for **receiving** data from the client. |
| `DataOutputStream out = new DataOutputStream(s.getOutputStream());` | Stream for **sending** data to the client. |
| `String msg = in.readUTF();` | Reads the message sent by the client (blocks until data arrives). |
| `if (msg.equals("hi")){` | Checks whether the message is exactly `hi` (case-sensitive). |
| `out.writeUTF("SERVER: HIII HOW CAN I HELP YOU......");` | Sends the greeting reply. |
| `}else{` | Runs for any other message. |
| `out.writeUTF("SERVER: SORRY I DONT UNDERSTAND YOUR REQUEST......");` | Sends the fallback reply. |
| `}` | End of if-else. |
| `in.close();` | Closes the input stream. |
| `out.close();` | Closes the output stream (flushes pending data first). |
| `s.close();` | Closes the client socket, ending the connection. |
| `}catch( Exception e){` | Catches any I/O or network error for this client. |
| `System.out.println(e);` | Prints the exception on the server console. |
| `}` | End of catch block. |
| `});` | End of the thread's lambda body. |
| `t.start();` | Starts the thread; the main loop goes back to waiting for the next client. |
| `}` | End of the `while(true)` loop. |

### 3.2 Client.java

| Line / Code | Explanation |
|---|---|
| `import java.io.*;` | Imports data stream classes. |
| `import java.net.*;` | Imports `Socket`. |
| `import java.util.*;` | Imports `Scanner`. |
| `public class Client {` | Declares the client class. |
| `public static void main(String args[]) throws Exception{` | Entry point of the client. |
| `Socket s = new Socket("localhost",5000);` | Connects to the server on the same machine at port 5000. |
| `DataInputStream in = new DataInputStream(s.getInputStream());` | Stream for receiving data from the server. |
| `DataOutputStream out = new DataOutputStream(s.getOutputStream());` | Stream for sending data to the server. |
| `Scanner sc = new Scanner(System.in);` | Reads user input from the keyboard. |
| `String str = sc.nextLine();` | Reads the line typed by the user. |
| `out.writeUTF(str);` | Sends the message to the server. |
| `String reply = in.readUTF();` | Blocks until the server's reply arrives, then stores it. |
| `System.out.println(reply);` | Displays the reply. |
| `in.close();` | Closes the input stream. |
| `out.close();` | Closes the output stream. |
| `s.close();` | Closes the socket, ending the connection. |
| `sc.close();` | Closes the scanner. |
| `}` | End of `main`; the program ends. |

---

## 4. How to Run

```bash
javac Server.java Client.java
java Server          # Terminal 1
java Client          # Terminal 2, then type: hi
```

### Sample Output

| Client input | Client output |
|---|---|
| `hi` | `SERVER: HIII HOW CAN I HELP YOU......` |
| `hello` | `SERVER: SORRY I DONT UNDERSTAND YOUR REQUEST......` |

---

## 5. Notes / Possible Improvements

- **Case sensitivity:** `"Hi"` or `"HI"` will not match. Use `msg.equalsIgnoreCase("hi")` to accept them.
- **One request per connection:** the server closes the socket after a single reply. For a continuous conversation, wrap the read/reply logic in a loop.
- **No server log:** the server prints nothing when a client connects. Add a log line for easier debugging.
- **Resource handling:** use try-with-resources so streams and sockets close even if an exception occurs.
- **Hardcoded `localhost`:** the client must run on the same machine as the server. Use the server's IP for a LAN setup.