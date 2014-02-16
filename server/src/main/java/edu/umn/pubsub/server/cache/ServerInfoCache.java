package edu.umn.pubsub.server.cache;

import java.util.HashSet;
import java.util.Set;

import edu.umn.pubsub.common.server.ServerInfo;

/**
 * Cache for storing server and provides synchronized access.
 * @author prashant
 *
 */
public final class ServerInfoCache {
	private static ServerInfoCache instance = null;
	private Set<ServerInfo> servers = new HashSet<ServerInfo>();
	
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
	
	public synchronized boolean addServer(ServerInfo serverInfo) {
		return servers.add(serverInfo);
	}
	
	public synchronized boolean removeServer(ServerInfo serverInfo) {
		return servers.remove(serverInfo);
	}
	
	public synchronized Set<ServerInfo> getServers() {
		return servers;
	}
}
