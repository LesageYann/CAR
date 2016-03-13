package car.tp2;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import car.tp2.pages.AuthPage;
import car.tp2.pages.GetFTPPage;

@Path("/ftp")
public class FTPRessource {
	
	private FTPClient ftp;
	//use to reconnect after timeout connexion
	private String user;
	private String password;

	public FTPRessource(){
		ftp = new FTPClient();
	}
	
	private void connexion(String user, String password) throws SocketException, IOException{
		ftp.connect("ftp.rpdiv.com", 21);
		System.out.println("connect");
		System.out.println(ftp.getReplyString());
		System.out.println(ftp.user(user));
		System.out.println("no erreur");
		System.out.println(ftp.getReplyString());
		System.out.println(ftp.pass(password));
		System.out.println(ftp.getReplyString());
	}

	@GET
	@Path("/auth")
	@Produces("text/html")
	public String getConnexionForm( ) {
		return AuthPage.getPage();
	 }
	
	@POST
	@Path("/auth")
	@Produces("text/html")
	public String postConnexionForm( @FormParam("login") String login, @FormParam("psw") String psw) {
		this.user=login;
		this.password=psw;
		try {
			this.connexion(login,psw);
		} catch (IOException e) {
			e.printStackTrace();
			return AuthPage.postErrPage();
		}
		return AuthPage.postSuccesPage();
	 }
	
	@GET
	@Path("{var: .*}")
	@Produces("text/html")
	public String getStuff( @PathParam("var") String path ) {
		InputStream is;
		if (!ftp.isConnected()){
			try {
				connexion(user,password);
			}
			catch (IOException e) { 
				e.printStackTrace();
				return e.toString();
			}
		}
		try {
			if (!("").equals(path)) {
				path += "";
			}
			return GetFTPPage.getDir(path, ftp.listFiles(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return e.toString(); 
		}
	 }
	
	@GET
	@Produces("application/octet-stream")
	@Path("download/{filename}")
	public Response getFile(@PathParam("filename") String filename) {
		InputStream in;
		try {
			in = this.ftp.retrieveFileStream(filename);
			Response response = Response.ok(in).build();
			ftp.completePendingCommand();
			return response;
		} catch (IOException e) {
			System.out.print("Erreur lors du téléchargement du fichier :" + filename);
		}
       return null;
	}

	
	@PUT
	@Produces("application/octet-stream")
	@Path("{var: .*}")
	public Response putFile(@PathParam("var") String pathname, @FormParam("file") InputStream file) {
		if (!ftp.isConnected()){
			try {
				connexion(user,password);
			}
			catch (IOException e) { 
				e.printStackTrace();
				return Response.serverError().build();
			}
		}
		try {
			if (this.ftp.storeUniqueFile(pathname, file)){
				return Response.ok().build();
			}
			return Response.serverError().build();
		} catch (IOException e) {
			System.out.print("Erreur lors du téléchargement du fichier :" + pathname);
		}
       return null;
	}
	
	@DELETE
	@Produces("application/octet-stream")
	@Path("{var: .*}")
	public Response deleteFile(@PathParam("var") String pathname) {
		if (!ftp.isConnected()){
			try {
				connexion(user,password);
			}
			catch (IOException e) { 
				e.printStackTrace();
				return Response.serverError().build();
			}
		}
		try {
			if (this.ftp.deleteFile(pathname)){
				return Response.ok().build();
			}
			return Response.serverError().build();
		} catch (IOException e) {
			System.out.print("Erreur lors du téléchargement du fichier :" + pathname);
		}
       return null;
	}
	

}
