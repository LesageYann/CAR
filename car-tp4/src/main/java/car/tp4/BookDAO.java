package car.tp4;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookDAO {

	private static int count = 0; 
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int id;
	
	private String author;
	
	private String title;
	
	private int year;
	
	public BookDAO(final String author,final String title, int year) {
		this.id = count++;
		this.author = author;
		this.title = title;
		this.year = year;
	}
	
	public BookDAO(int id,final String author,final String title, int year) {
		this.id = id;
		this.author = author;
		this.title = title;
		this.year = year;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(final String author) {
		this.author = author;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	
}
