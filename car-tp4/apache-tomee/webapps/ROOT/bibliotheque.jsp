<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="car.tp4.*" %>

<html>

<h1>Biblioth&egrave;que</h1>

<%
	List<String> errors = (List<String>) request.getAttribute("error");
	if (errors != null) {
		for (String error : errors) {
			out.print(error + "<br/>");
		}
	}
%>

<%
	BookLibItf bookLib = (BookLibItf) session.getAttribute("library");
	Panier panier = (Panier)session.getAttribute("panier");
	if (bookLib == null) {  

%>

	Le contenu n'est pas initialis&eacute;, veuillez l'initialiser : <a href='/init'>Cliquez ici</a>
	
<%
	}
	else {
%>
	<p id="nbLivrePanier"> Vous avez actuellement <%=panier.getNbArticles()%> livres dans votre <a href='/panier.jsp'>panier</a></p>
	<table>
		<tr>
			<td>Auteur</td>
			<td>Titre</td>
			<td>Ann&eacute;e</td>
			<td></td>
		</tr>
<%		for (BookDAO book : bookLib.getBooks()) {
			out.print("<tr>");
			out.print("<td>"+book.getAuthor()+"</td>");
			out.print("<td>"+book.getTitle()+"</td>");
			out.print("<td>"+book.getYear()+"</td>");
			out.print("<td><a href='/addPanier?id="+book.id +"'>+</a></td>");
			out.print("</tr>");
		}
%>
</table>
<%
	}
%>

<br/>
<a href='/form_ajout.jsp'>Form</a><br/>

</html>
