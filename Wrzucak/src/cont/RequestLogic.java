package cont;

import java.io.IOException;

import view.MyFrame;


public class RequestLogic {
	
	private static final Integer LOGIN = 1;
	private static final Integer REGISTRATION = 2;
	private static final Integer LOGOUT = 3;
	private static final Integer LIST = 4;
	
	private RequestLogic(){}
	public static RequestLogic getInstance(){
		return new RequestLogic();
	}
	
	public static void acceptRequest(MyFrame mf,int request,String ... args){
		
		String result = "";
		try{
			if(request == LOGIN){
				String login = args[0];
				String password = args[1];
				result = login(login,password);
				if(!result.equals("WRONG") && !result.equals("ERROR")){
					GUIManager.informUser(mf, request, "OK");
					GUIManager.showUserWindow(mf,result,login);
				}
				else{
					GUIManager.informUser(mf, request, result);
				}
			}
			else if(request == REGISTRATION){
				result = register(args[0],args[1]);
				GUIManager.informUser(mf, request, result);
			}
			else if(request == LOGOUT){
				logout(args[0],args[1]);
			}
			else if(request == LIST){
				String sessionID = args[0];
				String login = args[1];
				String[] lista = list(sessionID,login);
				GUIManager.list(mf,lista);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private static String register(String login, String password) throws IOException {
		RegistrationProtocole rp = new RegistrationProtocole();
		return rp.start(login, password);
	}

	private static String login(String login, String password) throws IOException {
		LoginProtocole lp = new LoginProtocole();
		return lp.start(login, password);
	}
	
	private static boolean logout(String sessionID,String login) throws IOException {
		LogoutProtocole lp = new LogoutProtocole();
		return lp.start(sessionID,login);
	}
	
	private static String[] list(String sessionID,String login) throws IOException{
		ListProtocole lp = new ListProtocole();
		return lp.start(sessionID,login);
	}
}
