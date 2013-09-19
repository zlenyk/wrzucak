package cont;

import java.io.File;
import java.io.IOException;

import cont.ConnectionManager.MessageManager;

public class SendFileProtocole {

	private enum ResponseCode{
		OK,WRONG,ERROR;
	}
	
	private static final String SENDCODE = "SEN";
	ConnectionManager cm;
	SendFileProtocole(){
		cm = new ConnectionManager();
	}
	
	
	public boolean start(String sessionID,String login,File file) throws IOException{
		if(cm.tryToConnect(login) == false){
			return false;
		}
		Long len = file.length();
		String message = MessageManager.buildMessage(SENDCODE,sessionID,len.toString(),file.getName());
		cm.write(message);
		String[] response = MessageManager.decodeResponse(cm.read());
		
		if(response[0].equals(ResponseCode.OK.name())){
			new SendFile().streamFile(file);
		}
		
		return false;
		
	}
}
