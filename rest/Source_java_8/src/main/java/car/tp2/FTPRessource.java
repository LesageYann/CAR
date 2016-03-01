package car.tp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.net.ftp.FTPClient;

@Path("/ftp")
public class FTPRessource {
	
	private FTPClient ftp;

	public FTPRessource(){
		ftp = new FTPClient();
	}

	@GET
	@Path("var: .*")
	@Produces("application/octet-stream")
	public String getStuff( @PathParam("var") String path ) {
		InputStream is;
		try {
			is = ftp.retrieveFileStream(path);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			return br.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString(); 
		}
	 }
}
