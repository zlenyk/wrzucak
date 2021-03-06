package cont;

import java.io.IOException;

import cont.ConnectionManager.MessageManager;

public class RegistrationProtocole {

	private static final String REGISTERCODE = "REG";
	ConnectionManager cm;
	
	private enum ResponseCode{
		OK,WRONG,ERROR;
	}
	
	RegistrationProtocole(){
		cm = new ConnectionManager();
	}
	
	/**
	 * @param login
	 * @param password
	 * @return result of operation
	 * @throws IOException 
	 */
	public String start(String login,String password) throws IOException{
		
		if(cm.tryToConnect(login,false) == false){
			return ResponseCode.ERROR.name();
		}
		
		String message = MessageManager.buildMessage(REGISTERCODE,MessageManager.fillLogin(login),MessageManager.hashString(password));
		cm.sendMessage(message);
		
		String[] response = MessageManager.decodeResponse(cm.readMessage());
		System.out.println(response[0]);
		return response[0];

	}
}
