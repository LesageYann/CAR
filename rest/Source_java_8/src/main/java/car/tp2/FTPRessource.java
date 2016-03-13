package car.tp2;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

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
			String html = "<h1>Server files</h1>\n" ;
			if (!("").equals(path)) {
				path += "/";
			}
			for(FTPFile f : ftp.listFiles(path)){
				if(!f.getName().equals(".")){
					html += "<p>" ;
					html += "Name : " + f.getName() + " / ";
					html += "Size in bytes : " + f.getSize() + " / ";
					if(f.isFile()){
						html += "  <a href =" + "http://localhost:8080/rest/tp2/ftp/download/" + path  + f.getName()+ ">Download</a>\n" ;
					}
					if(f.isDirectory()){
						html += "  <a href =" + "http://localhost:8080/rest/tp2/ftp/" + path + f.getName()+ ">Open</a>\n" ;
					}
					html += "</p>";
				}
			}
			return html;
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

	@GET
	@Produces("application/octet-stream")
	@Path("download/{var: .*}/{filename}")
	public Response getFile(@PathParam("var") String pathname, @PathParam("filename") String filename) {
		InputStream in;
		try {
			in = this.ftp.retrieveFileStream(pathname + "/" + filename);
			Response response = Response.ok(in).build();
			ftp.completePendingCommand();
			return response;
		} catch (IOException e) {
			System.out.print("Erreur lors du téléchargement du fichier :" + filename);
		}
       return null;
	}
	

}
