package cont;

import view.MyFrame;

public class GUIManager {

	private static final Integer LOGIN = 1;
	private static final Integer REGISTER = 2;
	
	public static void informUser(MyFrame mf,Integer request,String info){
		if(request == LOGIN){
			if(info == "OK"){
				mf.displayMessage("Logowanie udane");
			}
			else if(info == "WRONG"){
				mf.displayMessage("B��dny login lub has�o");
			}
			else{
				mf.displayMessage("B��d serwera");
			}
		}
		else if(request == REGISTER){
			if(info == "OK"){
				mf.displayMessage("Rejestracja udana");
			}
			else if(info == "WRONG"){
				mf.displayMessage("Login zaj�ty");
			}
			else{
				mf.displayMessage("B��d serwera");
			}
		}
	}
}
