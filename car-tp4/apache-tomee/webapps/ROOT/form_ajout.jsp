<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="car.tp4.*" %>
<html>

<h1>Formulaire d'ajout de livre</h1>
<%
	BookLibItf bookLib = (BookLibItf) session.getAttribute("library");
	if (bookLib == null) {  

%>

	Le contenu n'est pas initialis&eacute;, veuillez l'initialiser : <a href='/init'>Cliquez ici</a><br/>
	
<%
	}else {

String author = "";
String title = "";
String year = "";
if ( request.getParameter("author") != null ) {
out.print("Le livre suivant a &eacute;t&eacute; ajout&eacute;:<br/>");
author = request.getParameter("author");
title = request.getParameter("title");
year = request.getParameter("year");
    out.print("Auteur : "+author+"<br/>");
    out.print("Titre : "+title+"<br/>");
    out.print("Ann&eacute;e : "+year+"<br/><br/>");
    out.print("Vous pouvez ajouter un autre livre en compl&eacute;tant a nouveau le formulaire.<br/><p/>");
}

%>

<form action="/ajoutLivre">
Auteur : <input type="text" name="author" required value="<%=author%>"><br/>
Titre : <input type="text" name="title" required value="<%=title%>"><br/>
Ann&eacute;e : <input type="number" name="year" required value="<%=year%>"><br/>
<input type="submit">
</form>
<%
}
%>
<a href='/autors.jsp'>Les auteurs présents</a><br/>
<a href='/bibliotheque.jsp'>La bibliotheque</a><br/>
</html>
