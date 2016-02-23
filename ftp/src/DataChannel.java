import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class DataChannel {

	
	public Socket getNewDataChannel(final InetAddress clientAddr, final int communicationPort,final Boolean passive, final ServerSocket passiveSocket)throws ConnectException{
		Socket communicationSocket = null;
		try {
			if(passive){
				System.out.println("this is passive");
				communicationSocket= passiveSocket.accept();
			}else {
				System.out.println("this is not passive");
				communicationSocket = new Socket();
				communicationSocket.connect(new InetSocketAddress(clientAddr, communicationPort));
			}
			

		} catch (final ConnectException e) {
			throw new ConnectException() ;
		}catch (final IOException e) {
			e.printStackTrace();
		}

		return communicationSocket;
	}

}
