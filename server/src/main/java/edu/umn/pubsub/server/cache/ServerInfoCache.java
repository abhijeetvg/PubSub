package edu.umn.pubsub.server.cache;

import java.util.HashSet;
import java.util.Set;

import edu.umn.pubsub.common.exception.IllegalServerException;
import edu.umn.pubsub.common.server.ServerInfo;

/**
 * Cache for storing server and provides synchronized access.
 * @author prashant
 *
 */
public final class ServerInfoCache {
	private static ServerInfoCache instance = null;
	// This is the set of servers who have joined to our server.
	private Set<ServerInfo> receivableServers = new HashSet<ServerInfo>();
	// This is the set of servers who we have joined.
	private Set<ServerInfo> sendableServers = new HashSet<ServerInfo>();
	
	private ServerInfoCache() {
		// Singleton, hence private constructor
	}
	
	public static ServerInfoCache getInstance() {
		if(instance == null) {
			synchronized (ServerInfoCache.class) { 
				if(instance == null) {
					instance = new ServerInfoCache();
				}
			}
		}
		return instance;
	}
	
	public synchronized boolean addRecievableServer(ServerInfo serverInfo) {
		return receivableServers.add(serverInfo);
	}
	
	public synchronized boolean removeReceivableServer(ServerInfo serverInfo) throws IllegalServerException {
		if(!receivableServers.contains(serverInfo)) {
			throw new IllegalServerException("Server: " + serverInfo + " not joined.");
		}
		return receivableServers.remove(serverInfo);
	}
	
	/**
	 * Returns the set of servers who have joined current server
	 * @return
	 */
	public synchronized Set<ServerInfo> getReceivableServers() {
		return receivableServers;
	}
	
	public synchronized boolean addSendableServer(ServerInfo serverInfo) {
		return sendableServers.add(serverInfo);
	}
	
	public synchronized boolean removeSendableServer(ServerInfo serverInfo) throws IllegalServerException {
		if(!sendableServers.contains(serverInfo)) {
			throw new IllegalServerException("Server: " + serverInfo + " not joined.");
		}
		return sendableServers.remove(serverInfo);
	}
	
	public synchronized Set<ServerInfo> getSendableServers() {
		return sendableServers;
	}
}
