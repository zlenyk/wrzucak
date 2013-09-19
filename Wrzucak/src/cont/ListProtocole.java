package cont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<String> start(String sessionID,String login) throws IOException{
		
		List<String> ret = new ArrayList<String>();
		if(cm.tryToConnect(login) == false){
			ret.add(ResponseCode.ERROR.name());
			return ret;
		}
		
		String message = MessageManager.buildMessage(LISTCODE,sessionID);
		cm.write(message);
		String[] response = MessageManager.decodeResponse(cm.read());
		
		for(int i = 0; i<response.length; i++){
			ret.add(response[i]);
		}
		return  ret;
	}
}
