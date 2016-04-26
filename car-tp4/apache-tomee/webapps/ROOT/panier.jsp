<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Set"%>

<%@ page import="car.tp4.*" %>

<html>

<h1>Panier</h1>

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
	<p id="nbLivrePanier"> Vous avez actuellement <%=panier.getNbArticles()%> livres dans votre panier</p>
	<table>
		<tr>
			<td>Auteur</td>
			<td>Titre</td>
			<td>Ann&eacute;e</td>
			<td>Quantit&eacute;</td>
			<td></td>
		</tr>
<%		
		Set keys = panier.getContent().keySet();
		Iterator it = keys.iterator();
		while (it.hasNext()) {
			Integer intId = (Integer)it.next();
			int id = Integer.valueOf(intId);
			int quantite = Integer.valueOf((Integer)panier.getContent().get(intId));
			BookDAO book = bookLib.getBook(id);
			if (book != null) {
			
				out.print("<tr>");
				out.print("<td>"+book.getAuthor()+"</td>");
				out.print("<td>"+book.getTitle()+"</td>");
				out.print("<td>"+book.getYear()+"</td>");
				out.print("<td>"+quantite+"</td>");
				out.print("<td><a href='/removePanier?id="+book.id +"'>-</a></td>");
				out.print("</tr>");
			}
		}
%>
</table>
<%
	}
%>

<br/>
<a href='/form_ajout.jsp'>Form</a><br/>
<a href='/bibliotheque.jsp'>La bibliotheque</a><br/>

</html>
