import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FtpRequest implements Runnable {

	/** The socket needed to stay connect to the client **/
	private Socket socket;

	/** The username of the current user **/
	private String user;

	/** Give if the user is authenticated **/
	private boolean isAuthenticated;

	/** The socket dedicated to communication **/
	private Socket communicationSocket;

	/** The address of connected client **/
	private InetAddress clientAddr;

	/** Stream containing the incomming data **/
	private InputStream dataIn;

	/** Stream where FtpRequest will write the data to send **/
	private OutputStream dataOut;

	/** Define if the clientIsStillConnected **/
	private boolean clientIsConnected;

	/** Temporary solution for the connection **/
	private Map<String, String> mapUserPwd;

	/** Default directory on log **/
	private String defaultDir;

	/** The current directory **/
	private String currentDir;

	/** The port to communicate with the client **/
	private int communicationPort;
	
	/** Return if the transfert mode is passive or not **/
	private boolean isPassive;
	
	/** Passive socket for passive connection **/
	private ServerSocket passiveSocket; 

	private static final String fichierUserPwd = "./userPwd.txt"; // fichier
																	// hyper
																	// sécurisé

	public FtpRequest(final Socket socket, final String folder) throws IOException {
		this.socket = socket;
		this.dataIn = this.socket.getInputStream();
		this.dataOut = this.socket.getOutputStream();
		this.clientAddr = this.socket.getInetAddress();
		this.communicationPort = this.socket.getPort();
		this.user = "";
		this.isAuthenticated = false;
		this.defaultDir = folder;
		this.currentDir = folder;
		this.clientIsConnected = true;
		// par defaut on est en active, attendre le PASV (pas implementer pour l'instant) pour passer en passive (pas bien gérer pour l'instant
		this.isPassive = false;
		this.passiveSocket = new ServerSocket(0);
		mapUserPwdInitialisation();
	}

	private void mapUserPwdInitialisation() {
		this.mapUserPwd = new HashMap<String, String>();
		this.mapUserPwd.put("test", "test");
		this.mapUserPwd.put("petita", "mdppetita");
		this.mapUserPwd.put("lesagey", "mdpleasagey");
	}

	private String cleanCmd(final String cmd) {
		return cmd.replaceAll("\n|\r", "");
	}

	public void run() {
		final byte inMess[] = new byte[2048];
		try {
			this.sendMessage(Constantes.RESPONSE_220_WELCOME);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		final BufferedReader buffRead = new BufferedReader(new InputStreamReader(dataIn));
		while (this.clientIsConnected) {
			try {
				String message = buffRead.readLine();
				processRequest(message);
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.socket.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}
		System.out.println("Client Disconnect");

	}

	void sendMessage(String message) throws IOException {
		message += Constantes.END_LINE;
		this.dataOut.write(message.getBytes());
		this.dataOut.flush();

	}
	
	void getDataChannel(){
		try {
			
			if(this.isPassive){
				System.out.println("this is passive");
				this.communicationSocket= this.passiveSocket.accept();
			}else {
				System.out.println("this is not passive");
				this.communicationSocket = new Socket();
				this.communicationSocket.connect(new InetSocketAddress(this.clientAddr, this.communicationPort));
			}
			

		} catch (final IOException e) {
			e.printStackTrace();
		}

		
	}

	public void processRequest(final String string) throws IOException {
		if (string == null) {
			return;
		}
		System.out.println("Demande : " + string);
		String[] req = string.split(" ", 2);
		String mess;
		String request = req[0].toUpperCase();
		if (Constantes.CMD_USER.equals(request) && req.length == 2) {
			mess = this.processUser(this.cleanCmd(req[1]));
		} else if (Constantes.CMD_PASS.equals(request) && req.length == 2) {
			mess = this.processPass(this.cleanCmd(req[1]));
		} else if (Constantes.CMD_RETR.equals(request) && req.length == 2) {
			mess = this.processRetr(this.cleanCmd(req[1]));
		} else if (Constantes.CMD_STOR.equals(request) && req.length == 2) {
			mess = this.processStor(this.cleanCmd(req[1]));
		} else if (Constantes.CMD_LIST.equals(request)) {
			mess = this.processList();
		} else if (Constantes.CMD_QUIT.equals(request)) {
			mess = this.processQuit();
		} else if (Constantes.CMD_PORT.equals(request) && req.length == 2) {
			mess = this.processPort(this.cleanCmd(req[1]));
		} else if (Constantes.CMD_SYST.equals(request)) {
			mess = this.processSyst();
		} else if (Constantes.CMD_TYPE.equals(request) && req.length == 2) {
			mess = this.processType(this.cleanCmd(req[1]));
		} else if (Constantes.CMD_FEAT.equals(request)) { // Commande envoyé par
															// Filezilla
			mess = this.processFeat();
		} else if (Constantes.CMD_EPSV.equals(request)) { // Commande envoyé par
															// Filezilla
			mess = this.processEpsv();
		} else if (Constantes.CMD_EPRT.equals(request) && req.length == 2) { // Commande envoyé par
															// Filezilla
			mess = this.processEprt(this.cleanCmd(req[1]));
		} else if (Constantes.CMD_PWD.equals(request)) {
			mess = this.processPwd();
		} 
		else if (Constantes.CMD_CWD.equals(request)  && req.length == 2) {
			mess = this.processCwd(this.cleanCmd(req[1]));
		}  else if (Constantes.CMD_CDUP.equals(request)) {
			mess = this.processCdup();
		} else {
			mess = "500 unkown command";
		}
		this.sendMessage(mess);
	}

	private String processCdup() {
		if (!isAuthenticated) return Constantes.ERREUR_530;
		int lastInd = this.currentDir.lastIndexOf("\\");
		if (lastInd != -1) {
			this.currentDir = this.currentDir.substring(0, lastInd);
		}
		System.out.println(this.currentDir);
		return Constantes.RESPONSE_200_CDUP;
	}

	private String processCwd(final String dir) {
		if (!isAuthenticated) return Constantes.ERREUR_530;
		this.currentDir = dir;
		return Constantes.RESPONSE_250_CWD;
	}

	private String processEprt(final String fullAdresse) {
		if (!isAuthenticated) return Constantes.ERREUR_530;
		String[] tmp = fullAdresse.split("[|]");// | est le OU de regex, il pose problème dans ce cas spécifique
		this.isPassive = false;
		this.communicationPort = Integer.parseInt(tmp[3]);// * 256 + Integer.parseInt(tmp[5]);
		System.out.println("Le client veut qu'on parle sur " + this.communicationPort);
		return Constantes.RESPONSE_200_EPRT;
	}

	private String processEpsv() {
		return Constantes.RESPONSE_200_EPSV;
	}

	private String processPwd() {
		if (!isAuthenticated) return Constantes.ERREUR_530;
		return Constantes.RESPONSE_257_PWD + this.currentDir;
	}

	private String processFeat() {
		return Constantes.RESPONSE_211_FEAT;
	}

	private String processType(final String type) {
		return Constantes.RESPONSE_200_TYPE;
	}

	private String processSyst() {
		// TODO Auto-generated method stub
		return null;
	}

	private String processPort(final String fullAdresse) {
		if (!isAuthenticated) return Constantes.ERREUR_530;
		String[] tmp = fullAdresse.split(",");
		this.communicationPort = Integer.parseInt(tmp[4]) * 256 + Integer.parseInt(tmp[5]);
		System.out.println("Le client veut qu'on parle sur " + this.communicationPort);
		return Constantes.RESPONSE_200_PORT;
	}

	public String processQuit() throws IOException {
		this.clientIsConnected = false;
		return Constantes.RESPONSE_231_QUIT;
		//on devrait envoyer le message d'ici et détruire le thread non ?
	}

	public String processList() {
		if (!isAuthenticated) return Constantes.ERREUR_530;
		try {
			this.sendMessage(Constantes.RESPONSE_150_LIST);
		} catch (final IOException e2) {
			e2.printStackTrace();
		}

		String answer = "";
		String str = null;

			final File dir = new File(this.currentDir);
			final File filesList[] = dir.listFiles();
			String currentFile = "";
			
			// pour le format de list :http://stackoverflow.com/questions/2443007/ftp-list-format

			for (final File file : filesList) {
				if (!file.isHidden()) {
					int type=6;
					String permstr="drw-rw-rw-"; 

					if (file.isFile()) {
						type=1;
						permstr= "-rw-rw-rw-";
					}
					Date date=new Date(file.lastModified());
			        SimpleDateFormat df2 = new SimpleDateFormat("yyyy MMM dd");//("dd/MM/yy");
			        String dateText = df2.format(date);
					
					String username;
					try {
						username = java.nio.file.Files.getOwner(file.toPath()).toString();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						username="unknowUser";
					}
					currentFile = String.format( "%s %d %-10s  %10d %s %s\r\n",
						    permstr, type,username, 
						    file.length(), dateText,
						    file.getName());
					
					answer += currentFile + Constantes.END_LINE;
				}
			}

		this.getDataChannel();
		
		OutputStream os = null;
		DataOutputStream dos = null;

		try {
			os = this.communicationSocket.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeBytes(answer + Constantes.RESPONSE_226_LIST+ Constantes.END_LINE);
			System.out.println("Server says : " + answer);
			this.communicationSocket.close();
			this.communicationSocket = null;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
		return Constantes.RESPONSE_226_LIST;
	}

	public String processUser(final String user) {
		if (this.mapUserPwd.containsKey(user)) {
			this.user = user;
			return Constantes.RESPONSE_331_USER;// User name okay, need
		}
		return Constantes.RESPONSE_530_USER;
	}

	public String processPass(final String pass) {
		if (this.isAuthenticated) {
			return Constantes.RESPONSE_230_PASS; // Peut être une bétise (dépend
													// si
			// on accepte les connexions
			// successives ou pas) : Already
			// logged in.
		}
		if ((this.user == null) || ("".equals(this.user.trim()))) {
			return Constantes.RESPONSE_530_PASS; // Not logged in
		}
		if (this.mapUserPwd.get(this.user).equals(pass)) {
			this.isAuthenticated = true;
			return Constantes.RESPONSE_257_PASS_BEGIN + this.defaultDir + Constantes.RESPONSE_257_PASS_END;
		}
		return "530 Not logged in"; // Not logged in
	}

	public String processRetr(final String string) {
		if (!isAuthenticated) return Constantes.ERREUR_530;
		try {
			this.sendMessage(Constantes.RESPONSE_150_LIST);
		} catch (final IOException e2) {
			e2.printStackTrace();
		}

		this.getDataChannel();

		OutputStream os = null;
		DataOutputStream dos = null;
		InputStream is = null;

		try {
			os = this.communicationSocket.getOutputStream();
			System.out.print("path retr"+this.currentDir+"/"+string);
			Path target= Paths.get(this.currentDir+"/"+string);
			Files.copy(target, os);
			dos = new DataOutputStream(os);
			dos.writeBytes( Constantes.RESPONSE_226_RETR+ Constantes.END_LINE);
			this.communicationSocket.close();
			this.communicationSocket = null;
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return Constantes.RESPONSE_226_RETR;
	}

	public String processStor(final String string) {
		if (!isAuthenticated) return Constantes.ERREUR_530;
		try {
			this.sendMessage(Constantes.RESPONSE_150_LIST);
		} catch (final IOException e2) {
			e2.printStackTrace();
		}

		this.getDataChannel();

		OutputStream os = null;
		DataOutputStream dos = null;
		InputStream is = null;

		try {
			is = this.communicationSocket.getInputStream();
			System.out.print("path stor"+this.currentDir+"/"+string);
			Path target= Paths.get(this.currentDir+"/"+string);
			Files.copy(is,target);
			os = this.communicationSocket.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeBytes( Constantes.RESPONSE_226_STOR+ Constantes.END_LINE);
			this.communicationSocket.close();
			this.communicationSocket = null;
		} catch (final IOException e) {
			e.printStackTrace();
		}

		return Constantes.RESPONSE_226_STOR;
	}

}
