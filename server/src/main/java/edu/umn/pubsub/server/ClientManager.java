package edu.umn.pubsub.server;

import java.rmi.RemoteException;

import edu.umn.pubsub.common.client.ClientInfo;
import edu.umn.pubsub.common.content.Article;
import edu.umn.pubsub.common.content.Subscription;
import edu.umn.pubsub.server.cache.ClientInfoCache;

/**
 * This is a singleton ClientManager that manages subscriptions and publishing of documents for Clients.
 * @author prashant
 *
 */
public final class ClientManager {
	
	private static ClientManager instance = null;
	
	private ClientManager() {
		// This is a singleton class so private constructor.
	}
	
	public static ClientManager getInstance() {
		if(instance == null) {
			synchronized (ClientManager.class) {
				if(instance == null) {
					instance = new ClientManager();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Join the current client to the server. 
	 */
	public boolean Join(ClientInfo client) throws RemoteException {
		return ClientInfoCache.getInstance().addClient(client);
	}

	/**
	 * Remove the current client.

	 * */
	public boolean Leave(ClientInfo client) throws RemoteException {
		return ClientInfoCache.getInstance().removeClient(client);
	}

	
	public boolean Ping() throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Add the current article in the list of clients that match the subscriptions.
	 *
	 */
	public boolean Publish(ClientInfo client, Article article)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Add the client in the subscription map
	 * 
	 */
	public boolean Subscribe(ClientInfo client, Subscription subscription)
			throws RemoteException {
		return ClientInfoCache.getInstance().addSubscription(client, subscription);
	}

	/**
	 * Remove the client from subscription map.
	 * 
	 */
	public boolean Unsubscribe(ClientInfo client, Subscription subscription)
			throws RemoteException {
		return ClientInfoCache.getInstance().removeSubscription(client, subscription);
	}
}
