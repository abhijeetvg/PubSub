package edu.umn.pubsub.server;

import java.rmi.RemoteException;
import java.util.Set;

import edu.umn.pubsub.common.server.ServerInfo;
import edu.umn.pubsub.common.util.LogUtil;
import edu.umn.pubsub.common.content.Article;
import edu.umn.pubsub.server.cache.ServerInfoCache;

/**
 * This is a singleton ServerManager that manages connections with other group servers.
 * @author prashant
 *
 */
public final class ServerManager {
	private final String CLASS_NAME = ServerManager.class.getSimpleName(); 
	private static ServerManager instance = null;
	
	private ServerManager() {
		// Singleton
	}
	
	public static ServerManager getInstance() {
		if(instance == null) {
			synchronized (ServerManager.class) {
				if(instance == null) {
					instance = new ServerManager();
				}
				
			}
		}
		return instance;
	}

	public boolean JoinServer(ServerInfo serverInfo) throws RemoteException {
		String method = CLASS_NAME  + "JoinServer()";
		LogUtil.log(method, "Joining server: " + serverInfo);
		return ServerInfoCache.getInstance().addServer(serverInfo);
	}

	public boolean LeaveServer(ServerInfo serverInfo) throws RemoteException {
		String method = CLASS_NAME  + "LeaveServer()";
		LogUtil.log(method, "Leaving server: " + serverInfo);
		return ServerInfoCache.getInstance().removeServer(serverInfo);
	}

	public boolean PublishServer(ServerInfo senderServerInfo, Article article)
			throws RemoteException {
		String method = CLASS_NAME  + "PublishServer()";
		Set<ServerInfo> servers = ServerInfoCache.getInstance().getServers();
		if(servers.isEmpty()) {
			LogUtil.log(method, "No servers to publish");
			return true;
		}
		servers.remove(senderServerInfo);
		for (ServerInfo serverInfo : servers) {
			LogUtil.log(method, "Publishing article: " + article + " to server: " + serverInfo);
			// TODO prashant publish via UDP
		}
		LogUtil.log(method, "Publishing article: " + article + " to clients");
		ClientManager.getInstance().Publish(article);
		return true;
	}
	
	public boolean PublishServer(Article article)
			throws RemoteException {
		String method = CLASS_NAME  + "PublishServer()";
		Set<ServerInfo> servers = ServerInfoCache.getInstance().getServers();
		if(servers.isEmpty()) {
			LogUtil.log(method, "No servers to publish");
			return true;
		}
		for (ServerInfo serverInfo : servers) {
			LogUtil.log(method, "Publishing article: " + article + " to server: " + serverInfo);
			// TODO prashant publish via UDP
		}
		return true;
	}

}
