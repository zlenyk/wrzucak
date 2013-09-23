import java.util.HashMap;
import java.util.Map;


public class Protocole {
	private static Map<String,Client> map = new HashMap<String,Client>();
	private String nick;
	private Client cl;
	
	public Protocole(String nick,Client c) {
	    this.nick = nick;
	    cl = c;
	}
	
	private static boolean add_nick(String nick, Client c) {
	    if (map.containsKey(nick)) {
	        return false;
	    } else {
	        map.put(nick, c);
	        return true;
	    }
	}
}
