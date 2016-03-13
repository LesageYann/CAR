package car.tp2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.net.ftp.FTPClient;

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
		System.out.println("pseudo : "+ user);
		System.out.println("password : "+ password);
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
	public String getConnexionForm( @PathParam("var") String path ) {
		return "<form method=\"POST\" action=\"auth\"><input name=\"login\" placeholder=\"login\" /><br /><input type=\"password\" name=\"psw\" placeholder=\"password\" /><br /><input type=\"submit\" value=\"Submit\"><br /></form>";
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
			return "erreur de login/password<form method=\"POST\" action=\"auth\"><input name=\"login\" placeholder=\"login\" /><br /><input type=\"password\" name=\"psw\" placeholder=\"password\" /><br /><input type=\"submit\" value=\"Submit\"><br /></form>";
		}
		return "vous êtes maintenant connectez à [serveur]";
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
