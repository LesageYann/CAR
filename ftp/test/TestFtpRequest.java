import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestFtpRequest extends TestCase {
	
		Socket sock;
		FtpRequest ftp;
		
	@Before
	public void initialize() throws IOException {
		sock = Mockito.mock(Socket.class);
		InputStream inpStream = new ByteArrayInputStream("".getBytes());
		OutputStream outStream = new ByteArrayOutputStream();
		Mockito.when(sock.getInputStream()).thenReturn(inpStream);
		Mockito.when(sock.getOutputStream()).thenReturn(outStream);
		ftp = Mockito.spy(new FtpRequest(sock,""));
	}
	
	@Test
	public void testProcessRequestUser() throws IOException {
		ftp.processRequest("USER name"); 
		Mockito.verify(ftp).processUser("name");
	}
	
	@Test
	public void testProcessRequestPass() throws IOException {
		ftp.processRequest("PASS pwd"); 
		Mockito.verify(ftp).processPass("pwd");
	}
	
	@Test
	public void testProcessRequestRetr() throws IOException {
		ftp.processRequest("RETR name"); 
		Mockito.verify(ftp).processRetr("name");
	}
	
	@Test
	public void testProcessRequestStor() throws IOException {
		ftp.processRequest("STOR name"); 
		Mockito.verify(ftp).processStor("name");
	}
	
	@Test
	public void testProcessRequestList() throws IOException {
		ftp.processRequest("LIST"); 
		Mockito.verify(ftp).processList();
	}
	
	@Test
	public void testProcessRequestQuit() throws IOException {
		ftp.processRequest("QUIT"); 
		Mockito.verify(ftp).processQuit();
	}
	
	@Test
	public void testProcessRequestUnkownCMDError() throws IOException {
		ftp.processRequest("Hibou"); 
		Mockito.verify(ftp).sendMessage("500 unkown command");
		// erreur 500
		;
	}
	
	@Test
	public void testProcessUserRenvoie331(){
		final String rep = ftp.processUser("petita");
		assertTrue(rep.startsWith("331"));		
	}
	
	@Test
	public void testProcessPassRenvoie530SiUtilisateurNonInitialise(){
		final String rep = ftp.processPass("truc");
		assertTrue(rep.startsWith("530"));		
	}
	
	@Test
	public void testProcessPassRenvoie257SiUtilisateurBienConnecte(){
		ftp.processUser("test");
		final String rep = ftp.processPass("test");
		assertTrue(rep.startsWith("257")); // TODO A ameliorer
	}
	
	@Test
	public void testProcessPassRenvoie230SiUtilisateurDejaConnecte(){
		ftp.processUser("test");
		ftp.processPass("test");
		final String rep = ftp.processPass("test"); 
		assertTrue(rep.startsWith("230")); // TODO A ameliorer
	}
	
}
