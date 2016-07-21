/////////////////////////////////////////////////////////////
// Name: David McDonnel               			   //
// WUID: 420325                      			   //
// Date: 9/8/2015                                          //
// Description: MapClient                                  //
// - Input: This program take 4-5 arguments                //
//		 name - name of host server runs on        //
//		,port - port number server listens on      //
//		,operation - get, put, swap, or remove     //
//		,arg1 - first argument to be operated on   //
//		,arg2 - second argument to be operated on  //
//							   //
// - Behavior:  This program connects to a server,    	   //
//		then sends a request with an operation     //
//		and one or two arguments		   //
/////////////////////////////////////////////////////////////

import java.io.*;
import java.net.*;

/**
 * usage: MapClient name port operation arg1 arg2
 * Input:
 * name - String
 * port - integer
 * operation - String
 * arg1 - String
 * arg2 - String
 * Send a packet to the named server:port containing the given arguments. 
 * Wait for reply packet and print its contents.
**/

public class MapClient {
	public static void main(String args[]) throws Exception {
		// get server address using first command-line argument
		InetAddress serverAdr = InetAddress.getByName(args[0]);
		int port = Integer.parseInt(args[1]);
		// open datagram socket
		DatagramSocket sock = new DatagramSocket();

		// build packet addressed to server containing formatted arguments,
		// encoded using US-ASCII Charset, then send it
		String msg = "";
		for (int i = 2; i < args.length; i++){
			msg+= args[i] + ":";
		}
		msg = msg.substring(0,msg.length()-1);
		byte[] outBuf = msg.getBytes("US-ASCII");
		DatagramPacket outPkt = new DatagramPacket(outBuf,outBuf.length,
							   serverAdr,port);
		sock.send(outPkt);	// send packet to server
		// create buffer and packet for reply, then receive it
		byte[] inBuf = new byte[1000];
		DatagramPacket inPkt = new DatagramPacket(inBuf,inBuf.length);
		sock.receive(inPkt);	// wait for reply

		// print buffer contents and close socket
		String reply = new String(inBuf,0,inPkt.getLength(),"US-ASCII");
		System.out.println(reply);
		sock.close();
	}
}
