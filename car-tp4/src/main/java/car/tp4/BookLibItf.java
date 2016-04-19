package car.tp4;

import java.util.List;

public interface BookLibItf {

	public void initialize(final List<BookDAO> books);
	
	public List<String> getAutors();
	
	public void addBook(final BookDAO book);
	
}
