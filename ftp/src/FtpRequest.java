import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import Constantes.Constantes;
import nativeCMD.BadOrderException;
import nativeCMD.NativeCMD;
import nativeCMD.NotDirException;
import nativeCMD.NotImplementedException;

public class FtpRequest implements Runnable {

	/** The socket needed to stay connect to the client **/
	private final Socket socket;

	/** Give if the user is authenticated **/
	private boolean isAuthenticated;

	/** The address of connected client **/
	private final InetAddress clientAddr;

	/** Stream containing the incomming data **/
	private final InputStream dataIn;

	/** Stream where FtpRequest will write the data to send **/
	private final OutputStream dataOut;

	/** Define if the clientIsStillConnected **/
	private boolean clientIsConnected;

	/** The port to communicate with the client **/
	private int communicationPort;
	
	/** Return if the transfert mode is passive or not **/
	private boolean isPassive;
	
	/** Passive socket for passive connection **/
	private final ServerSocket passiveSocket;

	/** Set one behavior for the resquest system**/
	private final NativeCMD command; 

	private DataChannel dataFactory;

	public FtpRequest(final Socket socket, final NativeCMD cmd, final DataChannel dataChan ) throws IOException {
		this.socket = socket;
		this.dataIn = this.socket.getInputStream();
		this.dataOut = this.socket.getOutputStream();
		this.clientAddr = this.socket.getInetAddress();
		this.communicationPort = this.socket.getPort();
		this.isAuthenticated = false;
		this.command=cmd;
		this.dataFactory=dataChan;
		this.clientIsConnected = true;
		// par defaut on est en active, attendre le PASV (pas implementer pour l'instant) pour passer en passive (pas bien gérer pour l'instant
		this.isPassive = false;
		this.passiveSocket = new ServerSocket(0);
	}

	private String cleanCmd(final String cmd) {
		return cmd.replaceAll("\n|\r", "");
	}

	public void run() {
		try {
			this.sendMessage(Constantes.RESPONSE_220_WELCOME);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		final BufferedReader buffRead = new BufferedReader(new InputStreamReader(this.dataIn));
		while (this.clientIsConnected) {
			try {
				final String message = buffRead.readLine();
				this.processRequest(message);
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

	public void processRequest(final String string) throws IOException {
		if (string == null) {
			return;
		}
		System.out.println("Demande : " + string);
		final String[] req = string.split(" ", 2);
		String mess;
		try{
			final String request = req[0].toUpperCase();
			if (Constantes.CMD_USER.equals(request) && (req.length == 2)) {
				mess = this.processUser(this.cleanCmd(req[1]));
			} else if (Constantes.CMD_PASS.equals(request) && (req.length == 2)) {
				mess = this.processPass(this.cleanCmd(req[1]));
			} else if (Constantes.CMD_RETR.equals(request) && (req.length == 2)) {
				mess = this.processRetr(this.cleanCmd(req[1]));
			} else if (Constantes.CMD_STOR.equals(request) && (req.length == 2)) {
				mess = this.processStor(this.cleanCmd(req[1]));
			} else if (Constantes.CMD_LIST.equals(request)) {
				mess = this.processList();
			} else if (Constantes.CMD_QUIT.equals(request)) {
				mess = this.processQuit();
			} else if (Constantes.CMD_PORT.equals(request) && (req.length == 2)) {
				mess = this.processPort(this.cleanCmd(req[1]));
			} else if (Constantes.CMD_SYST.equals(request)) {
				mess = this.processSyst();
			} else if (Constantes.CMD_TYPE.equals(request) && (req.length == 2)) {
				mess = this.processType(this.cleanCmd(req[1]));
			} else if (Constantes.CMD_FEAT.equals(request)) { // Commande envoyé par
															// Filezilla
				mess = this.processFeat();
			} else if (Constantes.CMD_EPSV.equals(request)) { // Commande envoyé par
															// Filezilla
				mess = this.processEpsv();
			} else if (Constantes.CMD_EPRT.equals(request) && (req.length == 2)) { // Commande envoyé par
															// Filezilla
				mess = this.processEprt(this.cleanCmd(req[1]));
			} else if (Constantes.CMD_PWD.equals(request)) {
				mess = this.processPwd();
			} 
			else if (Constantes.CMD_CWD.equals(request)  && (req.length == 2)) {
				mess = this.processCwd(this.cleanCmd(req[1]));
			}  else if (Constantes.CMD_CDUP.equals(request)) {
				mess = this.processCdup();
			} else {
				mess = "500 unkown command";
			}
		}catch(BadOrderException e){
			mess = Constantes.ERREUR_530;
		}catch(NotDirException e){
			mess = Constantes.ERREUR_400_NotDir;
		}catch(NoSuchFileException e){
			mess = Constantes.ERREUR_404;
		}catch(ConnectException e){
			mess = Constantes.ERREUR_500_dataChan;
		}catch(NotImplementedException e){
			mess = Constantes.ERREUR_500_implent;
		}catch(Exception e){
			e.printStackTrace();
			mess = "500 unkown server erreur";
		}
		this.sendMessage(mess);
	}

	private String processCdup() throws BadOrderException, NotImplementedException {
		this.command.directoryUp();
		return Constantes.RESPONSE_200_CDUP;
	}

	private String processCwd(final String dir) throws BadOrderException, NotImplementedException, NotDirException {
		this.command.changeDirectory(dir);
		return Constantes.RESPONSE_250_CWD;
	}

	private String processEprt(final String fullAdresse) throws NotImplementedException {
		if (!this.command.isAuthenticated()) {
			return Constantes.ERREUR_530;
		}
		final String[] tmp = fullAdresse.split("[|]");// | est le OU de regex, il pose problème dans ce cas spécifique
		this.isPassive = false;
		this.communicationPort = Integer.parseInt(tmp[3]);// * 256 + Integer.parseInt(tmp[5]);
		System.out.println("Le client veut qu'on parle sur " + this.communicationPort);
		return Constantes.RESPONSE_200_EPRT;
	}

	private String processEpsv() {
		return Constantes.RESPONSE_200_EPSV;
	}

	private String processPwd() throws BadOrderException, NotImplementedException {
		return Constantes.RESPONSE_257_PWD + this.command.currentDir();
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
		if (!this.isAuthenticated) {
			return Constantes.ERREUR_530;
		}
		final String[] tmp = fullAdresse.split(",");
		this.communicationPort = (Integer.parseInt(tmp[4]) * 256) + Integer.parseInt(tmp[5]);
		System.out.println("Le client veut qu'on parle sur " + this.communicationPort);
		return Constantes.RESPONSE_200_PORT;
	}

	public String processQuit() throws IOException {
		this.clientIsConnected = false;
		return Constantes.RESPONSE_231_QUIT;
	}

	public String processList() throws BadOrderException, NotImplementedException, ConnectException {
		if (!this.isAuthenticated) {
			return Constantes.ERREUR_530;
		}
		try {
			this.sendMessage(Constantes.RESPONSE_150_LIST);
		} catch (final IOException e2) {
			e2.printStackTrace();
		}

		final String answer = this.command.getFilesList();

		Socket communicationSocket =this.dataFactory.getNewDataChannel(this.clientAddr, this.communicationPort,this.isPassive, this.passiveSocket);
		
		OutputStream os = null;
		DataOutputStream dos = null;

		try {
			os = communicationSocket.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeBytes(answer + Constantes.RESPONSE_226_LIST+ Constantes.END_LINE);
			System.out.println("Server says : " + answer);
			communicationSocket.close();
			communicationSocket = null;
		} catch (final IOException e) {
			e.printStackTrace();
		}
		
		return Constantes.RESPONSE_226_LIST;
	}

	public String processUser(final String user) throws NotImplementedException {
		if (this.command.userExist(user)) {
			return Constantes.RESPONSE_331_USER;// User name okay, need
		}
		return Constantes.RESPONSE_530_USER;
	}

	public String processPass(final String pass) throws BadOrderException, NotImplementedException {
		if (this.command.isAuthenticated()) {
			return Constantes.RESPONSE_230_PASS; // Peut être une bétise (dépend
													// si
			// on accepte les connexions
			// successives ou pas) : Already
			// logged in.
		}

		if (this.command.goodPassword(pass)) {
			this.isAuthenticated = true;
			return Constantes.RESPONSE_257_PASS_BEGIN + this.command.currentDir() + Constantes.RESPONSE_257_PASS_END;
		}
		return Constantes.RESPONSE_530_PASS; // Not logged in
	}

	public String processRetr(final String string) throws BadOrderException, NotImplementedException, ConnectException, NoSuchFileException {
		
		final Path target = this.command.getFilePath(string);
		System.out.println(target.toString());
		
		try {
			this.sendMessage(Constantes.RESPONSE_150_LIST);
		} catch (final IOException e2) {
			e2.printStackTrace();
		}

		System.out.println(this.clientAddr);
		System.out.println(this.communicationPort);
		System.out.println(this.isPassive);
		System.out.println(this.passiveSocket);
		Socket communicationSocket =this.dataFactory.getNewDataChannel(this.clientAddr, this.communicationPort,this.isPassive, this.passiveSocket);
		
		OutputStream os = null;
		DataOutputStream dos = null;
		try {
			os = communicationSocket.getOutputStream();
			Files.copy(target, os);
			dos = new DataOutputStream(os);
			dos.writeBytes( Constantes.RESPONSE_226_RETR+ Constantes.END_LINE);
			communicationSocket.close();
			communicationSocket = null;
		} catch (final NoSuchFileException e) {
			throw new NoSuchFileException(string);
		}catch (final IOException e) {
			e.printStackTrace();
		}

		return Constantes.RESPONSE_226_RETR;
	}

	public String processStor(final String string) throws BadOrderException, NotImplementedException, ConnectException, NoSuchFileException {
		
		final Path target = this.command.getFilePath(string);
		
		try {
			this.sendMessage(Constantes.RESPONSE_150_LIST);
		} catch (final IOException e2) {
			e2.printStackTrace();
		}

		Socket communicationSocket =this.dataFactory.getNewDataChannel(this.clientAddr, this.communicationPort,this.isPassive, this.passiveSocket);
		
		OutputStream os = null;
		DataOutputStream dos = null;
		InputStream is = null;

		try {
			is = communicationSocket.getInputStream();
			Files.copy(is,target, StandardCopyOption.REPLACE_EXISTING);
			os = communicationSocket.getOutputStream();
			dos = new DataOutputStream(os);
			dos.writeBytes( Constantes.RESPONSE_226_STOR+ Constantes.END_LINE);
			communicationSocket.close();
			communicationSocket = null;
		}catch (final IOException e) {
			e.printStackTrace();
		}

		return Constantes.RESPONSE_226_STOR;
	}

}
