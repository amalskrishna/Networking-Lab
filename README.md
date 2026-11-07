# Networking-Lab

A collection of computer networking experiments: Java socket programming (TCP and UDP), packet captures analysed in Wireshark, and common network command-line tools.

> 📘 **Detailed explanations for every experiment are given in the [`Tutorial`](./Tutorial) folder.** Each subfolder contains the server and client code along with an `explanation.md` covering the **algorithm, pseudocode and a line-by-line explanation**.

---

## 📁 Repository Structure

```
Networking-Lab/
├── Tutorial/
│   ├── discord/     # Server + client code + explanation.md
│   ├── fileserver/     # Server + client code + explanation.md
│   ├── tcpclientserver/          # Server + client code + explanation.md
│   └── udpclientserver/          # Server + client code + explanation.md
├── exp6server.java                 # Experiment 6 - UDP abbreviation expander (server)
├── exp6client.java                 # Experiment 6 - client
├── exp7server.java                 # Experiment 7 - multi-client TCP chat server
├── exp7client.java                 # Experiment 7 - chat client
├── exp8server.java                 # Experiment 8 - UDP time server
├── exp8client.java                 # Experiment 8 - UDP client
├── exp9server.java                 # Experiment 9 - concurrent TCP file server
├── exp9client.java                 # Experiment 9 - file client
├── dnsresolv.pcapng                # Wireshark capture: DNS resolution
├── httpanalysis.pcapng             # Wireshark capture: HTTP analysis
├── ifconfig                        # ifconfig command usage/output
├── nmap                            # nmap command usage/output
├── tcpdump                         # tcpdump command usage/output
└── whois-command                   # whois command usage/output
```

---

## 📚 Tutorial

The [`Tutorial`](./Tutorial) folder explains how each program works. It has four subfolders, and each one contains:

- 🖥️ Server code
- 💻 Client code
- 📝 `explanation.md` with the **algorithm**, **pseudocode** and a **line-by-line explanation**

| Subfolder | Topic |
|-----------|-------|
| [multi-user-chat-server](./Tutorial/multi-user-chat-server) | Multi-user chat server that handles many clients using threads |
| [concurrent-file-server](./Tutorial/concurrent-file-server) | Concurrent file server that serves file requests from multiple clients |
| [tcp-client-server](./Tutorial/tcp-client-server) | Basic TCP client-server communication |
| [udp-client-server](./Tutorial/udp-client-server) | Basic UDP client-server communication |

---

## 🧪 Java Socket Programming Experiments

| Experiment | Description | Protocol | Port |
|-----------|-------------|----------|------|
| **Exp 6** | **Abbreviation expander.** The client sends a sentence containing chat abbreviations (e.g. `tbh`, `idk`, `asap`, `omg`) and the server replies with the formal English version. | UDP | 5000 |
| **Exp 7** | **Multi-client chat.** The server handles each client in its own thread and broadcasts messages to all connected users. Type `exit` to leave the chat. | TCP | 5000 |
| **Exp 8** | **Time server.** The client sends a datagram and the server replies with the current date and time. | UDP | 7000 |
| **Exp 9** | **Concurrent file server.** The client sends a file name, and the server replies with its own process ID and the file contents (or "File not found!"). | TCP | 5000 |

### ▶️ How to Run

Make sure the JDK is installed (`java -version`, `javac -version`).

**1. Compile**

```bash
javac exp6server.java exp6client.java
```

**2. Start the server (Terminal 1)**

```bash
java exp6server
```

**3. Start the client (Terminal 2)**

```bash
java exp6client
```

Replace `exp6` with `exp7`, `exp8` or `exp9` to run the other experiments.

> Run the server first. The clients connect to `localhost` by default. For Exp 7, open several client terminals to see messages broadcast to everyone.

### 💡 Example (Exp 6)

```
Enter a sentence : idk what happened tbh
Translated formal english below ........

I don't know what happened to be honest
```

---

## 🦈 Packet Capture Analysis (Wireshark)

| File | Description |
|------|-------------|
| `dnsresolv.pcapng` | Capture of DNS query and response packets during name resolution |
| `httpanalysis.pcapng` | Capture of HTTP request and response traffic |

Open these in [Wireshark](https://www.wireshark.org/) and use display filters such as `dns` or `http` to inspect the packets.

---

## 🛠️ Network Command-Line Tools

| File | Tool | Purpose |
|------|------|---------|
| `ifconfig` | `ifconfig` | View and configure network interfaces |
| `nmap` | `nmap` | Network discovery and port scanning |
| `tcpdump` | `tcpdump` | Capture and analyse network packets from the terminal |
| `whois-command` | `whois` | Look up domain registration and ownership details |

---

## ⚙️ Requirements

- Java JDK 8 or higher
- Wireshark (to open `.pcapng` files)
- A Linux/Unix terminal with `ifconfig`, `nmap`, `tcpdump` and `whois` installed

---

## 👤 Author

**amalskrishna**
[GitHub Profile](https://github.com/amalskrishna)