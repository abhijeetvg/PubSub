package edu.umn.pubsub.server.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.umn.pubsub.common.client.ClientInfo;
import edu.umn.pubsub.common.content.Article;
import edu.umn.pubsub.common.content.Subscription;
import edu.umn.pubsub.common.exception.IllegalClientException;
import edu.umn.pubsub.common.exception.MaxClientsExceededException;
import edu.umn.pubsub.server.Server;

/**
 * This is a cache that stores clients and subscriptions and provides synchronized access.
 * @author prashant
 *
 */
public final class ClientInfoCache {
	private static ClientInfoCache instance = null;
	
	private int numOfClients = 0;
	// Data structures for holding client and subscription information
	private final Set<ClientInfo> clients = new HashSet<ClientInfo>();
	private final Map<Subscription, Set<ClientInfo>> subscriptionClientMap = new HashMap<Subscription, Set<ClientInfo>>();
	
	private ClientInfoCache() {
		if(instance != null) {
			throw new IllegalStateException();
		}
		numOfClients = 0;
		// Singleton cache
	}
	
	public static ClientInfoCache getInstance() {
		if(instance == null) {
			synchronized (ClientInfoCache.class) {
				if(instance == null) {
					instance = new ClientInfoCache();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Adds the client to the client list
	 * @throws MaxClientsExceededException 
	 */
	public synchronized boolean addClient(ClientInfo client) throws MaxClientsExceededException {
		if(numOfClients == Server.getMaxClients()) {
			throw new MaxClientsExceededException("Max number of clients(" +Server.getMaxClients()+") reached. Try again");
		}
		numOfClients++;
		return clients.add(client);
	}
	
	/**
	 * Removes client from client list
	 * @throws IllegalClientException 
	 */
	public synchronized boolean removeClient(ClientInfo client) throws IllegalClientException {
		if(!clients.contains(client)) {
			throw new IllegalClientException("Client : " + client + " not joined. Cannot remove it");
		}
		// First remove the client each of the subscription from subscription map and then remove it from  
		Set<Subscription> keySet = subscriptionClientMap.keySet();
		for (Subscription subscription : keySet) {
			subscriptionClientMap.get(subscription).remove(client);
		}
		numOfClients--;
		return clients.remove(client);
	}
	
	/**
	 * Add subscription for given client
	 */
	public synchronized boolean addSubscription(ClientInfo client, Subscription subscription) throws IllegalClientException{
		if(!clients.contains(client)) {
			throw new IllegalClientException("Client : " + client + " not joined. Cannot add subscription");
		}
		Set<ClientInfo> clients;
		if(!subscriptionClientMap.containsKey(subscription)) {
			clients = new HashSet<ClientInfo>();
			subscriptionClientMap.put(subscription, clients);
		}else{
			clients = subscriptionClientMap.get(subscription);
		}
		return clients.add(client);
	}
	
	/**
	 * Add subscription for given client.
	 * 
	 * @return <code>true</code> if the cache contained this subscription for the client.
	 * 
	 */
	public synchronized boolean removeSubscription(ClientInfo client, Subscription subscription) throws IllegalClientException{
		if(!clients.contains(client)) {
			throw new IllegalClientException("Client : " + client + " not joined. Cannot remove subscription");
		}
		if(!subscriptionClientMap.containsKey(subscription)) {
			return false;
		}
		Set<ClientInfo> set = subscriptionClientMap.get(subscription);
		return set.remove(client);
	}
	
	/**
	 * Checks if the cache contains the given client.
	 */
	public synchronized boolean containsClient(ClientInfo client) {
		return clients.contains(client);
	}
	
	/**
	 * Checks if the subscription is present for the given client.
	 */
	public synchronized boolean containsSubscription(ClientInfo client, Subscription subscription) {
		if(!subscriptionClientMap.containsKey(subscription)) {
			return false;
		}
		return subscriptionClientMap.get(subscription).contains(client);
	}
	
	/**
	 * Returns the set of clients having matching subscriptions matching to current article
	 */
	public synchronized Set<ClientInfo> getClientsWithMatchingSubscriptions(Article article) {
		Set<ClientInfo> clientsMatched = new HashSet<ClientInfo>();
		if(article == null) {
			return clientsMatched;
		}
		for(Subscription subscription : subscriptionClientMap.keySet()) {
			if(subscription.matchesArticle(article)) {
				clientsMatched.addAll(subscriptionClientMap.get(subscription));
			}
		}
		return clientsMatched;
	}
}
