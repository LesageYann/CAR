
public class FtpRequest {
	
	private static final String fichierUserPwd = "./userPwd.txt"; // fichier hyper sécurisé

	public void processRequest(String string) {
		String[] req=string.split(" ",2);
		if("USER".equals(req[0])){
			this.processUser(req[1]); 
		}
		else if("PASS".equals(req[0])) {
			this.processPass(req[1]);
		}else if("RETR".equals(req[0])) {
			this.processRetr(req[1]);
		}else if("STOR".equals(req[0])) {
			this.processStor(req[1]);
		}else if("LIST".equals(req[0])) {
			this.processList();
		}else if("QUIT".equals(req[0])) {
			this.processQuit();
		}
	}
	
	public void processQuit() {
		// TODO Auto-generated method stub
		
	}

	public void processList() {
		// TODO Auto-generated method stub
		
	}

	public int processUser(String user) {
		String result= "" ;
		
		return 200;
	}

	public void processPass(String string) {
		// TODO Auto-generated method stub
		
	}

	public void processRetr(String string) {
		// TODO Auto-generated method stub
		
	}

	public void processStor(String string) {
		// TODO Auto-generated method stub
		
	}


}
