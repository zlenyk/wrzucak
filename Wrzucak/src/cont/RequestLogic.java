package cont;

import java.io.File;
import java.io.IOException;
import java.util.List;

import view.MyFrame;
import view.UserWindow;


public class RequestLogic {
	
	private static final Integer LOGIN = 1;
	private static final Integer REGISTRATION = 2;
	private static final Integer LOGOUT = 3;
	private static final Integer LIST = 4;
	private static final Integer RECIEVE = 5;
	
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
				List<String> lista = list(sessionID,login);
				GUIManager.list((UserWindow)mf,lista);
			}
			else if(request == RECIEVE){
				String sessionID = args[0];
				String login = args[1];
				String name = args[2];
				recieveFile(sessionID,login,name);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void acceptFile(String sessionId,String login,File f){
		try {
			new SendFileProtocole().start(sessionId,login,f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	private static String register(String login, String password) throws IOException {
		return new RegistrationProtocole().start(login, password);
	}

	private static String login(String login, String password) throws IOException {
		return new LoginProtocole().start(login, password);
	}
	
	private static boolean logout(String sessionID,String login) throws IOException {
		return new LogoutProtocole().start(sessionID,login);
	}
	
	private static List<String> list(String sessionID,String login) throws IOException{
		return new ListProtocole().start(sessionID,login);
	}
	private static boolean recieveFile(String sessionID, String login,String name) throws IOException{
		return new RecieveFileProtocole().start(sessionID,login,name);
	}
}
