package edu.umn.pubsub.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

import edu.umn.pubsub.common.constants.RMIConstants;
import edu.umn.pubsub.common.util.LogUtil;

/**
 * This is the starting point for server.
 * This will first register itself to the registry server. 
 * Then, bind {@link PubSubService} and start accepting subscriptions from the clients.
 * 
 * @author prashant
 *
 */

public class Server {
	private static final String CLASS_NAME = Server.class.getSimpleName();
	
	public static void main(String[] args) {
		String method = CLASS_NAME + ".main()";
		// TODO Register to the RegisteryServer.
		
		LogUtil.log(method, "Starting server on  " + args[0]);
		System.setProperty("java.rmi.server.hostname", args[0]);
		LogUtil.log(method, "Binding " + RMIConstants.PUB_SUB_SERVICE);
		// Bind the PubSubService RMI Registry
		try {
			Naming.rebind(RMIConstants.PUB_SUB_SERVICE, PubSubService.getInstance());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LogUtil.log(method, "DONE Binding " + RMIConstants.PUB_SUB_SERVICE);

	}

}
