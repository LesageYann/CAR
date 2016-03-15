package car.tp2.pages;

/**
 * Classe décrivant la page d'authentification et ses résultantes
 * @author LESAGE Yann PETIT Antoine
 *
 */
public class AuthPage {
	final static String head="<html><head></head><body>";
	final static String form="<form method=\"POST\" action=\"auth\"><input name=\"login\" placeholder=\"login\" /><br /><input type=\"password\" name=\"psw\" placeholder=\"password\" /><br /><input type=\"submit\" value=\"Submit\"><br /></form>";
	final static String errPsw ="erreur de login/password";
	final static String err ="erreur de connexion au serveur ftp";
	final static String succes = "<p>Vous &ecirc;tes connect&eacute;</p><a href=\".\"> aller sur la racine ftp</a>";
	final static String end = "</body></html>";
	
	/**
	 * Retour la page contenant le formulaire
	 * 
	 */
	static public String getPage(){
		return head + form+end;
	}
	
	/**
	 * Retour la page contenant le formulaire et indiquant que le mot de passe est incorrect
	 * 
	 */
	static public String badPwdPage(){
		return head + errPsw + form + end;
	}
	
	/**
	 * Retour la page contenant le formulaire et indiquant une erreur
	 * 
	 */
	static public String postErrPage(){
		return head + err + form + end;
	}
	
	/**
	 * Retour la page indiquant la réussite de la connexion
	 * 
	 */
	static public String postSuccesPage(){
		return head + succes + end;
	}
}
