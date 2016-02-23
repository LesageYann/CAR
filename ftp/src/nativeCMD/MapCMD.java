package nativeCMD;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import Constantes.Constantes;

public class MapCMD implements NativeCMD {
	/** The username of the current user **/
	private String user;
	
	/** Give if the user is authenticated**/
	private boolean isAuthenticated;
	/**  for the connection **/
	private Map<String,String> mapUserPwd;

	private String defaultDir;

	private String currentDir;

	public MapCMD(String folder){
		this.user = "";
		this.isAuthenticated = false;
		this.mapUserPwdInitialisation();
		this.defaultDir = folder;
		this.currentDir = folder;
	}
	
	private void mapUserPwdInitialisation() {
		this.mapUserPwd = new HashMap<String, String>();
		this.mapUserPwd.put("test", "test");
		this.mapUserPwd.put("petita", "mdppetita");
		this.mapUserPwd.put("lesagey", "mdpleasagey");
	}
	
	public boolean userExist(String user){
		if (this.mapUserPwd.containsKey(user)) {
			this.user = user;
			return true;// User name okay, need
		}
		return false;
	}
	
	public boolean goodPassword(String password) throws BadOrderException{
		if ((this.user == null) || ("".equals(this.user.trim()))) {
			throw new BadOrderException();
		}
		if (this.mapUserPwd.get(this.user).equals(password)) {
			this.isAuthenticated = true;
			return true;
		}
		return false;
	}

	public Path getFilePath(String name) throws BadOrderException {
		if (!this.isAuthenticated) {
			throw new BadOrderException();
		}
		if(!name.startsWith("/")){
			name= this.currentDir+"/"+name;
		}
		System.out.println(name);
		return Paths.get(name);
	}

	
	public void directoryUp() throws BadOrderException {
		if (!this.isAuthenticated) {
			throw new BadOrderException();
		}
		this.currentDir=new File(this.currentDir).getParent();
	}

	public void changeDirectory(String dir) throws BadOrderException, NotDirException {
		if (!this.isAuthenticated) {
			throw new BadOrderException();
		}
		System.out.println("avant startwith "+dir);
		System.out.println(dir.startsWith("/"));
		if(!dir.startsWith("/")){
			dir= this.currentDir+"/"+dir;
		}
		System.out.println("apres startwith "+dir);
		System.out.println(dir);
		System.out.println(dir);
		final File directory = new File(dir);
		System.out.println(directory.isDirectory());
		if(!directory.isDirectory()){
			throw new NotDirException();
		}
		this.currentDir =dir;
	}

	public String currentDir() throws BadOrderException {
		if (!this.isAuthenticated) {
			throw new BadOrderException();
		}
		return this.currentDir;
	}

	public String getFilesList() {
		String answer = "";
		final File dir = new File(this.currentDir);
		final File filesList[] = dir.listFiles();
		String currentFile = "";
			
		// pour le format de list :http://stackoverflow.com/questions/2443007/ftp-list-format

		System.out.println(filesList);
		for (final File file : filesList) {
			if (!file.isHidden()) {
				int type=6;
				String permstr="drw-rw-rw-"; 

				if (file.isFile()) {
					type=1;
					permstr= "-rw-rw-rw-";
				}
				final Date date=new Date(file.lastModified());
			    final SimpleDateFormat df2 = new SimpleDateFormat("yyyy MMM dd");
			    final String dateText = df2.format(date);
				
				String username;
				try {
					username = java.nio.file.Files.getOwner(file.toPath()).toString();
				} catch (final IOException e) {
					// TODO Auto-generated catch block
					username="unknowUser";
				}
				currentFile = String.format( "%s %d %-10s  %10d %s %s\r\n",
					    permstr, type,username, 
					    file.length(), dateText,
					    file.getName());
					
				answer += currentFile + Constantes.END_LINE;
			}
		}
		return answer;
	}

	@Override
	public boolean isAuthenticated() {
		return this.isAuthenticated;
	}

}
