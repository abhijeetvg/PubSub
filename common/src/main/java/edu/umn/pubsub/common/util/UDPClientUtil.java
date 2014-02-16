package edu.umn.pubsub.common.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import edu.umn.pubsub.common.content.Article;

/**
 * UDP Client Util.
 * @author Abhijeet
 *
 */
public class UDPClientUtil {
	
	private UDPClientUtil() {
	}
	
	/**
	 * Send article data gram packet to given host.
	 *   
	 * @param host
	 * @param port
	 * @param article
	 * @throws IOException
	 */
	public static void send(String host, int port, Article article) 
			throws IOException {
		
		DatagramSocket socket = new DatagramSocket(port);
		
		InetAddress addr = InetAddress.getByName(host);
		
		byte[] data = article.toString().getBytes();
		DatagramPacket packet = new DatagramPacket(data, 0, addr, data.length);
		
		socket.send(packet);
		
		socket.close();
		
	}
	
}
