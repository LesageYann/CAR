import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

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
	private InputStream dataIn;
	
	/** Stream where FtpRequest will write the data to send **/
	private OutputStream dataOut;
	
	/** Temporary solution for the connection **/
	private Map<String,String> mapUserPwd;
	
	private static final String fichierUserPwd = "./userPwd.txt"; // fichier hyper sécurisé

	public FtpRequest(final Socket socket, final String folder) throws IOException {
		this.socket = socket;
		this.dataIn = this.socket.getInputStream();
		this.dataOut = this.socket.getOutputStream();
		this.user = "";
		this.isAuthenticated = false;
		this.folderPath = folder;
		mapUserPwdInitialisation();
	}
	
	private void mapUserPwdInitialisation() {
		this.mapUserPwd = new HashMap<String,String>();
		this.mapUserPwd.put("test", "test");
		this.mapUserPwd.put("petita", "mdppetita");
		this.mapUserPwd.put("lesagey", "mdpleasagey");
	}
	
	public void run() {
		byte inMess[] = new byte[2048];
		try {
			this.dataIn.read(inMess);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	public String processUser(final String user) {
		this.user = user;
		return "331"; //User name okay, need password.
	}
	
	public String processPass(final String pass) {
		if (this.isAuthenticated) {
			return "230"; // Peut être une bétise (dépend si on accepte les connexions successives ou pas) : Already logged in. 
		}
		if ((this.user == null) || ("".equals(this.user.trim()))) {
			return "530"; // Not logged in
		}
		if (this.mapUserPwd.get(this.user).equals(pass)) {
			this.isAuthenticated = true;
			return "257";
		}
		return "530"; // Not logged in
	}

	public void processRetr(final String string) {
		// TODO Auto-generated method stub
		
	}

	public void processStor(final String string) {
		// TODO Auto-generated method stub
		
	}


}
