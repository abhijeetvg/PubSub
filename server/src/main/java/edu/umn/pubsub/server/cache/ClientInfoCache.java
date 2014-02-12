package edu.umn.pubsub.server.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.umn.pubsub.common.client.ClientInfo;
import edu.umn.pubsub.common.content.Article;
import edu.umn.pubsub.common.content.Subscription;

/**
 * This is a cache that stores clients and subscriptions and provides synchronized access.
 * @author prashant
 *
 */
public final class ClientInfoCache {
	private static ClientInfoCache instance = null;
	
	// Data structures for holding client and subscription information
	private final Set<ClientInfo> clients = new HashSet<ClientInfo>();
	private final Map<Subscription, Set<ClientInfo>> subscriptionClientMap = new HashMap<Subscription, Set<ClientInfo>>();
	
	private ClientInfoCache() {
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
	 */
	public synchronized boolean addClient(ClientInfo client) {
		return clients.add(client);
	}
	
	/**
	 * Removes client from client list
	 */
	public synchronized boolean removeClient(ClientInfo client) {
		return clients.remove(client);
	}
	
	/**
	 * Add subscription for given client
	 */
	public synchronized boolean addSubscription(ClientInfo client, Subscription subscription) {
		if(!clients.contains(client)) {
			throw new IllegalStateException("Client : " + client + " not joined. Cannot add subscription");
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
	public synchronized boolean removeSubscription(ClientInfo client, Subscription subscription) {
		if(!clients.contains(client)) {
			throw new IllegalStateException("Client : " + client + " not joined. Cannot remove subscription");
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
