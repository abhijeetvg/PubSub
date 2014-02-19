package edu.umn.pubsub.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;

import edu.umn.pubsub.common.rmi.Communicate;
import edu.umn.pubsub.common.server.ServerInfo;
import edu.umn.pubsub.common.util.LogUtil;
import edu.umn.pubsub.common.content.Article;
import edu.umn.pubsub.common.exception.IllegalServerException;
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
		String method = CLASS_NAME  + ".JoinServer()";
		LogUtil.log(method, "Joining server: " + serverInfo);
		return ServerInfoCache.getInstance().addRecievableServer(serverInfo);
	}

	public boolean LeaveServer(ServerInfo serverInfo) throws RemoteException {
		String method = CLASS_NAME  + ".LeaveServer()";
		LogUtil.log(method, "Leaving server: " + serverInfo);
		try {
			return ServerInfoCache.getInstance().removeReceivableServer(serverInfo);
		} catch (IllegalServerException e) {
			throw new RemoteException("Server not joined.",e);
		}
	}

	public boolean PublishServer(ServerInfo senderServerInfo, Article article)
			throws RemoteException {
		String method = CLASS_NAME  + ".PublishServer()";
		LogUtil.log(method, "Got article from server: " + senderServerInfo);
		LogUtil.log(method, "Publishing article: " + article + " to clients");
		ClientManager.getInstance().Publish(article);
		return true;
	}
	
	public boolean PublishServer(Article article)
			throws RemoteException {
		String method = CLASS_NAME  + ".PublishServer()";
		Set<ServerInfo> servers = ServerInfoCache.getInstance().getSendableServers();
		if(servers.isEmpty()) {
			LogUtil.log(method, "No servers to publish");
			return true;
		}
		for (ServerInfo serverInfo : servers) {
			LogUtil.log(method, "Publishing article: " + article + " to server: " + serverInfo);
			PublishToServer(serverInfo, article);
		}
		return true;
	}

	
	private void PublishToServer(ServerInfo serverInfo, Article article) {
		String method = CLASS_NAME + ".PublishToServer()";
		LogUtil.log(method, "Binding to server: " + serverInfo);
		
		Communicate client;
		try {
			client = (Communicate) Naming.lookup("rmi://" + serverInfo.getIp() + ":" + serverInfo.getPort() + 
					"/" + serverInfo.getBindingName());
		} catch (MalformedURLException e) {
			LogUtil.log(method, "Got Exception: " + e.getMessage() + ". Trying next server.");
			return;
		} catch (RemoteException e) {
			LogUtil.log(method, "Got Exception: " + e.getMessage() + ". Trying next server.");
			return;
		} catch (NotBoundException e) {
			LogUtil.log(method, "Got Exception: " + e.getMessage() + ". Trying next server.");
			return;
		}
		LogUtil.log(method, "DONE Binding to server: " + serverInfo);
		
		LogUtil.log(method, "Publishing article: " + article.toString() + " to server: " + serverInfo);
		try {
			client.PublishServer(serverInfo.getIp(), serverInfo.getPort(), article.toString());
		} catch (RemoteException e) {
			LogUtil.log(method, "Got Exception: " + e.getCause().getMessage() + ". Trying next server.");
			return;
		}
		LogUtil.log(method, "DONE Publishing article: " + article.toString() + " to server: " + serverInfo);
	}
}
