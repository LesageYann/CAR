package car.tp2.pages;

/**
 * Classe décrivant la page principal lorsque l'authentification n'a pas encore était faite
 * @author LESAGE Yann PETIT Antoine
 *
 */
public class HomePage {
	final static String head="<html><head></head><body>";
	final static String authLink="<p><a href=" + "http://localhost:8080/rest/tp2/ftp/auth"+ ">Il faut vous authentifier d'abord</a> </p>";
	final static String end = "</body></html>";

	/**
	 * Retourne la page d'accueil redirigeant vers l'authentification
	 * @return la page d'accueil redirigeant vers l'authentification
	 */
	public static String getPage() {
		return head+authLink + end;
	}

}
