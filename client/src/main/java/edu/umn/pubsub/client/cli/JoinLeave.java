package edu.umn.pubsub.client.cli;

import java.rmi.RemoteException;

import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.client.udp.UDPClientData;
import edu.umn.pubsub.common.rmi.Communicate;
import edu.umn.pubsub.common.udp.UDPData;
import edu.umn.pubsub.common.udp.UDPServer;

/**
 * Used to invoke Join and Leave RMI method calls. Method calls that this command
 * invoke:
 * 
 *   Join: Register to a server (provide client IP, Port for subsequent UDP 
 *         communications)
 *   Leave: Leave from a server
 *   JoinServer: Once the server receives the list of servers from registry server, it 
 *               can join to other servers to receive articles from them. 
 *   LeaveServer: Leave server.
 * 
 * @author Abhijeet
 *
 */
public class JoinLeave extends BaseCommand {

	private final int ARG_HOST = 1;
	private final int ARG_PORT = 2;
	
	private int cmdType;
	private boolean isServerCall;
	
	public JoinLeave(String cmd, int doCommand) {
		this(cmd, doCommand, false);
	}
	
	public JoinLeave(String cmd, int doCommand, boolean isServerCall) {
		super(cmd);
		this.cmdType = doCommand;
		this.isServerCall = isServerCall;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean execute(Communicate client) 
			throws NumberFormatException, RemoteException, ClientNullException {
		
		if (null == client) {
			throw new ClientNullException(CommandConstants.ERR_RMI_CLIENT_NULL);
		}
		
		//Instantiate UDP server to handle these requests
		if (cmdType == CommandConstants.DO_COMMAND) {
			UDPData udpData = new UDPClientData();
			UDPServer.getUDPServer(Integer.parseInt(getArgument(ARG_PORT)), udpData)
				.start();
		}

		if (isServerCall) {
			if (cmdType == CommandConstants.DO_COMMAND) {
				return client.JoinServer(getArgument(ARG_HOST)
					, Integer.parseInt(getArgument(ARG_PORT)));
			}
		
			if (cmdType == CommandConstants.UNDO_COMMAND) {
				return client.LeaveServer(getArgument(ARG_HOST)
					, Integer.parseInt(getArgument(ARG_PORT)));
			}
		}

		if (cmdType == CommandConstants.DO_COMMAND) {
			return client.Join(getArgument(ARG_HOST)
					, Integer.parseInt(getArgument(ARG_PORT)));
		}
		
		if (cmdType == CommandConstants.UNDO_COMMAND) {
			return client.Leave(getArgument(ARG_HOST)
					, Integer.parseInt(getArgument(ARG_PORT)));
		}
	
		return false;
		
	}

}
