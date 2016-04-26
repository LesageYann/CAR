package car.tp4;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet permettant l'ajout au panier d'un livre
 * 
 * @author Antoine PETIT et Yann LESAGE
 *
 */
@WebServlet(urlPatterns = "/addPanier")
public class AddPanierServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Service gérant l'ajout du livre au panier puis la redirection vers la page bibliotheque
	 */
	public void service(final HttpServletRequest request, final HttpServletResponse response) {
		Panier panier = (Panier) request.getSession().getAttribute("panier");
		int id =   Integer.valueOf((String) request.getParameter("id"));
		panier.addBook(id);
		// Redirection
		RequestDispatcher dispatcher = request.getRequestDispatcher("bibliotheque.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (final ServletException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
