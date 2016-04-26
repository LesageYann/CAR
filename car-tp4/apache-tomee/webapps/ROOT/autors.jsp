<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="car.tp4.*" %>

<html>


<%
	List<String> errors = (List<String>) request.getAttribute("error");
	if (errors != null) {
		for (String error : errors) {
			out.print(error + "<br/>");
		}
	}
%>

<%
	BookLibItf bookLib = (BookLibItf) session.getAttribute("library");;
	if (bookLib == null) {  

%>

	Le contenu n'est pas initialis&eacute;, veuillez l'initialiser : <a href='/init'>Cliquez ici</a>
	
<%
	}
	else {
		out.print("Les auteurs suivants sont présents :<br/><br/><ul>");
		for (String author : bookLib.getAutors()) {
			out.print("<li>Auteur : "+author+"</li>");
		}
		out.print("</ul>");
	}
%>
<br/>
<a href='/form_ajout.jsp'>Form</a><br/>
<a href='/bibliotheque.jsp'>Bibliotheque</a><br/>

</html>
