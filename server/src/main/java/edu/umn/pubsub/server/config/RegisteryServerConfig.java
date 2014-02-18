package edu.umn.pubsub.server.config;

/**
 * Config for Registery Server
 * @author prashant
 *
 */
public final class RegisteryServerConfig {
	private RegisteryServerConfig() {
		// Constants file, so cannot be instantiated
	}
	
	public static final String REGISTER_SERVER_ADDRESS = "128.101.35.147";
	public static final int REGISTER_SERVER_PORT = 5105;
	public static final String HEARTBEAT = "heartbeat";
}
