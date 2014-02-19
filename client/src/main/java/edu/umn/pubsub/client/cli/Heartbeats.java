package edu.umn.pubsub.client.cli;

import java.rmi.RemoteException;

import edu.umn.pubsub.common.rmi.Communicate;
import edu.umn.pubsub.common.util.LogUtil;

/**
 * Used to check if server is alive every 5s.
 * 
 * Consider using Callable!
 *
 * @author Abhijeet
 *
 */
public class Heartbeats implements Runnable {

	private Communicate client;
	private static final int HEARTBEAT_INTERVEL = 5000;
	private static boolean notExit = true;

	public Heartbeats(Communicate client2) {
		this.client = client2;
	}

	public static synchronized void setNotExit(boolean notExit) {
		Heartbeats.notExit = notExit;
	}

	@Override
	public void run() {

		try {
			while (notExit && client.Ping()) {
				Thread.sleep(HEARTBEAT_INTERVEL);
			}
			
			//client.Leave(IP, Port)
		} catch (RemoteException e) {
			System.out.print("Ping Err: ");
			LogUtil.catchedRemoteException(e);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			LogUtil.info("Ping Stopped.");
		}

	}

}
