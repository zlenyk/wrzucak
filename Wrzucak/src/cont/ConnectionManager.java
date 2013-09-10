package cont;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ConnectionManager {

	private static final String[] hostsURLs = {"127.0.0.1","127.0.0.1","127.0.0.1"};
	private static final Integer port = 12345;
	private OutputStream toHost = null;
	private Socket clientSocket = null;
	BufferedReader stdin = null;
	private InputStream fromHost = null;

	
	/**
	 * @
	 * @param number if which host to use
	 * @return if operation was successful
	 */
	public boolean connectToHost(int number){
		try {
			clientSocket = new Socket(hostsURLs[number],port);
		} catch (IOException e) {
			try {
				closeAll();
			} catch (IOException e1){e1.printStackTrace();}
			e.printStackTrace();
			return false;
		}
		try {
			stdin = new BufferedReader(new InputStreamReader(System.in));
			fromHost = clientSocket.getInputStream();
			toHost = clientSocket.getOutputStream();
		} catch (IOException e) {
			try {
				closeAll();
			} catch (IOException e1) {e1.printStackTrace();}
			return false;
		}
		return true;
	}
	
	public boolean write(String message){
		try {
			toHost.write(message.getBytes());
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	public String read() throws IOException {
		String linia = "";
		int c;
		while ((c = fromHost.read()) != '\n') {
			linia = linia + (char) c;
		}
		
		return linia;
	}
	private void closeAll() throws IOException{
		if(toHost != null){
			toHost.close();
			toHost = null;
		}
		if(fromHost != null){
			fromHost.close();
			fromHost = null;
		}
		if(stdin != null){
			stdin.close();
			stdin = null;
		}
		if(clientSocket != null){
			clientSocket.close();
			clientSocket = null;
		}
	}
	public static class MessageManager{
		public static String buildMessage(String ... args){
			String message = "";
			for(String s : args){
				message += s + ";";
			}
			return message;
		}
		public static String hashString(String mes){
			MessageDigest md5;
			try {
				md5 = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return null; //nie powinna zdarzy� sie ta sytuacja.
			}
			md5.update(mes.getBytes());
			return (new BigInteger(1,md5.digest())).toString(16);
		}
		public static String[] decodeResponse(String response){
			return response.split("\\|");
		}
		public static int whichHostNotUse(String login){
			int result = 0;
			
			return result;
		}
	}
}