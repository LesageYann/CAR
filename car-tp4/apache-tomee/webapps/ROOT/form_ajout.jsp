<html>

<%

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
Auteur : <input type="text" name="author" value="<%=author%>"><br/>
Titre : <input type="text" name="title" value="<%=title%>"><br/>
Ann&eacute;e : <input type="text" name="year" value="<%=year%>"><br/>
<input type="submit">
</form>
</html>
