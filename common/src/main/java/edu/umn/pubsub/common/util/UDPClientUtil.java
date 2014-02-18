package edu.umn.pubsub.common.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 * UDP Client Util.
 * @author Abhijeet
 *
 */
public class UDPClientUtil {
	private static final String CLASS_NAME = UDPClientUtil.class.getSimpleName();
	private static final int TIMEOUT = 10*1000;
	
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
	public static void send(String host, int port, String article)
			throws IOException {
		
		DatagramSocket socket = new DatagramSocket();
		
		InetAddress addr = InetAddress.getByName(host);
		
		byte[] data = article.getBytes();
		DatagramPacket packet = new DatagramPacket(data, 0, data.length, addr, port);
		
		socket.send(packet);
		socket.close();
		
	}
	
	/**
	 * Sends a request to the host:port and returns the response.
	 *   
	 * @param host
	 * @param port
	 * @param request
	 * @throws IOException
	 */
	public static String getResponse(String host, int port, String request)
			throws IOException {
		String method = CLASS_NAME + ".getResponse()";
		DatagramSocket socket = new DatagramSocket();
		socket.setSoTimeout(TIMEOUT);
		
		InetAddress addr = InetAddress.getByName(host);
		
		byte[] data = request.getBytes();
		DatagramPacket packet = new DatagramPacket(data, 0, data.length, addr, port);
		LogUtil.log(method, "Sending request: " + request);
		socket.send(packet);
		LogUtil.log(method, "DONE Sending request: " + request);
			
		byte[] buf = new byte[1024]; 
		DatagramPacket datagramPacket = new DatagramPacket(buf, 0, buf.length, packet.getAddress(), packet.getPort());
		LogUtil.log(method, "Waiting for response.");
		try {
			socket.receive(datagramPacket);
		}catch(SocketTimeoutException ex) {
			LogUtil.log(method, "Read timed out. No response received after " + TIMEOUT + "ms. Returning null.");
			return null;
		}
			
		String response = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
		LogUtil.log(method, "Got response: " + response);
		socket.close();
		return response;
	}
	
}
