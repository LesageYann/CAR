package nativeCMD;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LinuxCMD implements NativeCMD {

    public static final String CHEMIN = "C:\\workspace\\";

    private static BufferedReader getOutput(Process p) {
        return new BufferedReader(new InputStreamReader(p.getInputStream()));
    }

    private static BufferedReader getError(Process p) {
        return new BufferedReader(new InputStreamReader(p.getErrorStream()));
    }

    /* inspirer de http://labs.excilys.com/2012/06/26/runtime-exec-pour-les-nuls-et-processbuilder/
     * https://docs.oracle.com/javase/7/docs/api/java/lang/Runtime.html#exec%28java.lang.String%29
     * et http://ydisanto.developpez.com/tutoriels/java/runtime-exec/ */
    public boolean userExist(String user) {
        System.out.println("Début du programme");
        try {
            String[] commande = {"grep", user,"/etc/passwd"};
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
        return void()
    }
}