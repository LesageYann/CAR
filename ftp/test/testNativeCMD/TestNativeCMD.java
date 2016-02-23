package testNativeCMD;


import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Matchers.*;
import static org.mockito.AdditionalMatchers.*;

import nativeCMD.BadOrderException;
import nativeCMD.NativeCMD;
import nativeCMD.NotImplementedException;



@RunWith(MockitoJUnitRunner.class)
public abstract class TestNativeCMD {

	/**
	 * userCMD : string array to launch the verification of user existance
	 * userCMDOutSucces and userCMDErrSucces : string return by rutime.exec(userCMD) in succes
	 * userCMDOutFail  and userCMDErrFail : string return by rutime.exec(userCMD) in fail
	 * the same case for : passwordCMD , 
	 */
	protected Runtime run;
	protected NativeCMD target;
	protected String[] userCMD;
	protected String userCMDOutSucces;
	protected String userCMDErrSucces;
	protected String userCMDOutFail;
	protected String userCMDErrFail;
	
	protected String[] passwordCMD;
	protected String passwordCMDErrFail;
	protected String passwordCMDOutFail;
	protected String passwordCMDErrSucces;
	protected String passwordCMDOutSucces;
	
	
	
	
	/**
	 * create an object "target" who is an instance of object to test
	 * and an object "runtime" who is an empty mock 
	 * need to be @Before
	 */
	abstract void initializeTest();
	
	private void mockProcess(Process mock, String out, String err){
		InputStream outs= new ByteArrayInputStream(out.getBytes());
		InputStream errs= new ByteArrayInputStream(err.getBytes());
		Mockito.when(mock.getInputStream()).thenReturn(outs);
		Mockito.when(mock.getErrorStream()).thenReturn(errs);
	}
	
	@Test
	public void testUserExistSuccess() throws IOException, NotImplementedException {
		Process p=Mockito.mock(Process.class);
		mockProcess(p,userCMDOutSucces,userCMDErrSucces);
		Mockito.when(run.exec(aryEq(userCMD))).thenReturn(p);
		assertTrue(target.userExist("test"));
	}

	@Test
	public void testUserExistFail() throws IOException, NotImplementedException {
		Process p=Mockito.mock(Process.class);
		mockProcess(p,userCMDOutFail, userCMDErrFail);
		Mockito.when(run.exec(aryEq(userCMD))).thenReturn(p);
		assertFalse(target.userExist("test"));
	}
	
	
	@Test
	public void testUserPasswordSucces() throws IOException, BadOrderException, NotImplementedException {
		Process p=Mockito.mock(Process.class);
		mockProcess(p,userCMDOutSucces,userCMDErrSucces);
		Mockito.when(run.exec(aryEq(userCMD))).thenReturn(p);
		System.out.println(target.userExist("test"));
		mockProcess(p,passwordCMDOutSucces, passwordCMDErrSucces);
		Mockito.when(run.exec(aryEq(passwordCMD))).thenReturn(p);
		assertTrue(target.goodPassword("ftp")); 
	}
	
	@Test
	public void testUserPasswordFail() throws IOException, BadOrderException, NotImplementedException {
		Process p=Mockito.mock(Process.class);
		mockProcess(p,userCMDOutSucces,userCMDErrSucces);
		Mockito.when(run.exec(aryEq(userCMD))).thenReturn(p);
		System.out.println(target.userExist("test"));
		mockProcess(p,passwordCMDOutFail, passwordCMDErrFail);
		Mockito.when(run.exec(aryEq(passwordCMD))).thenReturn(p);
		assertFalse(target.goodPassword("ftp"));
	}
	
	
	@Test(expected=BadOrderException.class) 
	public void testUserPasswordUserNotSetFail() throws IOException,BadOrderException, NotImplementedException {
		Process p=Mockito.mock(Process.class);
		mockProcess(p,passwordCMDOutSucces, passwordCMDErrSucces);
		Mockito.when(run.exec(aryEq(passwordCMD))).thenReturn(p);
		target.goodPassword("ftp");
	}
}
