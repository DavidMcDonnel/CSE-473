//David McDonnel
//CSE 473 John Dehart
//10/21/15
//Lab3

/** DhtClient: client for a dynamic hash table
 * usage: DhtClient myIp server cmd [key [value]]
 *
 * This program sends a command to a remote DhtServer
 * in a UDP datagram then waits for a reply packet.
 *
 * myIp - address for client port
 * server - configuration file for a DhtServer (Ip address and port)
 * cmd - can be "get" or "put"
 * key, value - optional String inputs
 *
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class DhtClient {
	public static void main(String[] args){
		if (args.length<3 || args.length>5){
			System.err.println("usage: DhtClient myIp server "
					+ "cmd [ key ] [ value ]");
			System.exit(1);
		}
		String cmd = args[2];
		String key = null;
		String value = null;
		if (args.length>3) key = args[3];
		if (args.length>4) value = args[4];

		//open sock and read remote server address
		InetAddress myIp = null;
		DatagramSocket sock = null;
		InetSocketAddress serverAdr = null;
		try{
			myIp = InetAddress.getByName(args[0]);
			sock = new DatagramSocket(0,myIp);
			BufferedReader config = new BufferedReader(
					new InputStreamReader(
						new FileInputStream(args[1]),
						"US-ASCII"));
			String line = config.readLine();
			String[] chunks = line.split(" ");
			serverAdr = new InetSocketAddress(
					chunks[0],Integer.parseInt(chunks[1]));
		} catch (Exception e){
			System.err.println("usage: DhtClient myIp server "
					+ "cmd [ key ] [ value ]");
			System.exit(1);
		}
		Packet p = new Packet();
		p.type=cmd; p.key=key; p.val=value; p.tag=12345;
		p.send(sock,serverAdr,true);
		Packet reply = new Packet();
		reply.receive(sock,true);
	}
}

