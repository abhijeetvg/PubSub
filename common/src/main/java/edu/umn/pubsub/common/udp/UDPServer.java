package edu.umn.pubsub.common.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * UDP Server implementation
 * @author Abhijeet
 *
 */
public class UDPServer extends Thread {

	private static final String UDP_SERVER_NAME = "UDPServerThread";
	private static final int MAX_ARTICLE_LENGTH = 1000;
	private DatagramSocket socket = null;
	private int port;
	
	private static UDPServer udpServer = null;
	
	private UDPData udpData;
	
	private UDPServer(int port, UDPData udpData) {
		super(UDP_SERVER_NAME);
		
		this.udpData = udpData;
		this.port = port;
		
		try {
			//InetAddress addr = InetAddress.getByName("localhost");
			socket = new DatagramSocket(port);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static synchronized UDPServer getUDPServer(int port, UDPData udpData) {
		if (null == udpServer) {
			udpServer = new UDPServer(port, udpData);
		}

		return udpServer;
	}
	
	public void run() {
		
		try {
			while (true) {
				byte[] buf = new byte[MAX_ARTICLE_LENGTH];
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);

				String data = new String(packet.getData(), 0, packet.getLength());
				udpData.process(data);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void close() {
		if (null != socket) socket.close();
	}

}
