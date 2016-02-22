package nativeCMD;

import java.nio.file.Path;

public interface  NativeCMD {

	boolean userExist(String user);
	
	/** ask system to authenticate the user
	 * @param password
	 * @return true if the password is good or return false in other cases.
	 * @throws BadOrderException if no name  have been set before with userExist
	 */
	boolean goodPassword(String password) throws BadOrderException;
	
	/** get the complete path for a file with only the basename of file
	 * @param name the file's basename  
	 * @return the file's path 
	 * @throws BadOrderException if no name  have been set before with userExist
	 */
	Path getFilePath(String name) throws BadOrderException;
	
	/** Move to parent directory
	 * @throws BadOrderException if no name  have been set before with userExist
	 */
	void directoryUp() throws BadOrderException;
	
	/** Move to directory gived
	 * @param dir the to go to the next dir
	 * @throws BadOrderException if no name  have been set before with userExist
	 */
	void changeDirectory(String dir) throws BadOrderException;

	/**
	 * 
	 * @return the current directory as a string.
	 * @throws BadOrderException 
	 */
	String currentDir() throws BadOrderException;

	/**
	 * 
	 * @return return the files and directory list of the current dir
	 */
	String getFilesList();

	boolean isAuthenticated();
}