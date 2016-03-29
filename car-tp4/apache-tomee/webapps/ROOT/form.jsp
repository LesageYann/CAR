<html>

<%

String author = "";
String title = "";
String year = "";
if ( request.getParameter("author") != null ) {
author = request.getParameter("author");
title = request.getParameter("title");
year = request.getParameter("year");
    out.print("Auteur : "+author+"<br/>");
    out.print("Titre : "+title+"<br/>");
    out.print("Ann&eacute;e : "+year+"<br/><p/>");
}

%>

<form action="form.jsp">
Auteur : <input type="text" name="author" value="<%=author%>"><br/>
Titre : <input type="text" name="title" value="<%=title%>"><br/>
Ann&eacute;e : <input type="text" name="year" value="<%=year%>"><br/>
<input type="submit">
</form>
</html>
