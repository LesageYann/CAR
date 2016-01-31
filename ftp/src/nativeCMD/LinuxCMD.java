package nativeCMD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxCMD implements NativeCMD {

    private String user;
	private String password;
	private Runtime runtime;
    
    public LinuxCMD(Runtime runtime){
    	this.runtime=runtime;
    }
    
    protected static BufferedReader getOutput(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

	protected static BufferedReader getError(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
    }

    /* inspirer de http://labs.excilys.com/2012/06/26/runtime-exec-pour-les-nuls-et-processbuilder/
     * https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html#exec%28java.lang.String%29
     * et http://ydisanto.developpez.com/tutoriels/java/runtime-exec/ */
    public boolean userExist(String user) {
        boolean res = false;
        try {
        	String[] userCMD = new String[3];
    		userCMD[0]="bash";
    		userCMD[1]="-c";
    		userCMD[2]="echo ftp | su -l "+user+" -c \"echo True\"";
            Process p = runtime.exec(userCMD);
            BufferedReader error = getError(p);
            p.waitFor();
            
            String line = error.readLine();
            if(line.equals("Password: su: Authentication failure")){
            	this.user=user;
            	res=true;
            } else{
            	res=false;
            }
            

            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public boolean goodPassword(String password) throws BadOrderException {
        if(user==null){
        	throw new BadOrderException();
        }
        boolean res = false;
        try {
        	String[] commande = {"bash","-c","echo "+password+" | su -l "+user+" -c \"echo True\""};
            Process p = runtime.exec(commande);
            BufferedReader output = getOutput(p);
            p.waitFor();
            
            String line = output.readLine();
            System.out.println(line);
            if(line.equals("True")){
            	this.password=password;
            	res= true;
            } else{
            	res= false;
            }
            

            
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res;
    }
    
    public boolean userExistee(String user) {
        System.out.println("Début du programme");
        try {
        	String[] commande = {"bash","-c","echo ftp | su -l user -c \"echo True\""};
            Process p = Runtime.getRuntime().exec(commande);
            BufferedReader output = getOutput(p);
            BufferedReader error = getError(p);
            String ligne = "";

            while ((ligne = output.readLine()) != null) {
                System.out.println(ligne);
            }
           
          //  while ((ligne = error.readLine()) != null) {
          //      System.out.println(ligne);
           // }

            p.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}