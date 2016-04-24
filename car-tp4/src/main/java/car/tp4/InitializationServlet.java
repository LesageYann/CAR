package car.tp4;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		List<BookDAO> books = new ArrayList<BookDAO>();
		List<String> error = new ArrayList<String>();
		try {

			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			Connection connexion = DriverManager.getConnection("jdbc:hsqldb:file:database", "sa", "");
			DatabaseMetaData dbm = connexion.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "LIVRE", null);
			if (tables.next()) {
				Statement statement = connexion.createStatement();
				ResultSet resultat = statement.executeQuery("SELECT * FROM LIVRE");
				while (resultat.next()) {
					//Comment gérer l'id du livre ?
					books.add(new BookDAO((String) resultat.getObject("author"), (String) resultat.getObject("title"),
							((Integer) resultat.getObject("year")).intValue()));
				}
			} else {
				//Si la table n'existe pas on la créée
				Statement statement = connexion.createStatement() ;
				statement.executeUpdate("CREATE TABLE Livre (id INT,author CHAR , title CHAR, year INT)");
			}

		} catch (InstantiationException e1) {
			error.add(e1.toString());
		} catch (IllegalAccessException e1) {
			error.add(e1.toString());
		} catch (ClassNotFoundException e1) {
			error.add(e1.toString());
		} catch (SQLException e) {
			error.add(e.toString());
		}
		myBookLib.initialize(books);
		request.getSession().setAttribute("library", myBookLib);
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
