import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur extends Thread {

	private ServerSocket serveurSocket;
	private Socket socket = new Socket();

	/** Initialization of Serveur with a specific port **/
	public void initialization(int port) {
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
				new Thread(new FtpRequest(this.socket,".")).start();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public ServerSocket getServeurSocket() {
		return serveurSocket;
	}

	public void setServeurSocket(ServerSocket serveurSocket) {
		this.serveurSocket = serveurSocket;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

}
