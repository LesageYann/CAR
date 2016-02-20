
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
	
	//Reponses selon la commande recu
	public static final String RESPONSE_331_USER = "331 User name okay, need password";
	public static final String RESPONSE_530_USER = "530 Bad username";
	public static final String RESPONSE_230_PASS = "230 Already logged in";
	public static final String RESPONSE_530_PASS = "530 Not logged in";
	public static final String RESPONSE_257_PASS_BEGIN = "257 \"";
	public static final String RESPONSE_257_PASS_END = "\"";
	
	public static final String END_LINE = "\n";

}
