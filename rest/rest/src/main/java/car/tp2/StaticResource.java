package car.tp2;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Exemple de ressource REST accessible a l'adresse :
 * 
 * 		http://localhost:8080/rest/tp2/helloworld
 * 
 * @author Lionel Seinturier <Lionel.Seinturier@univ-lille1.fr>
 */
@Path("/helloworld")
public class StaticResource {

	@GET
	@Path("{var: .*}")
	@Produces("text/html")
	public String getStuff( @PathParam("var") String path ) {
		return "<h1>Hello World</h1>";
	}
}

