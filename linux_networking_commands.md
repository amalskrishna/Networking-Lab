# Linux Networking Commands

These are commonly used Linux networking commands for checking network interfaces, connectivity, bandwidth, routing, DNS, network statistics, and packet traffic.

For each command, this guide covers its purpose, syntax, sample output, and how to interpret the output.

> **Note:** Sample outputs are illustrative. Actual output varies depending on your Linux distribution, network configuration, installed tools, and current network activity.

## 1. `ifconfig` — Interface Configuration

**Purpose:** Displays and configures network interfaces such as Ethernet and Wi-Fi. It shows IP addresses, MAC addresses, and network statistics.

**Syntax:**

```bash
ifconfig
```

**Sample output:**

```text
eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>
        inet 192.168.1.10
        netmask 255.255.255.0
        broadcast 192.168.1.255
        ether 00:1A:2B:3C:4D:5E
        RX packets 1200  bytes 950000
        TX packets 850   bytes 650000

lo: flags=73<UP,LOOPBACK,RUNNING>
        inet 127.0.0.1
        netmask 255.0.0.0
```

**Explanation:**

- `eth0` — Ethernet network interface.
- `inet` — IPv4 address.
- `netmask` — Identifies the network portion of an IP address.
- `ether` — MAC address.
- `RX` — Received packets.
- `TX` — Transmitted packets.
- `lo` — Loopback interface (localhost).

**Note:** `ifconfig` is an older command. Modern Linux systems generally use `ip`.

## 2. `ifplugstatus` — Network Cable Status

**Purpose:** Checks whether a network cable is physically connected to an Ethernet interface.

**Syntax:**

```bash
ifplugstatus
```

**Sample output:**

```text
eth0: link beat detected
wlan0: unplugged
```

**Explanation:**

- `link beat detected` — Ethernet cable is connected and a link is detected.
- `unplugged` — No physical Ethernet link is detected.

It is mainly useful for checking wired network connections.

## 3. `iftop` — Network Bandwidth Monitoring

**Purpose:** Monitors real-time network bandwidth usage and displays data transfer between different hosts.

**Syntax:**

```bash
sudo iftop
```

**Sample output:**

```text
192.168.1.10  =>  142.250.190.14   2.5Mb  1.8Mb  1.2Mb
192.168.1.10  <=  142.250.190.14   500Kb  300Kb  200Kb

TX:  2.5Mb
RX:  500Kb
TOTAL: 3.0Mb
```

**Explanation:**

- `=>` — Outgoing traffic.
- `<=` — Incoming traffic.
- `TX` — Transmitted data.
- `RX` — Received data.
- `TOTAL` — Combined traffic.

The bandwidth values represent traffic rates over different time intervals.

## 4. `ping` — Network Connectivity Testing

**Purpose:** Checks whether another computer or server is reachable over a network. It uses ICMP Echo Request and Echo Reply packets.

**Syntax:**

```bash
ping google.com
```

To send only four packets:

```bash
ping -c 4 google.com
```

**Sample output:**

```text
PING google.com (142.250.190.14): 56 data bytes

64 bytes from 142.250.190.14:
icmp_seq=1 ttl=117 time=25.4 ms

64 bytes from 142.250.190.14:
icmp_seq=2 ttl=117 time=24.8 ms

64 bytes from 142.250.190.14:
icmp_seq=3 ttl=117 time=26.1 ms

64 bytes from 142.250.190.14:
icmp_seq=4 ttl=117 time=25.0 ms

--- google.com ping statistics ---
4 packets transmitted, 4 received, 0% packet loss
```

**Explanation:**

- `icmp_seq` — Sequence number of the packet.
- `ttl` — Time To Live.
- `time` — Round-trip time in milliseconds.
- `packet loss` — Percentage of packets that did not receive replies.

Lower response time generally indicates better network responsiveness.

## 5. `ip` — Network Configuration and Management

**Purpose:** Displays and manages network interfaces, IP addresses, routing tables, and network links. It is the modern replacement for many `ifconfig` and `route` operations.

**Syntax:**

```bash
ip addr
```

Other useful variations:

```bash
ip link
ip route
ip addr show
```

**Sample output (`ip addr`):**

```text
1: lo: <LOOPBACK,UP>
    inet 127.0.0.1/8

2: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP>
    link/ether 00:1a:2b:3c:4d:5e
    inet 192.168.1.10/24
    brd 192.168.1.255
```

**Explanation:**

- `lo` — Loopback interface.
- `eth0` — Ethernet interface.
- `link/ether` — MAC address.
- `inet` — IPv4 address.
- `/24` — CIDR prefix indicating the subnet mask.
- `UP` — Interface is administratively enabled.
- `LOWER_UP` — Physical/network link is detected.

## 6. `traceroute` — Route Tracking

**Purpose:** Displays the path taken by network packets from your computer to a destination server. It identifies intermediate routers (hops).

**Syntax:**

```bash
traceroute google.com
```

**Sample output:**

```text
traceroute to google.com (142.250.190.14)

1  192.168.1.1       2.1 ms
2  10.10.0.1         8.5 ms
3  172.16.1.1       15.2 ms
4  142.250.190.14   25.6 ms
```

**Explanation:**

- Each numbered line represents a hop (router).
- IP addresses identify intermediate routers.
- Time in milliseconds represents the response time for each hop.
- `* * *` indicates that a hop did not respond within the timeout.

It helps identify where delays or routing problems occur.

## 7. `mtr` — My Traceroute

**Purpose:** Combines the functionality of `ping` and `traceroute`. It continuously monitors the route and displays packet loss and latency at each hop.

**Syntax:**

```bash
mtr google.com
```

**Sample output:**

```text
Host             Loss%   Snt   Last   Avg   Best  Wrst
192.168.1.1       0.0%    10    2.0    2.5   1.8   4.0
10.10.0.1         0.0%    10    8.0    8.5   7.2  10.0
172.16.1.1       10.0%    10   15.0   16.0  14.0  20.0
google.com         0.0%    10   25.0   26.0  24.0  30.0
```

**Explanation:**

- `Loss%` — Percentage of packets lost.
- `Snt` — Number of packets sent.
- `Last` — Last recorded response time.
- `Avg` — Average response time.
- `Best` — Lowest response time.
- `Wrst` — Highest response time.

It is useful for diagnosing network performance issues.

## 8. `netstat` — Network Statistics

**Purpose:** Displays network connections, listening ports, routing tables, and network statistics.

**Syntax:**

```bash
netstat -tuln
```

Other useful commands:

```bash
netstat -an
netstat -r
netstat -s
```

**Sample output (`netstat -tuln`):**

```text
Proto  Recv-Q  Send-Q  Local Address      Foreign Address  State

tcp    0       0       0.0.0.0:22         0.0.0.0:*        LISTEN
tcp    0       0       127.0.0.1:3306     0.0.0.0:*        LISTEN
udp    0       0       0.0.0.0:68         0.0.0.0:*
```

**Explanation:**

- `-t` — Display TCP connections.
- `-u` — Display UDP connections.
- `-l` — Display listening sockets.
- `-n` — Display numerical IP addresses and port numbers.
- `LISTEN` — Service is waiting for incoming connections.
- Port `22` — Commonly used by SSH.
- Port `3306` — Commonly used by MySQL.

**Note:** `ss` is the modern replacement for `netstat`.

## 9. `whois` — Domain Information Lookup

**Purpose:** Retrieves registration information about a domain name or IP address, such as registrar, registration dates, and name servers.

**Syntax:**

```bash
whois google.com
```

**Sample output:**

```text
Domain Name: GOOGLE.COM
Registrar: MarkMonitor Inc.
Creation Date: 1997-09-15
Registry Expiry Date: 2028-09-14
Name Server: NS1.GOOGLE.COM
Name Server: NS2.GOOGLE.COM
Domain Status: clientDeleteProhibited
```

**Explanation:**

- `Domain Name` — Name of the registered domain.
- `Registrar` — Company managing the domain registration.
- `Creation Date` — Domain registration date.
- `Expiry Date` — Registration expiry date.
- `Name Server` — DNS servers associated with the domain.
- `Domain Status` — Current registration restrictions/status.

Some registration details may be hidden due to privacy protection.

## 10. `nmap` — Network Mapper

**Purpose:** Scans networks and devices to identify active hosts, open ports, and running services.

**Syntax:**

```bash
nmap 192.168.1.1
```

To scan a particular network:

```bash
nmap 192.168.1.0/24
```

**Sample output:**

```text
Starting Nmap Scan

Nmap scan report for 192.168.1.1
Host is up (0.0020s latency).

PORT     STATE   SERVICE
22/tcp   open    ssh
80/tcp   open    http
443/tcp  open    https
3306/tcp closed  mysql

Nmap done: 1 IP address scanned
```

**Explanation:**

- `Host is up` — Target device is reachable.
- `PORT` — Port number and protocol.
- `STATE` — Status of the port.
- `open` — A service is accepting connections.
- `closed` — Port is reachable but no service is listening.
- `SERVICE` — Common service associated with the port.

Nmap is widely used for network auditing and security testing. Only scan networks you own or have permission to test.

## 11. `nmcli` — Network Manager Command Line Interface

**Purpose:** Manages network connections, Wi-Fi, and network devices using the NetworkManager service.

**Syntax:**

```bash
nmcli device status
```

Other useful commands:

```bash
nmcli connection show
nmcli device wifi list
nmcli networking
```

**Sample output (`nmcli device status`):**

```text
DEVICE  TYPE      STATE      CONNECTION
eth0    ethernet  connected  Wired connection 1
wlan0   wifi      connected  Home_WiFi
lo      loopback  connected  lo
```

**Explanation:**

- `DEVICE` — Network interface name.
- `TYPE` — Type of network interface.
- `STATE` — Current connection status.
- `CONNECTION` — Active connection profile name.

It can also be used to connect to Wi-Fi networks and configure network settings.

## 12. `speedtest-cli` — Internet Speed Testing

**Purpose:** Measures internet download speed, upload speed, and latency using Speedtest-compatible servers.

**Syntax:**

```bash
speedtest-cli
```

**Sample output:**

```text
Retrieving speedtest.net configuration...
Testing from Internet Service Provider...

Download: 85.42 Mbit/s
Upload:   32.18 Mbit/s
Ping:     12.45 ms
```

**Explanation:**

- `Download` — Speed at which data is received.
- `Upload` — Speed at which data is sent.
- `Ping` — Response time to the test server.

Higher download/upload speeds generally indicate faster data transfer.

## 13. `bmon` — Bandwidth Monitor

**Purpose:** Monitors network bandwidth usage and displays real-time incoming and outgoing traffic through a terminal interface.

**Syntax:**

```bash
bmon
```

**Sample output:**

```text
Interface: eth0

RX (Received):
Rate:  2.5 Mbit/s
Bytes: 150 MB
Packets: 12000

TX (Transmitted):
Rate:  1.2 Mbit/s
Bytes: 80 MB
Packets: 8500
```

**Explanation:**

- `RX` — Incoming network traffic.
- `TX` — Outgoing network traffic.
- `Rate` — Current data transfer rate.
- `Bytes` — Total amount of transferred data.
- `Packets` — Number of packets transferred.

Unlike `iftop`, which focuses on traffic between individual hosts, `bmon` focuses on interface-level bandwidth monitoring.

## 14. `nslookup` — DNS Lookup

**Purpose:** Queries DNS servers to find the IP address associated with a domain name or vice versa.

**Syntax:**

```bash
nslookup google.com
```

**Sample output:**

```text
Server:     192.168.1.1
Address:    192.168.1.1#53

Non-authoritative answer:
Name:       google.com
Address:    142.250.190.14
```

**Explanation:**

- `Server` — DNS server used for the query.
- `Address` — IP address of the DNS server.
- `Name` — Domain name being queried.
- `Non-authoritative answer` — Response obtained from a DNS resolver rather than directly from the domain's authoritative DNS server.

It is useful for troubleshooting DNS resolution problems.

## 15. `tcpdump` — Packet Analyzer

**Purpose:** Captures and analyzes network packets traveling through a network interface. It helps troubleshoot network communication and inspect protocols.

**Syntax:**

```bash
sudo tcpdump -i eth0
```

To capture only ICMP packets:

```bash
sudo tcpdump -i eth0 icmp
```

**Sample output:**

```text
12:30:15.123 IP 192.168.1.10 > 8.8.8.8:
ICMP echo request, id 1234, seq 1

12:30:15.145 IP 8.8.8.8 > 192.168.1.10:
ICMP echo reply, id 1234, seq 1

12:30:16.123 IP 192.168.1.10 > 8.8.8.8:
ICMP echo request, id 1234, seq 2
```

**Explanation:**

- `-i eth0` — Capture packets on the eth0 interface.
- `IP` — Internet Protocol packet.
- `>` — Direction of packet transmission.
- `ICMP echo request` — Request sent by the `ping` command.
- `ICMP echo reply` — Response received from the destination.

It is useful for analyzing network traffic and debugging communication problems.

---

# Quick Revision Table

This table is useful for lab exams and viva preparation.

| Command | Main purpose |
|---|---|
| `ifconfig` | Display/configure network interfaces |
| `ifplugstatus` | Check Ethernet cable connection |
| `iftop` | Monitor bandwidth between hosts |
| `ping` | Test network connectivity |
| `ip` | Manage IP addresses and routes |
| `traceroute` | Display packet route |
| `mtr` | Continuous route and packet-loss monitoring |
| `netstat` | Display network connections and statistics |
| `whois` | Retrieve domain registration information |
| `nmap` | Scan hosts and open ports |
| `nmcli` | Manage network connections |
| `speedtest-cli` | Measure internet speed |
| `bmon` | Monitor interface bandwidth |
| `nslookup` | Query DNS information |
| `tcpdump` | Capture and analyze packets |

## Important Differences

| Commands | Difference |
|---|---|
| `ifconfig` vs `ip` | Older vs modern network interface management |
| `iftop` vs `bmon` | Host-to-host traffic vs interface bandwidth |
| `ping` vs `traceroute` | Tests reachability vs identifies packet route |
| `traceroute` vs `mtr` | One-time route tracing vs continuous monitoring |
| `netstat` vs `nmap` | Displays local network information vs scans hosts and ports |
| `nslookup` vs `whois` | DNS information vs domain registration information |
| `iftop` vs `tcpdump` | Bandwidth monitoring vs packet-level analysis |

## Installation Commands

If these utilities are not installed on Ubuntu/Debian, you can install them using:

```bash
sudo apt update

sudo apt install net-tools
sudo apt install iftop
sudo apt install ifplugd
sudo apt install traceroute
sudo apt install mtr
sudo apt install whois
sudo apt install nmap
sudo apt install network-manager
sudo apt install speedtest-cli
sudo apt install bmon
sudo apt install dnsutils
sudo apt install tcpdump
```

## Lab Exam Tip

You do not need to memorize every line of the output. For each command, understand:

1. Its purpose.
2. Its basic syntax.
3. Three or four important output fields.
4. One practical use.

That is usually enough to explain what the command does and interpret its result in a practical exam.
