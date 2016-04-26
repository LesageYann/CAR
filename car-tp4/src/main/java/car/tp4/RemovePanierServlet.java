package car.tp4;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/removePanier")
public class RemovePanierServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void service(final HttpServletRequest request, final HttpServletResponse response) {
		Panier panier = (Panier) request.getSession().getAttribute("panier");
		int id =   Integer.valueOf((String) request.getParameter("id"));
		panier.removeBook(id);
		// Redirection
		RequestDispatcher dispatcher = request.getRequestDispatcher("panier.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (final ServletException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
