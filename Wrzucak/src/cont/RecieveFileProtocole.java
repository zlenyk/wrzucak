package cont;

import java.io.IOException;

import cont.ConnectionManager.MessageManager;

public class RecieveFileProtocole {

	private enum ResponseCode{
		OK,WRONG,ERROR;
	}
	
	private static final String RECIEVECODE = "REC";
	ConnectionManager cm;
	RecieveFileProtocole(){
		cm = new ConnectionManager();
	}
	
	
	public boolean start(String sessionID,String login,String name) throws IOException{
		if(cm.tryToConnect(login,false) == false){
			return false;
		}
		cm.sendMessage(MessageManager.buildMessage(RECIEVECODE,sessionID,name));
		String[] response = MessageManager.decodeResponse(cm.readMessage());
		
		if(response[0].equals(ResponseCode.OK.name())){
			cm.downloadFile("files/"+name);
		}
		
		return false;
		
	}
}
