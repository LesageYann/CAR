package car.tp4;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateful;

@Stateful(name = "notreBibliotheque")
public class BookLib implements BookLibItf {

	private List<BookDAO> bibli;

	public BookLib(final List<BookDAO> books) {
		this.bibli = books;
	}

	public BookLib() {
		this.bibli = new ArrayList<BookDAO>();
	}

	public void initialize()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<BookDAO> books = new ArrayList<BookDAO>();
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connexion = DriverManager.getConnection("jdbc:hsqldb:file:database", "sa", "");
		DatabaseMetaData dbm = connexion.getMetaData();
		ResultSet tables = dbm.getTables(null, null, "LIVRE", null);
		if (tables.next()) {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery("SELECT * FROM LIVRE");
			while (resultat.next()) {
				books.add(new BookDAO(((Integer) resultat.getObject("id")).intValue(),
						(String) resultat.getObject("author"), (String) resultat.getObject("title"),
						((Integer) resultat.getObject("year")).intValue()));
			}
		} else {
			// Si la table n'existe pas on la créée
			Statement statement = connexion.createStatement();
			statement.executeUpdate("CREATE TABLE Livre (id INT,author VARCHAR(100) , title VARCHAR(100), year INT)");
		}

	}

	public List<String> getAutors() {
		final List<String> result = new ArrayList<String>();
		for (final BookDAO book : bibli) {
			if (!result.contains(book.getAuthor()))
				result.add(book.getAuthor());
		}
		return result;
	}

	public void addBook(final BookDAO book) {
		this.bibli.add(book);
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			Connection connexion = DriverManager.getConnection("jdbc:hsqldb:file:database", "sa", "");
			Statement statement = connexion.createStatement();
			statement.executeUpdate("INSERT INTO LIVRE (id, author, title, year) VALUES (" + book.id + ",'"
					+ book.getAuthor() + "','" + book.getTitle() + "'," + book.getYear() + ")");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<BookDAO> getBooks() {
		return this.bibli;
	}

	public BookDAO getBook(int id) {
		Iterator<BookDAO> it = this.bibli.iterator();
		while (it.hasNext()) {
			BookDAO book = (BookDAO) it.next();
			if (book.id == id)
				return book;
		}
		return null;
	}

}
