package edu.umn.pubsub.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.umn.pubsub.common.client.ClientInfo;
import edu.umn.pubsub.common.content.Article;
import edu.umn.pubsub.common.content.Subscription;
import edu.umn.pubsub.common.rmi.Communicate;
import edu.umn.pubsub.common.server.ServerInfo;

/**
 * This is a singleton PubSubService which manages all the Client subscriptions and other group server connections.
 * <br>This is achieved by RMI implementations of all the RMI methods in delegate classes:{@link ClientManager}
 * and {@link ServerManager}. 
 * 
 * @author prashant
 *
 */
public final class PubSubService extends UnicastRemoteObject implements Communicate {
	
	private static final long serialVersionUID = 1L;
	private static PubSubService instance;
	private final ClientManager clientManager;
	private final ServerManager serverManager;

	private PubSubService() throws RemoteException{
		// Singleton
		clientManager = ClientManager.getInstance();
		serverManager = ServerManager.getInstance();
	}
	
	public static PubSubService getInstance() {
		if(instance == null) {
			synchronized (PubSubService.class) {	
				if(instance == null) {
					try {
						instance = new PubSubService();
					} catch (RemoteException e) {
						// TODO Handle this exception
						e.printStackTrace();
					}
				}
			}
		}
		return instance;
	}

	
	@Override
	public boolean Join(String ip, int port) throws RemoteException {
		return clientManager.Join(new ClientInfo(ip, port));
	}

	@Override
	public boolean JoinServer(String ip, int port) throws RemoteException {
		return serverManager.JoinServer(new ServerInfo(ip, port));
	}

	@Override
	public boolean Leave(String ip, int port) throws RemoteException {
		return clientManager.Leave(new ClientInfo(ip, port));
	}

	@Override
	public boolean LeaveServer(String ip, int port) throws RemoteException {
		return serverManager.LeaveServer(new ServerInfo(ip,port));
	}

	@Override
	public boolean Ping() throws RemoteException {
		return clientManager.Ping();
	}

	@Override
	public boolean Publish(String ip, int port, String article)
			throws RemoteException {
		return clientManager.Publish(new ClientInfo(ip,port), new Article(article));
	}

	@Override
	public boolean PublishServer(String ip, int port, String article)
			throws RemoteException {
		return serverManager.PublishServer(new ServerInfo(ip, port), new Article(article));
	}

	@Override
	public boolean Subscribe(String ip, int port, String subscription)
			throws RemoteException {
		return clientManager.Subscribe(new ClientInfo(ip, port), new Subscription(subscription));
	}

	@Override
	public boolean Unsubscribe(String ip, int port, String subscription)
			throws RemoteException {
		return clientManager.Unsubscribe(new ClientInfo(ip,port), new Subscription(subscription));
	}
}
