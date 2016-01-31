package testNativeCMD;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import nativeCMD.LinuxCMD;

@RunWith(MockitoJUnitRunner.class)
public class TestLinuxCMD  extends TestNativeCMD {


	@Before
	public void initializeTest(){ 
		userCMD =new String[3];
		userCMD[0]="bash";
		userCMD[1]="-c";
		userCMD[2]="echo ftp | su -l test -c \"echo True\"";
		userCMDOutSucces="empty";
		userCMDErrSucces="Password: su: Authentication failure";
		userCMDOutFail="empty";
		userCMDErrFail="su: user test does not exist";
		
		passwordCMD =new String[3];
		passwordCMD[0]="bash";
		passwordCMD[1]="-c";
		passwordCMD[2]="echo ftp | su -l test -c \"echo True\"";
		passwordCMDOutSucces="True";
		passwordCMDErrSucces="Password:";
		passwordCMDOutFail="empty";
		passwordCMDErrFail="Password: su: Authentication failure";

		run= Mockito.mock(Runtime.class);
		target= new LinuxCMD(run);
	}

}
