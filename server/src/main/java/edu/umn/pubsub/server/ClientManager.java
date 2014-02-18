package edu.umn.pubsub.server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Set;

import edu.umn.pubsub.common.client.ClientInfo;
import edu.umn.pubsub.common.content.Article;
import edu.umn.pubsub.common.content.Subscription;
import edu.umn.pubsub.common.exception.IllegalClientException;
import edu.umn.pubsub.common.util.LogUtil;
import edu.umn.pubsub.common.util.UDPClientUtil;
import edu.umn.pubsub.server.cache.ClientInfoCache;

/**
 * This is a singleton ClientManager that manages subscriptions and publishing of documents for Clients.
 * @author prashant
 *
 */
public final class ClientManager {
	
	private static ClientManager instance = null;
	private final static String CLASS_NAME = ClientManager.class.getSimpleName();
	
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
		String method = CLASS_NAME + ".Join()";
		LogUtil.log(method, "Joining : "+ client);
		return ClientInfoCache.getInstance().addClient(client);
	}

	/**
	 * Remove the current client.

	 * */
	public boolean Leave(ClientInfo client) throws RemoteException {
		String method = CLASS_NAME + ".Leave()";
		LogUtil.log(method, "Leaving : "+ client);
		try {
			return ClientInfoCache.getInstance().removeClient(client);
		} catch (IllegalClientException e) {
			throw new RemoteException("Client not joined",e);
		}
	}

	
	public boolean Ping() throws RemoteException {
		// By default return true
		return true;
	}

	/**
	 * Publish the current article to all the clients that have matching subscriptions.
	 *
	 */
	public boolean Publish(ClientInfo client, Article article)
			throws RemoteException {
		String method = CLASS_NAME + ".Publish()";
		Set<ClientInfo> clients = ClientInfoCache.getInstance().getClientsWithMatchingSubscriptions(article);
		// If the clientInfo matches the client who has send this article then don't send the article to the same client again.
		clients.remove(client);
		boolean result = PublishToClients(article, clients);
		LogUtil.log(method, "Publishing article: " + article + " to servers.");
		return result &&  ServerManager.getInstance().PublishServer(article);
	}
	
	private boolean PublishToClients(Article article, Set<ClientInfo> clients) {
		String method = CLASS_NAME + ".PublishToClients()";
		if(clients.isEmpty()) {
			LogUtil.log(method, "No clients for article : " + article);
			return true;
		}
		for (ClientInfo clientInfo : clients) {
			LogUtil.log(method, "Publishing article(" + article + ") to "+ clientInfo);
			try {
				UDPClientUtil.send(clientInfo.getIp(), clientInfo.getPort(), article.toString());
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.log(method, "Got IOException while sending article: " + article + " to client: " + clientInfo);
			}
		}
		return true;
	}
	
	/**
	 * Publish the current article to all the clients that have matching subscriptions.
	 *
	 */
	public boolean Publish(Article article)
			throws RemoteException {
		return PublishToClients(article, ClientInfoCache.getInstance().getClientsWithMatchingSubscriptions(article));
	}

	/**
	 * Add the client in the subscription map
	 * 
	 */
	public boolean Subscribe(ClientInfo client, Subscription subscription)
			throws RemoteException {
		String method = CLASS_NAME + ".Subscribe()";
		LogUtil.log(method, "Adding Subscription: "+ subscription + " for client: " + client);
		try {
			return ClientInfoCache.getInstance().addSubscription(client, subscription);
		} catch (IllegalClientException e) {
			throw new RemoteException("Client not joined.",e);
		}
	}

	/**
	 * Remove the client from subscription map.
	 * 
	 */
	public boolean Unsubscribe(ClientInfo client, Subscription subscription)
			throws RemoteException {
		String method = CLASS_NAME + "Unsubscribe()";
		LogUtil.log(method, "Removing Subscription: "+ subscription + " for client: " + client);
		try {
			return ClientInfoCache.getInstance().removeSubscription(client, subscription);
		} catch (IllegalClientException e) {
			throw new RemoteException("Client not joined.",e);
		}
	}
}
