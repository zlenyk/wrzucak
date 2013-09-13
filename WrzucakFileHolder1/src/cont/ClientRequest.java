package cont;

import java.io.File;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import cont.ConnectionManager.MessageManager;

public class ClientRequest implements Runnable {
	
	private enum MessageCode{
		OK,WRONG,ERROR;
	}
	
	private static final String LOGIN = "LOG";
	private static final String REGISTER = "REG";
	private static final String LOGOUT = "OUT";
	private static final String LIST = "LIS";
	private static final String SEND = "SEN";
	ConnectionManager cm;
	SocketChannel socket;
	HashMap<String,String> sessionSet;
	ClientRequest(SocketChannel s,Map<String,String> set){
		socket = s;
		sessionSet = (HashMap<String,String>) set;
		cm = new ConnectionManager(socket);
	}


	@Override
	public void run() {
		while(true){
			String[] response = MessageManager.decodeResponse(cm.readMessage());
			String login;
			String hashPassword;
			if(response[0].equals(LOGIN)){
				login = response[1];
				hashPassword = response[2];
				if( DatabaseManager.checkLogin(login, hashPassword)){
					String sessionId = makeNewSessionID(login);
					cm.sendMessage(MessageManager.buildMessage(MessageCode.OK.name(),sessionId));
					sessionSet.put(sessionId,login);
				}
				else{
					cm.sendMessage(MessageManager.buildMessage(MessageCode.WRONG.name()));
				}
			}
			else if(response[0].equals(REGISTER)){
				login = response[1];
				hashPassword = response[2];
				if( DatabaseManager.register(login, hashPassword)){
					new File("users/"+login).mkdir();
					cm.sendMessage(MessageCode.OK.name());
				}
				else{
					cm.sendMessage(MessageCode.WRONG.name());
				}
			}
			else if(response[0].equals(LOGOUT)){
				String sessionID = response[1];
				sessionSet.remove(sessionID);
				cm.sendMessage(MessageManager.buildMessage(MessageCode.OK.name()));
			}
			else if(response[0].equals(LIST)){
				String sessionID = response[1];
				//if
			}
			else if(response[0].equals(SEND)){
				String sessionID;
			}
			cm.close();
			break;
		}
	}


	private String makeNewSessionID(String login) {
		String id = "";
		int rand1 = (int)(Math.random() * (1000000 + 1));
		id += new Integer(rand1).toString();
		id += login;
		return MessageManager.hashString(id);
	}

}