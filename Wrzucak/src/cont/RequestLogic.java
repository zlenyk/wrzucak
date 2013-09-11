package cont;

import java.io.IOException;

import view.MyFrame;


public class RequestLogic {
	
	private static final Integer LOGIN = 1;
	private static final Integer REGISTRATION = 2;
	
	private RequestLogic(){}
	public static RequestLogic getInstance(){
		return new RequestLogic();
	}
	
	public static void acceptRequest(MyFrame mf,int request,String ... args){
		
		String result = "";
		try{
			if(request == LOGIN){
				result = login(args[0],args[1]);
			}
			else if(request == REGISTRATION){
				result = register(args[0],args[1]);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		GUIManager.informUser(mf, request, result);
	}

	private static String register(String login, String password) throws IOException {
		RegistrationProtocole rp = new RegistrationProtocole();
		return rp.start(login, password);
	}

	private static String login(String login, String password) throws IOException {
		LoginProtocole lp = new LoginProtocole();
		return lp.start(login, password);
	}
}
