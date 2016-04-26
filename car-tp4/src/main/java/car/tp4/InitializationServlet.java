package car.tp4;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/init")
public class InitializationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void service(final HttpServletRequest request, final HttpServletResponse response) {
		BookLibItf myBookLib = new BookLib();
		Panier panier = new Panier();
		List<String> error = new ArrayList<String>();

		try {
			myBookLib.initialize();
		} catch (InstantiationException e1) {
			error.add(e1.getMessage());
		} catch (IllegalAccessException e1) {
			error.add(e1.getMessage());
		} catch (ClassNotFoundException e1) {
			error.add(e1.getMessage());
		} catch (SQLException e) {
			error.add(e.getMessage());		
		}
		request.getSession().setAttribute("library", myBookLib);
		request.getSession().setAttribute("panier", panier);
		request.setAttribute("error", error);
		// Redirection
		RequestDispatcher dispatcher = request.getRequestDispatcher("autors.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (final ServletException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
