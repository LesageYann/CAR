package car.tp4;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

@Local
public interface BookLibItf {

	public void initialize() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;
	
	public List<String> getAutors() ;
	
	public List<BookDAO> getBooks();
	
	public void addBook(final BookDAO book);
	
	public BookDAO getBook(int id);
	
}
