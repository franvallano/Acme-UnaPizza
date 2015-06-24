package utilities;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class PasswordCode {
	 public static String encode(String input){
	 	Md5PasswordEncoder encoder;
	 	String result;
	 	 
	 	encoder = new Md5PasswordEncoder();
	 	 
	 	result = encoder.encodePassword(input, null);
	 	 
	 	return result;
	 }
}
	