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

/**
 * Session Bean g�rant la biblioth�que de livre
 * @author Antoine PETIT et Yann LESAGE
 *
 */
@Stateful(name = "notreBibliotheque")
public class BookLib implements BookLibItf {

	private List<BookDAO> bibli;
	private BookService bs;

	/**
	 * Constructeur
	 * @param books la liste des livres � ajouter dans la biblioth�que
	 */
	public BookLib(final List<BookDAO> books) {
		this.bibli = books;
		this.bs =  new BookService();
	}

	/**
	 * Constructeur
	 */
	public BookLib() {
		this.bibli = new ArrayList<BookDAO>();
		this.bs =  new BookService();
	}

	/**
	 * M�thode d'initialisation du bean 
	 */
	public void initialize()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		//this.bibli = bs.getAllBooks();
		Class.forName("org.hsqldb.jdbcDriver").newInstance();
		Connection connexion = DriverManager.getConnection("jdbc:hsqldb:file:database", "sa", "");
		DatabaseMetaData dbm = connexion.getMetaData();
		ResultSet tables = dbm.getTables(null, null, "LIVRE", null);
		if (tables.next()) {
			Statement statement = connexion.createStatement();
			ResultSet resultat = statement.executeQuery("SELECT * FROM LIVRE");
			while (resultat.next()) {
				this.bibli.add(new BookDAO(((Integer) resultat.getObject("id")).intValue(),
						(String) resultat.getObject("author"), (String) resultat.getObject("title"),
						((Integer) resultat.getObject("year")).intValue()));
			}
		} else {
			// Si la table n'existe pas on la cr��e
			Statement statement = connexion.createStatement();
			statement.executeUpdate("CREATE TABLE Livre (id INT,author VARCHAR(100) , title VARCHAR(100), year INT)");
			connexion.commit();
			connexion.close();
		}

	}

	/**
	 * Retourne tout les auteurs pr�sent sur l'application
	 */
	public List<String> getAutors() {
		final List<String> result = new ArrayList<String>();
		for (final BookDAO book : bibli) {
			if (!result.contains(book.getAuthor()))
				result.add(book.getAuthor());
		}
		return result;
	}

	/**
	 * Ajoute le livre en base
	 */
	public void addBook(final BookDAO book) {
		this.bibli.add(book);
		//this.bs.addBook(book);
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			Connection connexion = DriverManager.getConnection("jdbc:hsqldb:file:database", "sa", "");
			Statement statement = connexion.createStatement();
			statement.executeUpdate("INSERT INTO LIVRE (id, author, title, year) VALUES (" + book.id + ",'"
					+ book.getAuthor() + "','" + book.getTitle() + "'," + book.getYear() + ")");
			connexion.commit();
			connexion.close();
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

	/**
	 * Retourne tout les livres de l'application
	 */
	public List<BookDAO> getBooks() {
		return this.bibli;
	}
	
	/**
	 * Retourne le livre correspondant � l'id pass� en param�tre, sinon null
	 */
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
