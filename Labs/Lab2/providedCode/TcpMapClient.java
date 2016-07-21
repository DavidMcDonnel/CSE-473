/////////////////////////////////////////////////////////////
//Name: David McDonnel               			   //
//WUID: 420325                      			   //
//Date: 9/30/2015                                         //
//Description: TcpMapClient                               //
//- Input: This program take 4-5 arguments                //
//name - name of host server runs on        //
//,port - port number server listens on      //
//,operation - get, put, swap, or remove     //
//,arg1 - first argument to be operated on   //
//,arg2 - second argument to be operated on  //
////
//- Behavior:  This program connects to a server,    	   //
//then sends a request with an operation     //
//and one or two arguments		   //
/////////////////////////////////////////////////////////////

import java.io.*;
import java.net.*;

/**
 * usage: TcpMapClient name port operation arg1 arg2 Input: name - String port -
 * integer operation - String arg1 - String arg2 - String Send a packet to the
 * named server:port containing the given arguments. Wait for reply packet and
 * print its contents.
 **/

public class TcpMapClient {
	public static void main(String args[]) throws Exception {
		// get server address using first command-line argument
		InetAddress serverAdr = InetAddress.getByName(args[0]);
		int port = 31357;
		if (args.length == 2) {
			port = Integer.parseInt(args[1]);
		}

		Socket socket = new Socket(serverAdr, port);
		BufferedReader in = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "US-ASCII"));
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream(), "US-ASCII"));

		BufferedReader sysin = new BufferedReader(new InputStreamReader(
				System.in));
		String line;

		while (true) {
			line = sysin.readLine();
			if (line == null || line.length() == 0)
				break;
			// write line on socket and print reply to System.out
			out.write(line);
			out.newLine();
			out.flush();
			System.out.println(in.readLine());
		}

		socket.close();
	}
}