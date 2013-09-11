package cont;

import java.nio.channels.SocketChannel;

import cont.ConnectionManager.MessageManager;

public class ClientRequest implements Runnable {
	
	private enum MessageCode{
		OK,WRONG,ERROR;
	}
	
	private static final String LOGIN = "LOG";
	private static final String REGISTER = "REG";
	ConnectionManager cm;
	SocketChannel socket;
	ClientRequest(SocketChannel s){
		socket = s;
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
			}
			else if(response[0].equals(REGISTER)){
				login = response[1];
				hashPassword = response[2];
				if( DatabaseManager.register(login, hashPassword)){
					cm.sendMessage(MessageCode.OK.name());
				}
				else{System.out.println("NIEOK");
					cm.sendMessage(MessageCode.WRONG.name());
				}
			}
		}
	}

}
