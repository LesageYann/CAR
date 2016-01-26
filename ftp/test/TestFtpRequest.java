import java.net.ServerSocket;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestFtpRequest extends TestCase {
	
	@Test
	public void testProcessRequestUser(){
		FtpRequest ftp = Mockito.spy(new FtpRequest());
		ftp.processRequest("USER name"); 
		Mockito.verify(ftp).processUser("name");
	}
	
	@Test
	public void testProcessRequestPass(){
		FtpRequest ftp = Mockito.spy(new FtpRequest());
		ftp.processRequest("PASS pwd"); 
		Mockito.verify(ftp).processPass("pwd");
	}
	
	@Test
	public void testProcessRequestRetr(){
		FtpRequest ftp = Mockito.spy(new FtpRequest());
		ftp.processRequest("RETR name"); 
		Mockito.verify(ftp).processRetr("name");
	}
	
	@Test
	public void testProcessRequestStor(){
		FtpRequest ftp = Mockito.spy(new FtpRequest());
		ftp.processRequest("STOR name"); 
		Mockito.verify(ftp).processStor("name");
	}
	
	@Test
	public void testProcessRequestList(){
		FtpRequest ftp = Mockito.spy(new FtpRequest());
		ftp.processRequest("LIST"); 
		Mockito.verify(ftp).processList();
	}
	
	@Test
	public void testProcessRequestQuit(){
		FtpRequest ftp = Mockito.spy(new FtpRequest());
		ftp.processRequest("QUIT"); 
		Mockito.verify(ftp).processQuit();
	}
	
	@Test
	public void testProcessRequestUnkownCMDError(){
		ServerSocket socket = Mockito.mock(ServerSocket.class);
		FtpRequest ftp = new FtpRequest(socket);
		ftp.processRequest("Hibou"); 
		
		// erreur 500
		;
	}
	
	
//	@Test
//	public void testProcessUserExist(){
//		String name = "testuser"; 
//		FtpRequest ftp= new FtpRequest();
//		String pwd = ftp.processUser(name);
//		assertEquals("testpwd",pwd);
//	}
}
