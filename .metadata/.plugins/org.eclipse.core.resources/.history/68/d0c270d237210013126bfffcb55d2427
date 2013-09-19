package cont;

import view.MyFrame;

public class GUIManager {

	private static final Integer LOGIN = 1;
	private static final Integer REGISTER = 2;
	
	public static void informUser(MyFrame mf,Integer request,String info){
		if(request == LOGIN){
			if(info.equals("OK")){
				mf.displayMessage("Logowanie udane");
			}
			else if(info.equals("WRONG")){
				mf.displayMessage("B³êdny login lub has³o");
			}
			else{
				mf.displayMessage("B³¹d serwera");
			}
		}
		else if(request == REGISTER){
			if(info.equals("OK")){
				mf.displayMessage("Rejestracja udana");
			}
			else if(info.equals("WRONG")){
				mf.displayMessage("Login zajêty");
			}
			else{
				mf.displayMessage("B³¹d serwera");
			}
		}
	}

	public static void showUserWindow(MyFrame mf,String sessionID,String login) {
		mf.showUserWindow(sessionID,login);
	}

	public static void list(MyFrame mf, String[] lista) {
		// TODO Auto-generated method stub
		
	}
}
