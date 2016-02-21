import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur extends Thread {

	private ServerSocket serveurSocket;
	private Socket socket = new Socket();
	private String initialDir;

	/** Initialization of Serveur with a specific port **/
	public void initialization(int port,String dir) {
		this.initialDir=dir;
		try {
			this.serveurSocket = new ServerSocket(port);
			System.out.println("Initialization OK on: " + port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Wait for a new connection from a Client
	 * A new thread is created when a client is connected
	 */
	@Override
	public void run() {
		while (true) {
			System.out.println("Waiting client ...");
			try {
				this.socket = this.serveurSocket.accept();
				new Thread(new FtpRequest(this.socket,this.initialDir)).start();//essai de mettre un meilleur chemin
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	public static final void main(String[] args){
		Serveur s = new Serveur();
		s.initialization(1666, args[0]);
		s.run();
	}

	public ServerSocket getServeurSocket() {
		return serveurSocket;
	}

	public void setServeurSocket(final ServerSocket serveurSocket) {
		this.serveurSocket = serveurSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(final Socket socket) {
		this.socket = socket;
	}

}
