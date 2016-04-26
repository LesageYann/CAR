package car.tp4.Session.Interface;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import car.tp4.Entity.BookDAO;

/**
 * Interface du Session Bean gérant la bibliothèque de livre
 * @author Antoine PETIT & Yann LESAGE
 *
 */
@Local
public interface BookLibItf {

	public void initialize() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	
	public List<String> getAutors() ;
	
	public List<BookDAO> getBooks();
	
	public void addBook(final BookDAO book);
	
	public BookDAO getBook(int id);
	
}
