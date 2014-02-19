package edu.umn.pubsub.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Set;

import edu.umn.pubsub.client.cli.BaseCommand;
import edu.umn.pubsub.client.cli.CommandFactory;
import edu.umn.pubsub.client.cli.Heartbeats;
import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.client.exceptions.IllegalCommandException;
import edu.umn.pubsub.client.udp.UDPConnInfo;
import edu.umn.pubsub.common.config.RegisteryServerConfig;
import edu.umn.pubsub.common.constants.RMIConstants;
import edu.umn.pubsub.common.exception.IllegalIPException;
import edu.umn.pubsub.common.rmi.Communicate;
import edu.umn.pubsub.common.server.ServerInfo;
import edu.umn.pubsub.common.udp.PrintLock;
import edu.umn.pubsub.common.util.LogUtil;
import edu.umn.pubsub.common.util.StringUtil;
import edu.umn.pubsub.common.util.UDPClientUtil;

/**
 * RMI Client, shell interface.
 * 
 * @author Abhijeet
 * 
 */
public class Client {

	private Communicate client;
	private ServerInfo serverInfo;
	private UDPConnInfo conInfo;

	private static final String CMD_PROMPT = "\nPubSub-Client-1.0$ ";
	private static final String GOOD_BYE_MSG = "Good Bye! ";

	private static final String USAGE_HELP = "arguments: <rmi_server_host> <rmi_server_port> <udp_host> <udp_host>";

	public Client(ServerInfo serverH, UDPConnInfo conInfo)
			throws MalformedURLException, RemoteException, NotBoundException {
		serverInfo = serverH;
		startRMIClient();
		this.conInfo = conInfo;
	}

	private void executeCmd(String cmdStr)
			throws IllegalCommandException, NumberFormatException, RemoteException, ClientNullException {

		BaseCommand cmd = CommandFactory.getCommand(cmdStr, conInfo);

		if (!cmd.execute(client)) {
			LogUtil.info(CommandConstants.ERR_COMMAND_EXEC_FAILED);
		}

	}

	/**
	 * Starts the shell and accepts commands. Ends when "exit" or "quit" is
	 * encountered.
	 *
	 * @throws IOException
	 * @throws ClientNullException
	 * @throws IllegalCommandException
	 * @throws NumberFormatException
	 */
	public void startShell() throws IOException
			, NumberFormatException, IllegalCommandException, ClientNullException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String cmd;

		// Start heart beat thread
		Thread t = new Thread(new Heartbeats(client));
		t.start();

		try {

			// TODO: Improve; this makes it slow but for now needs to
			// synchronized with UDP thread printing data.

			System.out.print(CMD_PROMPT);

			while ((cmd = in.readLine()) != null) {
				synchronized (PrintLock.printLock) {
					if (cmd.isEmpty() || cmd.startsWith("#")) {
						System.out.print(CMD_PROMPT);
						continue;
					}

					// optional feature
					if (!t.isAlive()) {
						String commandDelimiter = ";";
						String getListCommand = "GetList" + commandDelimiter
								+ "RMI" + commandDelimiter + conInfo.getHost()
								+ commandDelimiter + conInfo.getPort();
						Set<ServerInfo> serverInfoList = StringUtil
								.parseGetListResult(
										UDPClientUtil
												.getResponse(
														RegisteryServerConfig.REGISTER_SERVER_ADDRESS,
														RegisteryServerConfig.REGISTER_SERVER_PORT,
														getListCommand),
										serverInfo.getIp());

						serverInfo = serverInfoList.iterator().next();
					}

					if (cmd.trim().equalsIgnoreCase("exit")
							|| cmd.trim().equalsIgnoreCase("quit")) {
						break;
					}

					executeCmd(cmd);

					System.out.print(CMD_PROMPT);
				}
			}
		} finally {
			// LogUtil.info("Leaving Server.");
			// executeCmd("leave");
			LogUtil.info("Stopping threads.");
			t.stop();
			LogUtil.info(GOOD_BYE_MSG);
		}
	}

	/**
	 * Drives the client execution.
	 *
	 * TODO: Poor exception handling, everything is propagated and printed in 
	 * this method currently, custom err messages should be printed where the exception 
	 * is raised.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		try {

			Client shell = null;
			if (4 == args.length) {
				shell = new Client(new ServerInfo(args[0],
						Integer.parseInt(args[1])), new UDPConnInfo(args[2],
						Integer.parseInt(args[3])));
			} else if (3 == args.length) {
				shell = new Client(new ServerInfo(args[0],
						RMIConstants.RMI_DEFAULT_PORT), new UDPConnInfo(
						args[1], Integer.parseInt(args[2])));
			} else {
				LogUtil.info(USAGE_HELP);
				return;
			}

			shell.startShell();

		} catch (RemoteException e) {
			LogUtil.catchedRemoteException(e);
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalCommandException e) {
			LogUtil.error("", e.getMessage());
		} catch (ClientNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void startRMIClient() throws MalformedURLException,
			RemoteException, NotBoundException {
		client = (Communicate) Naming.lookup("rmi://" + serverInfo.getIp()
				+ ":" + serverInfo.getPort() + "/"
				+ RMIConstants.PUB_SUB_SERVICE);
	}

}
