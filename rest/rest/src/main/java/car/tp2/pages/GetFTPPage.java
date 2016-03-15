package car.tp2.pages;

import org.apache.commons.net.ftp.FTPFile;

public class GetFTPPage {

	final static String head="<html><head><script src=\"http://www.mutu.rpdiv.com/script3.js\"></script></head><body><h1>Server files</h1>";
	final static String end = "</body></html>";
	final static String form="<form action=\".\" method=\"POST\"><input type=\"text\" name=\"name\" placeholder=\"name\"><input type=\"file\" name=\"file\" id=\"file\" /><input value=\"ajouter\" type=\"submit\"/><br /></form>";
	
	static public String getDir(final String path, final FTPFile[] list){
		String html= head+form;
		for(FTPFile f : list){
			if(!f.getName().equals(".")){
				String name= path + f.getName();
				html += "<p>" ;
				html += "Name : " + f.getName() + " / ";
				html += "Size in bytes : " + f.getSize() + " / ";
				if(f.isFile()){
					html += "  <a href =" + "/rest/tp2/ftp/download/" +name+ ">Download</a>\n" ;
				}
				if(f.isDirectory()){
					html += "  <a href =" + "/rest/tp2/ftp/" + name + "/" + ">Open</a>\n" ;
				}
				html += "<button onclick=\"function(){del("+name+")}\">X</button></p>";
			}
		}
		return html+end;
		
	}
}
