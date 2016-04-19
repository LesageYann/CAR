<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="car.tp4.*" %>

<html>


<%
	BookLibItf bookLib = new BookLib();
	List<BookDAO> list = new ArrayList<BookDAO>();
	list.add(new BookDAO("George R.R. Martin","Le livre où beaucoup de gens meurs",1985));
	list.add(new BookDAO("Tolkien","Le truc avec les anneaux", 1947));
	list.add(new BookDAO("Andrzej Sapkowski", "Le messieur aux trois glaives", 1990));
	list.add(new BookDAO("isaac asimov","dans celui ci, il n'y aura enfin pas de revolte de robot", 1980));
	bookLib.initialize(list);
	session.setAttribute("BOOKLIB",bookLib);

%>

L'initialisation de la librairie s'est bien passée.
<br/>
<a href='/form.jsp'>Form</a><br/>
<a href='/autors.jsp'>Librairie </a>
</html>
