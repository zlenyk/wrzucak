import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
public class Zadanie {
	
	
	
	public static void main(String[] arg) {
		String s = "przykladowehasło";
		String login = "SFsdf;"
		System.out.println(hashString(s));
	}
	
	
	
	public static String hashString(String mes){
		MessageDigest md5;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null; //nie powinna zdarzyć sie ta sytuacja.
		}
		md5.update(mes.getBytes());
		return (new BigInteger(1,md5.digest())).toString(16);
	}
}