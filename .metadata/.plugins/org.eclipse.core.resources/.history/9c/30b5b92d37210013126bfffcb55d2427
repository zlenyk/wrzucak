package cont;

import java.io.IOException;
import java.util.ArrayList;

import cont.ConnectionManager.MessageManager;


public class ListProtocole {
	
	private static final String LISTCODE = "LIS";
	ConnectionManager cm;
	
	private enum ResponseCode{
		OK,WRONG,ERROR;
	}
	
	ListProtocole(){
		cm = new ConnectionManager();
	}
	
	public String[] start(String sessionID,String login) throws IOException{
		
		ArrayList<String> ret = new ArrayList<String>();
		if(cm.tryToConnect(login) == false){
			ret.add(ResponseCode.ERROR.name());
			return (String[]) ret.toArray();
		}
		
		String message = MessageManager.buildMessage(LISTCODE,sessionID);
		cm.write(message);
		String[] response = MessageManager.decodeResponse(cm.read());
		
		if(response[0].equals(ResponseCode.OK.name())){
			for(int i = 1; i<response.length; i++){
				ret.add(response[i]);
			}
		}
		return (String[]) ret.toArray();
		
	}
}
