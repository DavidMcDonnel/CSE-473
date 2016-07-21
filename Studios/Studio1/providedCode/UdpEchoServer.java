import java.io.*;
import java.net.*;

public class UdpEchoServer {
	public static void main(String args[]) throws Exception {

		InetAddress myIp = null; int port = 12345; 
		if (args.length > 0) myIp = InetAddress.getByName(args[0]);
		if (args.length > 1) port = Integer.parseInt(args[1]);

		DatagramSocket sock = new DatagramSocket(port,myIp);

		// create two packets sharing a common buffer
		byte[] buf = new byte[1000];
		DatagramPacket pkt = new DatagramPacket(buf, buf.length);

		while (true) {
			// wait for incoming packet
			sock.receive(pkt);
			// and send it back
			sock.send(pkt);
		}
	}
}
