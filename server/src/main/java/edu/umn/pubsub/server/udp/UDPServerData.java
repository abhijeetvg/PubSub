package edu.umn.pubsub.server.udp;

import edu.umn.pubsub.common.udp.UDPData;
import edu.umn.pubsub.common.util.LogUtil;

/**
 * The class will process the data got from UDP server.
 * This handles the GetList reply and the Ping reply.
 * 
 * @author prashant
 *
 */
public final class UDPServerData implements UDPData {

	private final String CLASS_NAME = UDPServerData.class.getSimpleName();
	private static UDPServerData instance = null;
	
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
		// just log for now
		String method = CLASS_NAME + ".process()";
		LogUtil.log(method, "Got data: " + data);
	}
}
