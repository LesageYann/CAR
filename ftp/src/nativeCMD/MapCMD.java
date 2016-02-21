package nativeCMD;

import java.util.Map;

public class MapCMD implements NativeCMD {
	/** The username of the current user **/
	private String user;
	
	/** Give if the user is authenticated**/
	private boolean isAuthenticated;
	/** Temporary solution for the connection **/
	private Map<String,String> mapUserPwd;

	private static final String fichierUserPwd = "./userPwd.txt"; // fichier
																	// hyper
																	// sécurisé
	
	public MapCMD(){
		this.user = "";
		this.isAuthenticated = false;
	}
	
	public boolean userExist(String user){
		if (this.mapUserPwd.containsKey(user)) {
			this.user = user;
			return true;// User name okay, need
		}
		return false;
	}
	
	public boolean goodPassword(String password) throws BadOrderException{
		if ((this.user == null) || ("".equals(this.user.trim()))) {
			throw new BadOrderException();
		}
		if (this.mapUserPwd.get(this.user).equals(password)) {
			this.isAuthenticated = true;
			return true;
		}
		return false;
	}

}
