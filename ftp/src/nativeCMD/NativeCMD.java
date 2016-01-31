package nativeCMD;

public interface  NativeCMD {

	boolean userExist(String user);
	
	boolean goodPassword(String password) throws BadOrderException;
	
	
}
