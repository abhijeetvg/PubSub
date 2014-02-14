package edu.umn.pubsub.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.StringTokenizer;

import sun.tools.jar.CommandLine;
import edu.umn.pubsub.client.cli.BaseCommand;
import edu.umn.pubsub.client.cli.CommandFactory;
import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.ClientNullException;
import edu.umn.pubsub.client.exceptions.IllegalCommandException;
import edu.umn.pubsub.common.constants.RMIConstants;
import edu.umn.pubsub.common.rmi.Communicate;

/**
 * RMI Client, shell interface.
 * 
 * @author Abhijeet
 *
 */
public class Client {

	CommandFactory cmdFactory;
	Communicate client;
	
	private static final String CMD_PROMPT = "Client-1.0$ ";
	private static final String GOOD_BYE_MSG = "Good Bye! ";

	public Client(Communicate client) {
		this.client = client;
	}

	private void executeCmd(String cmdStr) {
		
		try {
			BaseCommand cmd = CommandFactory.getCommand(cmdStr);
			
			if (!cmd.execute(client)) {
				System.out.println(CommandConstants.ERR_COMMAND_EXEC_FAILED);
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
	 * Starts the shell and accepts commands.
	 * Ends when "exit" or "quit" is encountered.
	 */
	public void startShell() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    String cmd;
	    
	    try {
	    	
	    	System.out.println(CMD_PROMPT);
	    	
			while ((cmd = in.readLine()) != null) {
				
				if (cmd.isEmpty() || cmd.startsWith("#")) {
					System.out.println(CMD_PROMPT);
					continue;
				}
				
				if (cmd.trim().equalsIgnoreCase("exit") 
						|| cmd.trim().equalsIgnoreCase("quit")) {
					System.out.println(GOOD_BYE_MSG);
					break;
				}
				
				executeCmd(cmd);
				
				System.out.println(CMD_PROMPT);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	/**
	 * Drives the client execution.
	 * @param args
	 */
	public static void main(String[] args) {
		if (null == System.getSecurityManager()) {
			System.setSecurityManager(new SecurityManager());
		}
		
		Registry registry;
		try {
			registry = LocateRegistry.getRegistry(args[0]);
			Communicate client = (Communicate) registry
					.lookup(RMIConstants.PUB_SUB_SERVICE);
			
			Client shell = new Client(client);
			shell.startShell();
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
