/////////////////////////////////////////////////////////////
//Name: David McDonnel                                    //
//WUID: 420325                                            //
//Date: 9/30/2015                                         //
//Description: TcpMapServer                               //
//- Input:	port - optional argument to specify port # //
//myIp - optional argument to specify host ip//
////
//- Behavior:  This program runs a server,        	   //
//accepts input from a client,		   //
//performs the specified operations on the   //
//argument(s),				   //
//stores arguments to or updates a hashmap   //
/////////////////////////////////////////////////////////////

import java.io.*;
import java.net.*;
import java.util.HashMap;

public class TcpMapServer {

	private HashMap<String, String> hMap;

	public TcpMapServer() {
		hMap = new HashMap<String, String>();
	}

	/**
	 * input String key perform hashmap get on input return success message
	 **/
	public String get(String key) {
		String ans = hMap.get(key);
		return ans == null ? "no match" : "success:" + ans;
	}

	/**
	 * perform hashmap get on all entries return success message
	 **/
	public String getAll() {
		String ans = "";
		for (String key : hMap.keySet()) {
			ans += "key:" + key + "::" + hMap.get(key) + " ";
		}
		return ans.substring(0, ans.length() - 1);
	}

	/**
	 * input String key, String value perform hashmap put operation on inputs
	 * return success messsage
	 **/
	public String put(String key, String value) {
		boolean b = hMap.containsKey(key);
		String ans = hMap.put(key, value);
		return b ? "updated:" + key : "success";
	}

	/**
	 * input String key perform hashmap remove on input return success message
	 **/
	public String remove(String key) {
		String ans = hMap.remove(key);
		return ans != null ? "success" : "no match";
	}

	/**
	 * input String key, String value swap two hashmap key value pairs return
	 * success message
	 **/
	public String swap(String key1, String key2) {
		boolean b = hMap.containsKey(key1) && hMap.containsKey(key2);
		String tmp = hMap.get(key1);
		hMap.put(key1, hMap.get(key2));
		hMap.put(key2, tmp);
		return b ? "success" : "no match";
	}

	public static void main(String args[]) throws Exception {

		int port = 31357;
		InetAddress myIp = null;

		if (args.length == 2) {
			myIp = InetAddress.getByName(args[0]);
			port = Integer.parseInt(args[1]);
		}
		ServerSocket socket = new ServerSocket(port, 0, myIp);
		byte[] buf = new byte[1000];
		while (true) {
			Socket client = socket.accept();
			// Once connected, create read stream to get client input
			BufferedInputStream sin = new BufferedInputStream(
					client.getInputStream());
			BufferedOutputStream sout = new BufferedOutputStream(
					client.getOutputStream());
			
			BufferedReader in = new BufferedReader(new InputStreamReader(sin,"US-ASCII"));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(sout,"US-ASCII"));
			

			String[] input;
			byte[] outBytes = null;
			String response;
			int length;
			String decoded;
			//int nbytes=0;
			// Instance of class to hold hashmap
			TcpMapServer ms = new TcpMapServer();

			while (true) {

				// wait for incoming packet
				// parse incoming packet
				//nbytes = in.read(buf, 0, buf.length);
				//decoded = new String(buf, 0, nbytes);
				decoded=in.readLine();
				if(decoded == "" || decoded == null){
					break;
				}
				input = decoded.split(":");
				length = input.length;
				// handle all cases of operation input
				// For each case test if correct # of arguments
				// Then record response or mark as bad data
				switch (input[0]) {
				case "get":
					response = length == 2 ? ms.get(input[1]) : "bad";
					break;
				case "put":
					response = length == 3 ? ms.put(input[1], input[2]) : "bad";
					break;
				case "remove":
					response = length == 2 ? ms.remove(input[1]) : "bad";
					break;
				case "swap":
					response = length == 3 ? ms.swap(input[1], input[2])
							: "bad";
					break;
				case "get all":
					if(ms.hMap.isEmpty()){
						response = "empty";
					}else{
						response = length == 1 ? ms.getAll() : "bad";
					}
					break;
				default:
					response = "bad";
					break;
				}
				// If bad data respond with error
				if (response == "bad") {
					response = "error:unrecognizable input:" + decoded;
				}

				// and send it back
				//outBytes = response.getBytes("US-ASCII");

				//out.write(outBytes, 0, outBytes.length);
				//out.flush();
				out.write(response);
				out.newLine();
				out.flush();
			}
			in.close();
			out.close();
			sin.close();
			sout.close();
			client.close();
			continue;
		}
	}
}
