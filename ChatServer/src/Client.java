import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client implements Runnable {

	Socket client= null;
	private BufferedReader in = null;
	private PrintWriter out = null;
	String message;
	
	Client(Socket client){
		this.client = client;
        try {
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
	}
	@Override
	public void run() {
        while ((message = in.readLine()) != null) {
            response = protocol.process(msg);
            out.println("SERVER: " + response);
        }
		
	}
	public void sendMsg(String msg) {
	    out.println(msg);
	 }
}
