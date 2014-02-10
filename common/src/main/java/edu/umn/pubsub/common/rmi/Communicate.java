package edu.umn.pubsub.common.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * RMI interface for Client to Server communication. 
 *
 */
public interface Communicate extends Remote {
	
	/**
	 * 
	 * @param IP
	 * @param Port
	 * @return
	 * @throws RemoteException
	 */
	boolean JoinServer (String IP, int Port) throws RemoteException;
	
	/**
	 * 
	 * @param IP
	 * @param Port
	 * @return
	 * @throws RemoteException
	 */
	boolean LeaveServer (String IP, int Port) throws RemoteException;
	
	/**
	 * 
	 * @param IP
	 * @param Port
	 * @return
	 * @throws RemoteException
	 */
	boolean Join (String IP, int Port) throws RemoteException;
	
	/**
	 * 
	 * @param IP
	 * @param Port
	 * @return
	 * @throws RemoteException
	 */
	boolean Leave (String IP, int Port) throws RemoteException;
	
	/**
	 * 
	 * @param IP
	 * @param Port
	 * @param Article
	 * @return
	 * @throws RemoteException
	 */
	boolean Subscribe(String IP, int Port, String Article) throws RemoteException;
	
	/**
	 * 
	 * @param IP
	 * @param Port
	 * @param Article
	 * @return
	 * @throws RemoteException
	 */
	boolean Unsubscribe (String IP, int Port, String Article) throws RemoteException;
	
	/**
	 * 
	 * @param IP
	 * @param Port
	 * @param Article
	 * @return
	 * @throws RemoteException
	 */
	boolean Publish (String IP, int Port, String Article) throws RemoteException;
	
	/**
	 * 
	 * @param IP
	 * @param Port
	 * @param Article
	 * @return
	 * @throws RemoteException
	 */
	boolean PublishServer (String IP, int Port, String Article) throws RemoteException;
	
	/**
	 * 
	 * @return
	 * @throws RemoteException
	 */
	boolean Ping () throws RemoteException;
}