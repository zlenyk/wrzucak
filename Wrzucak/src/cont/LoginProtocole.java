package cont;

import java.io.IOException;

import cont.ConnectionManager.MessageManager;

/**
 * @author LEZY
 *
 */
public class LoginProtocole {

	private static final String LOGINCODE = "LOG";
	ConnectionManager cm;

	private enum ResponseCode{
		OK,WRONG,ERROR;
	}
	LoginProtocole(){
		cm = new ConnectionManager();
		
	}
	
	
	/**
	 * @param login
	 * @param password
	 * @return sessionID or error message
	 * @throws IOException in case of connection error
	 */
	public String start(String login, String password) throws IOException{
		
		if(cm.tryToConnect(login,false) == false){
			System.out.println("Blad");
			return ResponseCode.ERROR.name();
		}
		
		String message = MessageManager.buildMessage(LOGINCODE,MessageManager.fillLogin(login),MessageManager.hashString(password));
		cm.sendMessage(message);
		String[] response = MessageManager.decodeResponse(cm.readMessage());
		if(response[0].equals(ResponseCode.OK.name())){
			return response[1];
		}
		else {
			return response[0];
		}
	}
	
}


