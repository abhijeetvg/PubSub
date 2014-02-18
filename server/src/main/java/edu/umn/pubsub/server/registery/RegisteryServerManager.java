package edu.umn.pubsub.server.registery;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import edu.umn.pubsub.common.constants.RMIConstants;
import edu.umn.pubsub.common.exception.IllegalIPException;
import edu.umn.pubsub.common.server.ServerInfo;
import edu.umn.pubsub.common.util.LogUtil;
import edu.umn.pubsub.common.util.UDPClientUtil;
import edu.umn.pubsub.server.Server;
import edu.umn.pubsub.server.config.RegisteryServerConfig;

/**
 * This is singleton manager class for managing requests to the RegistryServer.
 * 
 * @author prashant
 *
 */
public final class RegisteryServerManager {
	private final String CLASS_NAME = RegisteryServerManager.class.getSimpleName();
	private final String commandDelimiter = ";"; 
	private static RegisteryServerManager instance = null;
	private boolean isRegistered = false;
	
	private RegisteryServerManager() {
		// Singleton, so cannot be instantiated.
	}
	
	public static RegisteryServerManager getInstance() {
		if(instance == null) {
			synchronized (RegisteryServerManager.class) {
				if(instance == null) {
					instance = new RegisteryServerManager();
				}
			}
		}
		return instance;
	}
	
	/** 
	 * Registers your server to the registery server via UDP.
	 * @return <code> true </code> if the registration was successful else <code> false </code>
	 */
	public boolean register() {
		String method = CLASS_NAME + ".register()";
		String registerCommand = "Register"  + commandDelimiter + "RMI" 
								+ commandDelimiter + Server.getServerIp() 
								+ commandDelimiter + Server.getServerPort()
								+ commandDelimiter + RMIConstants.PUB_SUB_SERVICE
								+ commandDelimiter + Server.getRMIServerPort();
		
		LogUtil.log(method, "Sending command: " + registerCommand + " via UDP");
		// TODO prashant send this command to regsitery server via UDP.
		isRegistered = true;
		return isRegistered;
	}
	
	/**
	 * Deregisters your server to the registery server via UDP.
	 * @return <code> true </code> if the deregistration was successful else <code> false </code>
	 */
	public boolean deregister() {
		String method = CLASS_NAME + "deregister()";
		String deregisterCommand = "Deregister"  + commandDelimiter + "RMI" 
								+ commandDelimiter + Server.getServerIp()
								+ commandDelimiter + Server.getServerPort();
		LogUtil.log(method, "Sending command: " + deregisterCommand + " via UDP");
		
		// TODO prashant send this command to regsitery server via UDP.
		isRegistered = false;
		return true;
	}

	/**
	 * Gets the list of active servers from 
	 */
	public Set<ServerInfo> getList() {
		String method = CLASS_NAME + ".getList()";
		String getListCommand = "GetList" + commandDelimiter 
								+ "RMI" + commandDelimiter
								+ Server.getServerIp() + commandDelimiter 
								+ Server.getServerPort() + commandDelimiter;
				
		LogUtil.log(method, "Sending getList command: " + getListCommand);
		try {
			UDPClientUtil.send(RegisteryServerConfig.REGISTER_SERVER_ADDRESS, RegisteryServerConfig.REGISTER_SERVER_PORT, getListCommand);
		} catch (IOException e) {
			LogUtil.log(method, "Got IOException while sending GetListCommand to Registry Server");
		}
		
		
		return parseGetListResult("result");
	}

	private Set<ServerInfo> parseGetListResult(String getListResult) {
		String method = CLASS_NAME + ".parseGetListResult()";
		Set<ServerInfo> activeServers = new HashSet<ServerInfo>();
		if(getListResult == null) {
			LogUtil.log(method, "Got null result in getList");
			return activeServers;
		}
		String[] split = getListResult.split(commandDelimiter);
		for(int i = 0; i < split.length; i = i + 3) {
			try {
				activeServers.add(new ServerInfo(split[i], split[i+1], Integer.parseInt(split[i+2])));
			} catch (NumberFormatException e) {
				LogUtil.log(method, "Got Invalid port: " + split[i+2] + " in getList");
			} catch (IllegalIPException e) {
				LogUtil.log(method, "Got Invalid IP: " + split[i] + " in getList");
			}
		}
		return activeServers;
	}
}


