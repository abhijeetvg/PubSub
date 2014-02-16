package edu.umn.pubsub.client.cli;

import edu.umn.pubsub.client.constants.CommandConstants;
import edu.umn.pubsub.client.exceptions.IllegalCommandException;
import edu.umn.pubsub.common.util.StringUtil;

/**
 * Factory that returns appropriate Command Handler to handle
 * the command sent by User.
 * 
 * @author Abhijeet
 *
 */
public class CommandFactory {

	private static final String JOIN_PREFIX = "join";
	private static final String JOIN_SERVER_PREFIX = "joinserver";
	private static final String LEAVE_PREFIX = "leave"; 
	private static final String LEAVE_SERVER_PREFIX = "leaveserver";
	private static final String SUBSCRIBE_PREFIX = "subscribe"; 
	private static final String UNSUBSCRIBE_PREFIX = "unsubscribe"; 
	private static final String PUBLISH_PREFIX = "publish"; 
	private static final String PUBLISH_SERVER_PREFIX = "publishserver";
	private static final String PING_PREFIX = "ping";
		
	public static BaseCommand getCommand(String cmd) 
			throws IllegalCommandException {
		
		String prefix = StringUtil.getCmdPrefix(cmd).trim();

		if (prefix.equalsIgnoreCase(JOIN_PREFIX)) {
			return new JoinLeave(cmd, CommandConstants.DO_COMMAND);
		} else if (prefix.equalsIgnoreCase(LEAVE_PREFIX)) {
			return new JoinLeave(cmd, CommandConstants.UNDO_COMMAND);
		} else if (prefix.equalsIgnoreCase(SUBSCRIBE_PREFIX)) {
			return new SubUnSub(cmd, CommandConstants.DO_COMMAND);
		} else if (prefix.equalsIgnoreCase(UNSUBSCRIBE_PREFIX)) {
			return new SubUnSub(cmd, CommandConstants.UNDO_COMMAND);
		} else if (prefix.equalsIgnoreCase(PUBLISH_PREFIX)) {
			return new PublishCmd(cmd);
		} else if (prefix.equalsIgnoreCase(PING_PREFIX)) {
			return new Ping(cmd);
		} else if (prefix.equalsIgnoreCase(JOIN_SERVER_PREFIX)) {
			return new JoinLeave(cmd, CommandConstants.DO_COMMAND, true);
		} else if (prefix.equalsIgnoreCase(LEAVE_SERVER_PREFIX)) {
			return new JoinLeave(cmd, CommandConstants.UNDO_COMMAND, true);
		} else if (prefix.equalsIgnoreCase(PUBLISH_SERVER_PREFIX)) {
			return new PublishCmd(cmd, true);
		}
		
		throw new IllegalCommandException(prefix + " " 
				+ CommandConstants.ERR_COMMAND_NOT_FOUND);
	}
}
