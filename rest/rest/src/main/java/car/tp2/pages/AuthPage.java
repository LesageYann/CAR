package car.tp2.pages;

public class AuthPage {
	final static String head="<html><head></head><body>";
	final static String form="<form method=\"POST\" action=\"auth\"><input name=\"login\" placeholder=\"login\" /><br /><input type=\"password\" name=\"psw\" placeholder=\"password\" /><br /><input type=\"submit\" value=\"Submit\"><br /></form>";
	final static String err ="erreur de login/password";
	final static String succes = "<p>Vous êtes connecter</p><a href=\".\"> aller sur la racine ftp</a>";
	final static String end = "</body></html>";
	
	
	static public String getPage(){
		return form;
	}
	
	static public String postErrPage(){
		return head + err + form + end;
	}
	
	static public String postSuccesPage(){
		return head + succes + end;
	}
}
