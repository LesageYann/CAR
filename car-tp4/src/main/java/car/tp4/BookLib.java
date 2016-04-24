package car.tp4;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

@Stateful(name="notreBibliotheque")
public class BookLib implements BookLibItf {

	private List<BookDAO> bibli;

	public BookLib(final List<BookDAO> books) {
		this.bibli = books;
	}

	public BookLib(){
		this.bibli = new ArrayList<BookDAO>();
	}
	
	public void initialize(final List<BookDAO> books){
		this.bibli = books;
	}
	
	public List<String> getAutors() {
		final List<String> result = new ArrayList<String>(); 
		for (final BookDAO book : bibli){
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
			statement.executeUpdate("INSERT INTO LIVRE (id, author, title, year) VALUES ("+ book.id + ",'" + book.getAuthor() + "','"
					+ book.getTitle() + "'," + book.getYear() + ")");
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

}
