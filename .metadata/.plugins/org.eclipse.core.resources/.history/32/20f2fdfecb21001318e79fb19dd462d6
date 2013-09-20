package cont;
import java.io.IOException;

import cont.ConnectionManager.MessageManager;

/**
 * @author LEZY
 *
 */
public class LogoutProtocole {

	private static final String LOGOUTCODE = "OUT";
	ConnectionManager cm;
	
	private enum ResponseCode{
		OK,WRONG,ERROR;
	}
	LogoutProtocole(){
		cm = new ConnectionManager();
	}
	
	
	/**
	 * @param sessionID
	 * @return if operation was successful
	 * @throws IOException in case of connection error
	 */
	public boolean start(String sessionID,String login) throws IOException{
		
		if(cm.tryToConnect(login) == false){
			return false;
		}
		
		String message = MessageManager.buildMessage(LOGOUTCODE,sessionID,MessageManager.fillLogin(login));
		cm.write(message);
		String[] response = MessageManager.decodeResponse(cm.read());
		
		return response[0].equals(ResponseCode.OK.name());
		
	}
	
}


