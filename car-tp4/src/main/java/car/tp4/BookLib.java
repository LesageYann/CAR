package car.tp4;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;

@Stateful
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
	}

}
