package edu.umn.pubsub.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import edu.umn.pubsub.common.client.ClientInfo;
import edu.umn.pubsub.common.content.Article;
import edu.umn.pubsub.common.content.Subscription;
import edu.umn.pubsub.common.exception.IllegalArticleException;
import edu.umn.pubsub.common.exception.IllegalArticleTypeException;
import edu.umn.pubsub.common.exception.IllegalIPException;
import edu.umn.pubsub.common.exception.IllegalSubscriptionException;
import edu.umn.pubsub.common.exception.MaxArticleLengthExceededException;
import edu.umn.pubsub.common.rmi.Communicate;
import edu.umn.pubsub.common.server.ServerInfo;
import edu.umn.pubsub.common.util.LogUtil;

/**
 * This is a singleton PubSubService which manages all the Client subscriptions and other group server connections.
 * <br>This is achieved by RMI implementations of all the RMI methods in delegate classes:{@link ClientManager}
 * and {@link ServerManager}. 
 * 
 * @author prashant
 *
 */
public final class PubSubService extends UnicastRemoteObject implements Communicate {
	
	private static final long serialVersionUID = 1L;
	private static final String CLASS_NAME = PubSubService.class.getSimpleName();
	private static PubSubService instance;
	private final ClientManager clientManager;
	private final ServerManager serverManager;

	private PubSubService() throws RemoteException{
		// Singleton
		clientManager = ClientManager.getInstance();
		serverManager = ServerManager.getInstance();
	}
	
	public static PubSubService getInstance() {
		String method = CLASS_NAME +".getInstance()";
		if(instance == null) {
			synchronized (PubSubService.class) {	
				if(instance == null) {
					try {
						instance = new PubSubService();
					} catch (RemoteException e) {
						LogUtil.log(method, "FATAL got remote exception in PubSubService.");
					}
				}
			}
		}
		return instance;
	}

	
	@Override
	public boolean Join(String ip, int port) throws RemoteException {
		try {
			return clientManager.Join(new ClientInfo(ip, port));
		} catch (IllegalIPException e) {
			throw new RemoteException("Invalid IP",e);
		}
	}

	@Override
	public boolean JoinServer(String ip, int port) throws RemoteException {
		try{
			return serverManager.JoinServer(new ServerInfo(ip, port));
		} catch (IllegalIPException e) {
			throw new RemoteException("Invalid IP",e);
		}
	}

	@Override
	public boolean Leave(String ip, int port) throws RemoteException {
		try{
			return clientManager.Leave(new ClientInfo(ip, port));
		} catch (IllegalIPException e) {
			throw new RemoteException("Invalid IP",e);
		}
	}

	@Override
	public boolean LeaveServer(String ip, int port) throws RemoteException {
		try{
			return serverManager.LeaveServer(new ServerInfo(ip,port));
		} catch (IllegalIPException e) {
			throw new RemoteException("Invalid IP",e);
		}
	}

	@Override
	public boolean Ping() throws RemoteException {
		return clientManager.Ping();
	}

	@Override
	public boolean Publish(String ip, int port, String article)
			throws RemoteException {
		try {
			return clientManager.Publish(new ClientInfo(ip,port), new Article(article));
		} catch (IllegalArticleException e) {
			throw new RemoteException("Invalid Article", e); 
		} catch (IllegalIPException e) {
			throw new RemoteException("Invalid IP",e);
		} catch (IllegalArticleTypeException e) {
			throw new RemoteException("Invalid Article Type",e);
		} catch (MaxArticleLengthExceededException e) {
			throw new RemoteException("Article length exceeded",e);
		}
	}

	@Override
	public boolean PublishServer(String ip, int port, String article)
			throws RemoteException {
		try {
			return serverManager.PublishServer(new ServerInfo(ip, port), new Article(article));
		} catch (IllegalArticleException e) {
			throw new RemoteException("Invalid Article", e); 
		} catch (IllegalIPException e) {
			throw new RemoteException("Invalid IP",e);
		} catch (IllegalArticleTypeException e) {
			throw new RemoteException("Invalid Article Type",e);
		} catch (MaxArticleLengthExceededException e) {
			throw new RemoteException("Article length exceeded",e);
		}
	}

	@Override
	public boolean Subscribe(String ip, int port, String subscription)
			throws RemoteException {
		try {
			return clientManager.Subscribe(new ClientInfo(ip, port), new Subscription(subscription));
		} catch (IllegalSubscriptionException e) {
			throw new RemoteException("Invalid Subscription",e);
		} catch (IllegalIPException e) {
			throw new RemoteException("Invalid IP",e);
		} catch (IllegalArticleTypeException e) {
			throw new RemoteException("Invalid Article Type",e);
		} catch (MaxArticleLengthExceededException e) {
			throw new RemoteException("Article length exceeded",e);
		}
	}

	@Override
	public boolean Unsubscribe(String ip, int port, String subscription)
			throws RemoteException {
		try {
			return clientManager.Unsubscribe(new ClientInfo(ip,port), new Subscription(subscription));
		} catch (IllegalSubscriptionException e) {
			throw new RemoteException("Invalid Subscription", e);
		} catch (IllegalIPException e) {
			throw new RemoteException("Invalid IP",e);
		} catch (IllegalArticleTypeException e) {
			throw new RemoteException("Invalid Article Type",e);
		} catch (MaxArticleLengthExceededException e) {
			throw new RemoteException("Article length exceeded",e);
		}
	}
}
