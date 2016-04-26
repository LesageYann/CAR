package car.tp4;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet permettant l'ajout d'un nouveau livre en base
 * 
 * @author Antoine PETIT et Yann LESAGE
 *
 */
@WebServlet(urlPatterns = "/ajoutLivre")
public class AjoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Service gérant l'ajout en base du livre et la redirection vers le formulaire
	 */
	public void service(final HttpServletRequest request,final HttpServletResponse response){
		final String author = request.getParameter("author");
		final String title = request.getParameter("title");
		final Integer year =  Integer.valueOf(request.getParameter("year"));
		final BookLibItf lib = (BookLibItf) request.getSession().getAttribute("library");
		
		System.out.println(lib.toString());
		lib.addBook(new BookDAO(author, title, year.intValue()));
		
		//Redirection
		RequestDispatcher dispatcher = request.getRequestDispatcher("form_ajout.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (final ServletException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
	
}
