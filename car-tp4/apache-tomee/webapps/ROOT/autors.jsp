<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="car.tp4.*" %>

<html>


<%
	BookLibItf bookLib = (BookLib) session.getAttribute("BOOKLIB");;
	if (bookLib == null) {  

%>

	Le contenu n'est pas initialisé, veuillez l'initialiser : <a href='/initialize.jsp'>Cliquez ici</a>
	
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
<a href='/form.jsp'>Form</a><br/>

</html>
