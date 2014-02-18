package edu.umn.pubsub.server.udp;

import java.io.IOException;

import edu.umn.pubsub.common.udp.UDPData;
import edu.umn.pubsub.common.util.LogUtil;
import edu.umn.pubsub.common.util.UDPClientUtil;
import edu.umn.pubsub.server.config.RegisteryServerConfig;

/**
 * The class will process the data got from UDP server.
 * This handles the GetList reply and the Ping reply.
 * 
 * @author prashant
 *
 */
public final class UDPServerData implements UDPData {

	private final String CLASS_NAME = UDPServerData.class.getSimpleName();
	private final String HEARTBEAT = "heartbeat";
	private final long TIMEOUT = 5000;
	private static UDPServerData instance = null;
	private static String buffer = null;
	
	private UDPServerData() {
		//Singleton, so private;
		if(instance != null) {
			throw new IllegalStateException();
		}
	}
	
	public static UDPServerData getInstance() {
		if(instance == null) {
			synchronized(UDPServerData.class) {
				if(instance == null) {
					instance = new UDPServerData();
				}
			}
		}
		return instance;
	}
	
	@Override
	public void process(String data) {
		String method = CLASS_NAME + ".process()";
		LogUtil.log(method, "Got data: " + data);
		if(data == null) {
			LogUtil.log(method, "Got null data. Returning....");
			return;
		}
		
		if(data.equals(HEARTBEAT)) {
			// Send the same string to the registery server
			LogUtil.log(method, "Got Heartbeat from registry server. Sending it back.");
			try {
				UDPClientUtil.send(RegisteryServerConfig.REGISTER_SERVER_ADDRESS, RegisteryServerConfig.REGISTER_SERVER_PORT, data);
			} catch (IOException e) {
				LogUtil.log(method, "Got IOexception while sending heartbeat again to the registery server");
			}
		}else{
			LogUtil.log(method, "Got GetList from registry server.");
			buffer = data;
		}
	}
	
	/**
	 * Returns the list reply got from registry server.
	 * Waits for {@code TIMEOUT} ms for reply before returning <code>null</code>.
	 * @return
	 */
	public String getList() {
		String method = CLASS_NAME + ".getList()";
		long currentTimeMillis = System.currentTimeMillis();
		while(buffer == null) {
			if(System.currentTimeMillis() - currentTimeMillis > TIMEOUT) {
				LogUtil.log(method, "Timedout while waiting for reply from registry server.");
				break;
			}
		}
		String temp = buffer;
		buffer = null;
		return temp;
	}
}
