package edu.umn.pubsub.server.registery;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;

import edu.umn.pubsub.common.rmi.Communicate;
import edu.umn.pubsub.common.server.ServerInfo;
import edu.umn.pubsub.common.util.LogUtil;
import edu.umn.pubsub.server.Server;
import edu.umn.pubsub.server.cache.ServerInfoCache;

public class RegisteryServerGetListPoller implements Runnable{
	private final static String CLASS_NAME = RegisteryServerGetListPoller.class.getSimpleName(); 
	
	private final long POLL_INTERVAL = 60*1000;
	
	@Override
	public void run() {
		String method = CLASS_NAME + ".run()";
		while(true) {
			LogUtil.log(method, "Calling getList");
			Set<ServerInfo> registeredServers = RegisteryServerManager.getInstance().getList();
			if(registeredServers.isEmpty()) {
				LogUtil.log(method, "No active servers present. Will try again after " + POLL_INTERVAL + "ms");
				sleep(POLL_INTERVAL);
				continue;
			}
			Set<ServerInfo> currentSendableServers = ServerInfoCache.getInstance().getSendableServers();
			
			// remove the servers that no more registered.
			currentSendableServers.retainAll(registeredServers);
			// Remove all the servers that we are already connected to
			registeredServers.removeAll(currentSendableServers);
			
			
			if(registeredServers.isEmpty()) {
				LogUtil.log(method, "No new active servers to join to. Will try again after " + POLL_INTERVAL + "ms");
				sleep(POLL_INTERVAL);
				continue;
			}
			
			for (ServerInfo serverInfo : registeredServers) {
				LogUtil.log(method, "Binding to server: " + serverInfo.toString());
				
				Communicate client;
				try {
					client = (Communicate) Naming.lookup("rmi://" + serverInfo.getIp() + ":" + serverInfo.getPort() + 
							"/" + serverInfo.getBindingName());
				} catch (MalformedURLException e) {
					LogUtil.log(method, "Got Exception: " + e.getMessage() + ". Trying next server.");
					continue;
				} catch (RemoteException e) {
					LogUtil.log(method, "Got Exception: " + e.getMessage() + ". Trying next server.");
					continue;
				} catch (NotBoundException e) {
					LogUtil.log(method, "Got Exception: " + e.getMessage() + ". Trying next server.");
					continue;
				}
				LogUtil.log(method, "DONE Binding to server: " + serverInfo);
				
				LogUtil.log(method, "Joining server: " + serverInfo);
				try {
					client.JoinServer(Server.getServerIp(), Server.getServerPort());
					ServerInfoCache.getInstance().addSendableServer(serverInfo);
				} catch (RemoteException e) {
					LogUtil.log(method, "Got Exception: " + e.getCause().getMessage() );
					LogUtil.log(method, "Trying next server.");
					continue;
				}
				LogUtil.log(method, "DONE Joining server: " + serverInfo);
			}
			sleep(POLL_INTERVAL);
		}
	}

	private void sleep(long pollInterval) {
		String method = CLASS_NAME + ".sleep()";
		try {
			Thread.sleep(pollInterval);
		} catch (InterruptedException e) {
			LogUtil.log(method, "Sleep Interrupted");
		}
	}
}
