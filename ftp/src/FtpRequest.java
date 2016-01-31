import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.ws.util.StringUtils;

public class FtpRequest implements Runnable {

	/** The socket needed to stay connect to the client	 **/
	private Socket socket;
	
	/** The username of the current user **/
	private String user;
	
	/** Give if the user is authenticated**/
	private boolean isAuthenticated;
	
	/** The path of the current folder **/
	private String folderPath;
	
	/** Stream containing the incomming data **/
	private DataInputStream dataIn;
	
	/** Stream where FtpRequest will write the data to send **/
	private DataOutputStream dataOut;
	
	/** Temporary solution for the connection **/
	private Map<String,String> mapUserPwd;
	
	private static final String fichierUserPwd = "./userPwd.txt"; // fichier hyper sécurisé

	public FtpRequest(final Socket socket, final String folder) {
		this.socket = socket;
		this.user = "";
		this.isAuthenticated = false;
		this.folderPath = folder;
	}
	
	private void mapUserPwdInitialisation() {
		this.mapUserPwd = new HashMap<String,String>();
		this.mapUserPwd.put("test", "test");
		this.mapUserPwd.put("petita", "mdppetita");
		this.mapUserPwd.put("lesagey", "mdpleasagey");
	}
	
	public void run() {
		//TODO
	}
	
	public void processRequest(final String string) {
		String[] req=string.split(" ",2);
		if("USER".equals(req[0])){
			this.processUser(req[1]); 
		}
		else if("PASS".equals(req[0])) {
			this.processPass(req[1]);
		}else if("RETR".equals(req[0])) {
			this.processRetr(req[1]);
		}else if("STOR".equals(req[0])) {
			this.processStor(req[1]);
		}else if("LIST".equals(req[0])) {
			this.processList();
		}else if("QUIT".equals(req[0])) {
			this.processQuit();
		} else {
			
		}
	}
	
	public void processQuit() {
		// TODO Auto-generated method stub
		
	}

	public void processList() {
		// TODO Auto-generated method stub
		
	}

	public int processUser(final String user) {
		this.user = user;
		return 331; //User name okay, need password.
	}

	public int processPass(final String pass) {
		if ((this.user == null) || ("".equals(this.user.trim()))) {
			return 530; // Not logged in
		}
		if (this.mapUserPwd.get(this.user).equals(pass)) {
			this.isAuthenticated = true;
			return 257;
		}
		return 530; // Not logged in
	}

	public void processRetr(final String string) {
		// TODO Auto-generated method stub
		
	}

	public void processStor(final String string) {
		// TODO Auto-generated method stub
		
	}


}
