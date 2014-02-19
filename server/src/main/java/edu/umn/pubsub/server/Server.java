package edu.umn.pubsub.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import edu.umn.pubsub.common.constants.RMIConstants;
import edu.umn.pubsub.common.udp.UDPServer;
import edu.umn.pubsub.common.util.LogUtil;
import edu.umn.pubsub.common.validator.ContentValidator;
import edu.umn.pubsub.server.registery.RegisteryServerGetListPoller;
import edu.umn.pubsub.server.registery.RegisteryServerManager;
import edu.umn.pubsub.server.udp.UDPServerData;

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
	private static String serverIp = null;
	private static final int serverUDPPort = 5105;
	private static int rmiServerPort = -1;
	
	public static void main(String[] args) {
		String method = CLASS_NAME + ".main()";
		
	/*	UDPServer.getUDPServer(5105, UDPServerData.getInstance()).start();
		LogUtil.log(method, "Started udp server");
		String command = "Register;RMI;131.212.229.0;5105;PubSubService;1099";
		try {
			LogUtil.log(method, "Sending: " + command);
			UDPClientUtil.send(RegisteryServerConfig.REGISTER_SERVER_ADDRESS, RegisteryServerConfig.REGISTER_SERVER_PORT, command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		command = "GetList;RMI;128.101.35.178;5105";
		try {
			LogUtil.log(method, "Sending: " + command);
			UDPClientUtil.send(RegisteryServerConfig.REGISTER_SERVER_ADDRESS, RegisteryServerConfig.REGISTER_SERVER_PORT, command);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(args.length != 2) {
			LogUtil.log(method, "Invalid cli arguments. Usage server <server ip> <server rmi port>");
			return;
		}
		if(!ContentValidator.isValidIp(args[0])) {
			LogUtil.log(method, args[0] + " is not a valid IP.");
			return;
		}
		serverIp = args[0];
		if(!ContentValidator.isValidPort(args[1])) {
			LogUtil.log(method, args[1] + " is not a valid Port.");
			return;
		}
		rmiServerPort = Integer.parseInt(args[1]);
		
		LogUtil.log(method, "Registering to Registery Server");
		if(!RegisteryServerManager.getInstance().register()) {
			LogUtil.log(method, "FAILED to register to Registery Server. Exiting. TRY AGAIN.");
			return;
		}
		LogUtil.log(method, "DONE Registering to Registery Server");
		
		LogUtil.log(method, "Starting UDP Server");
		UDPServer.getUDPServer(serverUDPPort, UDPServerData.getInstance()).start();
		LogUtil.log(method, "DONE Starting UDP Server");
		
		LogUtil.log(method, "Starting the Get list poller thread");
		Thread thread = new Thread(new RegisteryServerGetListPoller());
		thread.start();
		LogUtil.log(method, "DONE Starting the Get list poller thread");
		
		LogUtil.log(method, "Starting server on  " + serverIp);
		System.setProperty("java.rmi.server.hostname", serverIp);
		LogUtil.log(method, "Binding " + RMIConstants.PUB_SUB_SERVICE);
		// Bind the PubSubService RMI Registry
		try {
			LocateRegistry.createRegistry(getRMIServerPort());
			Naming.rebind(RMIConstants.PUB_SUB_SERVICE, PubSubService.getInstance());
		} catch (RemoteException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		} catch (MalformedURLException e) {
			LogUtil.log(method, "Got exception " + e.getMessage() + ". Exiting.");
			System.exit(1);
		}
		LogUtil.log(method, "DONE Binding " + RMIConstants.PUB_SUB_SERVICE);

	}
	
	public static String getServerIp() {
		return serverIp;
	}
	
	public static int getServerPort() {
		return serverUDPPort;
	}
	
	public static int getRMIServerPort() {
		if(rmiServerPort == -1) {
			return RMIConstants.RMI_DEFAULT_PORT;
		}
		return rmiServerPort;
	}
}
