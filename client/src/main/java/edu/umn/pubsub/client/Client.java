package edu.umn.pubsub.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import edu.umn.pubsub.client.cli.BaseCommand;
import edu.umn.pubsub.client.cli.CommandFactory;
import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.client.exceptions.IllegalCommandException;
import edu.umn.pubsub.client.udp.UDPConnInfo;
import edu.umn.pubsub.common.constants.RMIConstants;
import edu.umn.pubsub.common.rmi.Communicate;
import edu.umn.pubsub.common.udp.PrintLock;
import edu.umn.pubsub.common.util.LogUtil;

/**
 * RMI Client, shell interface.
 * 
 * @author Abhijeet
 *
 */
public class Client {

	private Communicate client;

	private UDPConnInfo conInfo;
	
	private static final String CMD_PROMPT = "\nPubSub-Client-1.0$ ";
	private static final String GOOD_BYE_MSG = "Good Bye! ";

	private static final String USAGE_HELP = "arguments: <rmi_server_host> <udp_host> <udp_host>";

	public Client(Communicate client, UDPConnInfo conInfo) {
		this.client = client;
		this.conInfo = conInfo;
	}

	private void executeCmd(String cmdStr) {

		try {
			BaseCommand cmd = CommandFactory.getCommand(cmdStr, conInfo);

			if (!cmd.execute(client)) {
				LogUtil.info(CommandConstants.ERR_COMMAND_EXEC_FAILED);
			}

		} catch (IllegalCommandException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Starts the shell and accepts commands. Ends when "exit" or "quit" is
	 * encountered.
	 */
	public void startShell() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String cmd;

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

					if (cmd.trim().equalsIgnoreCase("exit")
							|| cmd.trim().equalsIgnoreCase("quit")) {
						LogUtil.info("Leaving Server");
						executeCmd("leave");
						LogUtil.info(GOOD_BYE_MSG);
						break;
					}

					executeCmd(cmd);

					System.out.print(CMD_PROMPT);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Drives the client execution.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			if (3 != args.length) {
				LogUtil.info(USAGE_HELP);
			}

			Communicate client = (Communicate) Naming.lookup("rmi://" + args[0]
					+ "/" + RMIConstants.PUB_SUB_SERVICE);

			Client shell = new Client(client, new UDPConnInfo(args[1]
					, Integer.parseInt(args[2])));
			shell.startShell();

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
