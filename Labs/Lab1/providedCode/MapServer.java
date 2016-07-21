/////////////////////////////////////////////////////////////
// Name: David McDonnel                                    //
// WUID: 420325                                            //
// Date: 9/8/2015                                          //
// Description: MapServer                                  //
// - Input:	port - optional argument to specify port # //
//                                                         //
// - Behavior:  This program runs a server,        	   //
//		accepts input from a client,		   //
//		performs the specified operations on the   //
//		argument(s),				   //
//		stores arguments to or updates a hashmap   //
/////////////////////////////////////////////////////////////

import java.io.*;
import java.net.*;
import java.util.HashMap;


public class MapServer {

	private HashMap<String,String> hMap;
	public MapServer(){
		hMap = new HashMap<String,String>();
	}
	/**
	  *input String key
	  *perform hashmap get on input
	  *return success message
	  **/
	public String get(String key){
		String ans = hMap.get(key);
		return ans == null ? "no match" : "success:"+ans;
	}
	/**
	  *input String key, String value
	  *perform hashmap put operation on inputs
	  *return success messsage
	  **/
	public String put(String key, String value){
		boolean b = hMap.containsKey(key);
		String ans = hMap.put(key,value);
		return b ? "updated:"+key : "success";
	}
	/**
	  *input String key
	  *perform hashmap remove on input
	  *return success message
	  **/
	public String remove(String key){
		String ans = hMap.remove(key);
		return ans!=null ? "success":"no match";
	}
	/**
	  *input String key, String value
	  *swap two hashmap key value pairs
	  *return success message
	  **/
	public String swap(String key1,String key2){
		boolean b = hMap.containsKey(key1)
			    && hMap.containsKey(key2);
		String tmp = hMap.get(key1);
		hMap.put(key1,hMap.get(key2));
		hMap.put(key2,tmp);
		return b ? "success":"no match";
	}

	public static void main(String args[]) throws Exception {

		InetAddress myIp = null; int port = 31357; 
		myIp = InetAddress.getLocalHost();
		//If port present in args then set
		if (args.length > 0) port = Integer.parseInt(args[0]);

		DatagramSocket sock = new DatagramSocket(port,myIp);

		// create two packets sharing a common buffer
		byte[] buf = new byte[1000];
		DatagramPacket pkt = new DatagramPacket(buf, buf.length);
		String[] input;
		String response;
		int length;
		String data;
		MapServer ms = new MapServer();

		while (true) {
			// wait for incoming packet
			sock.receive(pkt);
			//parse incoming packet
			data = new String(pkt.getData(),0,pkt.getLength());
			input = data.split(":");
			length = input.length;
			//handle all cases of operation input
			switch (input[0]){
			  case "get":
			    response=length==2?ms.get(input[1]):"bad";
			    break;
			  case "put":
			    response=length==3?ms.put(input[1],input[2]):"bad";
			    break;
			  case "remove":
			    response=length==2?ms.remove(input[1]):"bad";
			    break;
			  case "swap":
			    response=length==3?ms.swap(input[1],input[2]):"bad";
			    break;
			  default:
			    response="bad";
			    break;
			}
			if(response=="bad"){
				response = "error:unrecognizable input:"+data;
			}
			pkt.setData(response.getBytes());

			// and send it back
			sock.send(pkt);
		}
	}
}
