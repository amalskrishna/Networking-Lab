# File Transfer Client-Server (Java Sockets)

A TCP-based file request system. The **Client** sends a filename to the **Server**. The server replies with its **process ID (PID)** and, if the file exists, the **contents of the file** line by line. Each client is handled in its own thread.

---

## 1. Algorithm

### Server

1. Start a `ServerSocket` listening on port **5000**.
2. Loop forever:
   1. Wait for a client to connect (`accept()`).
   2. Print "new client connected...".
   3. Start a new thread to handle this client:
      1. Create input and output data streams for the socket.
      2. Read the filename sent by the client.
      3. Get the server's process ID and send it to the client.
      4. Create a `File` object for the filename.
      5. If the file exists and is a regular file:
         1. Send `true` to the client.
         2. Open the file with a `BufferedReader`.
         3. Read it line by line and send each line to the client.
         4. Send the marker string `"END"` to signal the end of the file.
         5. Close the reader.
      6. Otherwise, send `false` to the client.
      7. Flush the output stream.
      8. If any exception occurs, print it.

### Client

1. Connect to the server at `localhost:5000`.
2. Create input and output data streams.
3. Ask the user for a filename and send it to the server.
4. Receive the server's PID and print it.
5. Receive the boolean `found` flag.
6. If `found` is true:
   1. Print "contents...:".
   2. Repeatedly read a line from the server.
   3. If the line is `"END"`, stop; otherwise print it.
7. If `found` is false, print "file not found....".

---

## 2. Pseudocode

### Server

```text
BEGIN Server
    serverSocket <- listen on port 5000
    PRINT "server started....."

    WHILE true DO
        socket <- serverSocket.accept()
        PRINT "new client connected..."

        START new thread:
            TRY
                in  <- input stream of socket
                out <- output stream of socket

                filename <- in.readUTF()
                pid <- current process ID
                out.writeLong(pid)

                file <- File(filename)
                IF file exists AND file is a regular file THEN
                    out.writeBoolean(true)
                    reader <- open file for reading
                    WHILE (line <- reader.readLine()) is not null DO
                        out.writeUTF(line)
                    END WHILE
                    out.writeUTF("END")
                    CLOSE reader
                ELSE
                    out.writeBoolean(false)
                END IF
                out.flush()
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

    PRINT "enter filename : "
    filename <- read line from keyboard
    out.writeUTF(filename)
    out.flush()

    pid <- in.readLong()
    PRINT "server pid " + pid

    found <- in.readBoolean()
    IF found THEN
        PRINT "contents...:"
        WHILE true DO
            line <- in.readUTF()
            IF line == "END" THEN
                BREAK
            END IF
            PRINT line
        END WHILE
    ELSE
        PRINT "file not found...."
    END IF
END Client
```

---

## 3. Line-by-Line Explanation

### 3.1 Server.java

| Line / Code | Explanation |
|---|---|
| `import java.io.*;` | Imports I/O classes: `File`, `BufferedReader`, `FileReader`, `DataInputStream`, `DataOutputStream`. |
| `import java.net.*;` | Imports `Socket` and `ServerSocket`. |
| `import java.util.*;` | Imports utility classes (not strictly needed here). |
| `public class Server {` | Declares the server class. |
| `public static void main(String args[]) throws Exception{` | Entry point. `throws Exception` avoids handling checked exceptions in `main`. |
| `ServerSocket ss = new ServerSocket(5000);` | Opens a server socket listening on port 5000. |
| `System.out.println("server started.....");` | Confirms the server is running. |
| `while(true){` | Infinite loop so the server keeps accepting clients. |
| `Socket s = ss.accept();` | Blocks until a client connects, then returns a `Socket` for that client. |
| `System.out.println("new client connected...");` | Logs the new connection. |
| `Thread t = new Thread(()->{` | Creates a thread (via lambda) so each client is served concurrently. |
| `try{` | Begins error handling for this client's session. |
| `DataInputStream in = new DataInputStream(s.getInputStream());` | Stream for **receiving** data from the client. |
| `DataOutputStream out = new DataOutputStream(s.getOutputStream());` | Stream for **sending** data to the client. |
| `String filename = in.readUTF();` | Reads the filename requested by the client. |
| `long pid = ProcessHandle.current().pid();` | Gets the OS process ID of the running server JVM. |
| `out.writeLong(pid);` | Sends the PID to the client as an 8-byte value. |
| `File file = new File(filename);` | Creates a `File` object representing the requested path. |
| `if (file.exists() && file.isFile()){` | Checks that the path exists and is a regular file, not a directory. |
| `out.writeBoolean(true);` | Tells the client the file was found. |
| `BufferedReader br = new BufferedReader(new FileReader(file));` | Opens the file for efficient line-by-line reading. |
| `String line;` | Variable to hold each line read. |
| `while((line = br.readLine())!= null){` | Reads one line at a time; `readLine()` returns `null` at end of file. |
| `out.writeUTF(line);` | Sends the line to the client. |
| `}` | End of the reading loop. |
| `out.writeUTF("END");` | Sends a sentinel string so the client knows the file is finished. |
| `br.close();` | Closes the file reader to release the file handle. |
| `}else{` | Runs when the file does not exist or is not a regular file. |
| `out.writeBoolean(false);` | Tells the client the file was not found. |
| `}` | End of if-else. |
| `out.flush();` | Forces any buffered data to be sent to the client. |
| `}catch(Exception e){` | Catches any I/O or network error during this client's session. |
| `System.out.println(e);` | Prints the exception on the server console. |
| `}` | End of catch block. |
| `});` | End of the thread's lambda body. |
| `t.start();` | Starts the thread; the main loop goes back to waiting for the next client. |
| `}` | End of the `while(true)` loop. |

### 3.2 Client.java

| Line / Code | Explanation |
|---|---|
| `import java.net.*;` | Imports `Socket`. |
| `import java.io.*;` | Imports data stream classes. |
| `import java.util.*;` | Imports `Scanner`. |
| `public class Client {` | Declares the client class. |
| `public static void main(String args[]) throws Exception{` | Entry point of the client. |
| `Socket s = new Socket("localhost",5000);` | Connects to the server on the same machine at port 5000. |
| `DataInputStream in = new DataInputStream(s.getInputStream());` | Stream for receiving data from the server. |
| `DataOutputStream out = new DataOutputStream(s.getOutputStream());` | Stream for sending data to the server. |
| `Scanner sc = new Scanner(System.in);` | Reads user input from the keyboard. |
| `System.out.print("enter filename : ");` | Prompts the user for a filename. |
| `String filename = sc.nextLine();` | Reads the filename typed by the user. |
| `out.writeUTF(filename);` | Sends the filename to the server. |
| `out.flush();` | Ensures the filename is sent immediately. |
| `long pid = in.readLong();` | Reads the server's process ID. The read order must match the server's write order. |
| `System.out.println("server pid " + pid);` | Displays the server PID. |
| `boolean found = in.readBoolean();` | Reads the flag saying whether the file was found. |
| `if (found){` | Branch for the file-found case. |
| `System.out.println("contents...:");` | Header printed before the file contents. |
| `while (true){` | Loop to receive all lines of the file. |
| `String line = in.readUTF();` | Reads one line from the server. |
| `if (line.equals("END")) break;` | Stops when the sentinel is received. |
| `System.out.println(line);` | Prints the received line. |
| `}` | End of the receive loop. |
| `}else{` | Branch for the file-not-found case. |
| `System.out.println("file not found....");` | Informs the user that the file does not exist. |
| `}` | End of if-else; the program then ends. |

---

## 4. How to Run

```bash
javac Server.java Client.java
java Server          # Terminal 1 (run from the folder containing the files to serve)
java Client          # Terminal 2, then enter a filename such as test.txt
```

---

## 5. Notes / Possible Improvements

- **Protocol order matters:** the server writes `long` then `boolean` then lines, and the client must read in exactly that order.
- **Sentinel problem:** if the file itself contains a line that is exactly `END`, the client will stop early. A safer approach is to send the line count first or use a separate end flag.
- **Resource leaks:** neither side closes the socket. Use try-with-resources (`try (Socket s = ...)`) so sockets and streams are always closed.
- **Security:** the server opens any path the client sends (for example `../../etc/passwd`). Restrict access to a specific directory and validate the filename.
- **PID:** `ProcessHandle.current().pid()` returns the PID of the server JVM, so every client sees the same value even though each is served by a different thread.
- Hardcoded `localhost` means the client must run on the same machine as the server. Use the server's IP for a LAN setup.