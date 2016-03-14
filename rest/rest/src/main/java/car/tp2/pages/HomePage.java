package car.tp2.pages;

import javax.ws.rs.core.Response;

public class HomePage {
	final static String head="<html><head></head><body>";
	final static String authLink="<p><a href=" + "http://localhost:8080/rest/tp2/ftp/auth"+ ">Il faut vous authentifier d'abord</a> </p>";
	final static String end = "</body></html>";

	public static String getPage() {
		return head+authLink + end;
	}

}
