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
		int notHost = MessageManager.whichHostNotUse(login);
		for(int i = (notHost + 1)%3; i != notHost; i = (i+1)%3){
			if(!cm.tryToConnectDirectly(i)){
				continue;
			}
			Long len = file.length();
			String message = MessageManager.buildMessage(SENDCODE,sessionID,len.toString(),file.getName());
			cm.sendMessage(message);
			String[] response = MessageManager.decodeResponse(cm.readMessage());
			
			if(response[0].equals(ResponseCode.OK.name())){
				cm.streamFile(file);
			}
		}
		
		return false;
		
	}
}
