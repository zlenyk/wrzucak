package cont;

import java.io.IOException;

import cont.ConnectionManager.MessageManager;

/**
 * @author LEZY
 *
 */
public class LoginProtocole {

	private static final String LOGINCODE = "LOGIN";
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
	 * @return sessionID or 
	 * @throws IOException in case of connection error
	 */
	public String start(String login, String password) throws IOException{
		
		if(tryToConnect(login,cm) == false){
			return ResponseCode.ERROR.name();
		}
		
		String message = MessageManager.buildMessage(LOGINCODE,login,MessageManager.hashString(password));
		cm.write(message);
		String[] response = MessageManager.decodeResponse(cm.read());
		
		if(response[0] == ResponseCode.OK.name()){
			return response[1];
		}
		else {
			return response[0];
		}
	}
	
	
	public boolean tryToConnect(String login,ConnectionManager cm){
		int NOThostNr = MessageManager.whichHostNotUse(login);
		if(cm.connectToHost(++NOThostNr)){
			return true;
		}
		else{
			return cm.connectToHost(++NOThostNr);
		}
	}
}


