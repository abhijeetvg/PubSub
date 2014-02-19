package edu.umn.pubsub.client.cli;

import java.rmi.RemoteException;

import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.client.udp.UDPClientData;
import edu.umn.pubsub.client.udp.UDPConnInfo;
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
	
	private int cmdType;
	private boolean isServerCall;
	
	public JoinLeave(String cmd, UDPConnInfo conInfo, int doCommand) {
		this(cmd, conInfo, doCommand, false);
	}
	
	public JoinLeave(String cmd, UDPConnInfo conInfo, int doCommand, boolean isServerCall) {
		super(cmd, conInfo);
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
			UDPServer.getUDPServer(getPort(), udpData)
				.start();

			if (isServerCall) {
				return client.JoinServer(getHost(), getPort());
			}

			return client.Join(getHost(), getPort());
		}

		if (cmdType == CommandConstants.UNDO_COMMAND) {

			UDPServer.stopThread();
			
			if (isServerCall) {
				return client.LeaveServer(getHost(), getPort());
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return client.Leave(getHost(), getPort());
		}

		return false;

	}

}
