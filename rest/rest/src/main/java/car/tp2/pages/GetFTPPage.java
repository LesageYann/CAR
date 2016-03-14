package car.tp2.pages;

import org.apache.commons.net.ftp.FTPFile;

public class GetFTPPage {

	final static String head="<html><head><script src=\"https://code.jquery.com/jquery-2.2.1.min.js\"></script></head><body><h1>Server files</h1>";
	final static String end = "<script>function del(path){$.ajax({url: path, type: 'DELETE', success: function() { alert('DELETE completed'); }});}</script></body></html>";
	final static String form="<form><input type=\"file\" name=\"file\" id=\"file\" /><button onclick=\"$.ajax({"
     +"url: 'http://example.com/', type: 'PUT', data: 'file='$('#file').files[0], success: function() { alert('PUT completed'); }"
     +"});\">ajouter</button><br /></form>";
	
	static public String getDir(final String path, final FTPFile[] list){
		String html= head;
		for(FTPFile f : list){
			if(!f.getName().equals(".")){
				String name= path + f.getName();
				html += "<p>" ;
				html += "Name : " + f.getName() + " / ";
				html += "Size in bytes : " + f.getSize() + " / ";
				if(f.isFile()){
					html += "  <a href =" + "http://localhost:8080/rest/tp2/ftp/download/" +name+ ">Download</a>\n" ;
				}
				if(f.isDirectory()){
					html += "  <a href =" + "http://localhost:8080/rest/tp2/ftp/" + name+ ">Open</a>\n" ;
				}
				html += "<button onclick=\"del("+name+")\">X</button></p>";
			}
		}
		return html+end;
		
	}
}
