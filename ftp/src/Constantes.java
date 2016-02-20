
public class Constantes {
	
	//Commandes possibles
	public static final String CMD_USER = "USER";
	public static final String CMD_PASS = "PASS";
	public static final String CMD_STOR = "STOR";
	public static final String CMD_RETR = "RETR";
	public static final String CMD_SYST = "SYST";
	public static final String CMD_LIST = "LIST";
	public static final String CMD_QUIT = "QUIT";
	public static final String CMD_TYPE = "TYPE";
	public static final String CMD_PORT = "PORT";
	public static final String CMD_FEAT = "FEAT";
	public static final String CMD_PWD = "PWD";
	public static final String CMD_EPSV = "EPSV"; 
	public static final String CMD_EPRT = "EPRT";
	public static final String CMD_CWD = "CWD";
	public static final String CMD_CDUP = "CDUP";
	
	
	//Reponses selon la commande recu
	public static final String RESPONSE_220_WELCOME = "220 Welcome on the FTP Server\n";
	public static final String RESPONSE_331_USER = "331 User name okay, need password";
	public static final String RESPONSE_530_USER = "530 Bad username";
	public static final String RESPONSE_230_PASS = "230 Already logged in";
	public static final String RESPONSE_530_PASS = "530 Not logged in";
	public static final String RESPONSE_257_PASS_BEGIN = "257 \"";
	public static final String RESPONSE_257_PASS_END = "\"";
	public static final String RESPONSE_150_LIST = "150 Opening data channel for directory list.";
	public static final String RESPONSE_231_QUIT = "231 Bye";
	public static final String RESPONSE_211_FEAT = "211\n211 END";
	public static final String RESPONSE_257_PWD = "257 ";
	public static final String RESPONSE_200_TYPE = "200 OK";
	public static final String RESPONSE_200_EPSV = "200 OK";
	public static final String RESPONSE_200_EPRT = "200 OK";
	public static final String RESPONSE_200_PORT = "200 OK";
	public static final String RESPONSE_226_LIST = "226 LIST Done";
	public static final String RESPONSE_200_CDUP = "200 OK";
	public static final String RESPONSE_250_CWD = "250 OK";
	public static final String ERREUR_530 = "530 Error : Not authenticated";
	
	public static final String END_LINE = "\n";

}
