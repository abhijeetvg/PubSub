package edu.umn.pubsub.server;

import java.rmi.RemoteException;

import edu.umn.pubsub.common.server.ServerInfo;
import edu.umn.pubsub.common.content.Article;

/**
 * This is a singleton ServerManager that manages connections with other group servers.
 * @author prashant
 *
 */
public final class ServerManager {
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

	public boolean JoinServer(ServerInfo server) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean LeaveServer(ServerInfo server) throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean PublishServer(ServerInfo server, Article article)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}
}
