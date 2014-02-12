package edu.umn.pubsub.server;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;

import edu.umn.pubsub.common.constants.RMIConstants;

/**
 * This is the starting point for server.
 * This will first register itself to the registry server. 
 * Then, bind {@link PubSubService} and start accepting subscriptions from the clients.
 * 
 * @author prashant
 *
 */

public class Server {
	public static void main(String[] args) {
		// TODO Register to the RegisteryServer.
		
		
		// Assign a security manager, in the event that dynamic
		// classes are loaded
		if(System.getSecurityManager() == null) {
			System.setSecurityManager(new RMISecurityManager());
		}

		// Bind the PubSubService RMI Registry
		try {
			Naming.bind(RMIConstants.PUB_SUB_SERVICE, PubSubService.getInstance());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
