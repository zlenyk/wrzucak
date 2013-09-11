package cont;

import java.io.IOException;


public class RequestLogic {
	
	private static final Integer LOGIN = 1;
	private static final Integer REGISTRATION = 2;
	
	private RequestLogic(){}
	public static RequestLogic getInstance(){
		return new RequestLogic();
	}
	
	public static boolean acceptRequest(int request,String ... args){
		
		boolean result = false;
		if(request == LOGIN){
			try {
				result = login(args[0],args[1]);
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		else if(request == REGISTRATION){
			result = register(args[1],args[2]);
		}
		return result;
	}

	private static boolean register(String string, String string2) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean login(String login, String password) throws IOException {
		LoginProtocole lp = new LoginProtocole();
		lp.start(login, password);
		return false;
	}
}
