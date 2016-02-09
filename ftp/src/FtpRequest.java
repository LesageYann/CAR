import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FtpRequest implements Runnable {

	/** The socket needed to stay connect to the client **/
	private Socket socket;

	/** The username of the current user **/
	private String user;

	/** Give if the user is authenticated **/
	private boolean isAuthenticated;

	/** The path of the current folder **/
	private String folderPath;

	/** Stream containing the incomming data **/
	private InputStream dataIn;

	/** Stream where FtpRequest will write the data to send **/
	private OutputStream dataOut;

	/** Temporary solution for the connection **/
	private Map<String, String> mapUserPwd;

	private static final String fichierUserPwd = "./userPwd.txt"; // fichier
																	// hyper
																	// sécurisé

	public FtpRequest(final Socket socket, final String folder)
			throws IOException {
		this.socket = socket;
		this.dataIn = this.socket.getInputStream();
		this.dataOut = this.socket.getOutputStream();
		this.user = "";
		this.isAuthenticated = false;
		this.folderPath = folder;
		mapUserPwdInitialisation();
	}

	private void mapUserPwdInitialisation() {
		this.mapUserPwd = new HashMap<String, String>();
		this.mapUserPwd.put("test", "test");
		this.mapUserPwd.put("petita", "mdppetita");
		this.mapUserPwd.put("lesagey", "mdpleasagey");
	}
	
	private  String cleanCmd(final String cmd){
		return cmd.replaceAll("\n|\r", "");
	}

	public void run() {
		byte inMess[] = new byte[2048];
		BufferedReader buffRead = new BufferedReader(new InputStreamReader(dataIn));
		while (true) {
			try {
				String message = buffRead.readLine();
				processRequest(message);
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void processRequest(final String string) throws IOException {
		String[] req = string.split(" ", 2);
		String mess;
		String request = req[0].toUpperCase();
		if ("USER".equals(request) && req.length == 2) {
			mess = this.processUser(this.cleanCmd(req[1]));
		} else if ("PASS".equals(request) && req.length == 2) {
			mess = this.processPass(this.cleanCmd(req[1]));
		} else if ("RETR".equals(request) && req.length == 2) {
			mess = this.processRetr(this.cleanCmd(req[1]));
		} else if ("STOR".equals(request) && req.length == 2) {
			mess = this.processStor(this.cleanCmd(req[1]));
		} else if ("LIST".equals(request)) {
			mess = this.processList();
		} else if ("QUIT".equals(request)) {
			mess = this.processQuit();
		} else {
			mess = "";
		}
		mess=mess + "\n";
		this.dataOut.write(mess.getBytes());
		this.dataOut.flush();
	}

	public String processQuit() {
		// TODO Auto-generated method stub
		return "";
	}

	public String processList() {
		// TODO Auto-generated method stub
		return "";
	}

	public String processUser(final String user) {
		if (this.mapUserPwd.containsKey(user)) {
			this.user = user;
			return "331 User name okay, need password";// User name okay, need
		}
		return "530 Bad username";
	}

	public String processPass(final String pass) {
		if (this.isAuthenticated) {
			return "230 Already logged in"; // Peut être une bétise (dépend si
											// on accepte les connexions
											// successives ou pas) : Already
											// logged in.
		}
		if ((this.user == null) || ("".equals(this.user.trim()))) {
			return "530 Not logged in"; // Not logged in
		}
		if (this.mapUserPwd.get(this.user).equals(pass)) {
			this.isAuthenticated = true;
			return "257 \"" + this.folderPath + "\"";
		}
		return "530 Not logged in"; // Not logged in
	}

	public String processRetr(final String string) {
		// TODO Auto-generated method stub
		return "";
	}

	public String processStor(final String string) {
		// TODO Auto-generated method stub
		return "";
	}

}
