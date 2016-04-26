package car.tp4;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Panier {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Map<Integer, Integer> content;
	private int nbArticles = 0;

	public Panier() {
		this.content = new HashMap<Integer, Integer>();
	}

	public void addBook(int id) {
		Integer previousValue = this.content.get(id);
		if (previousValue == null) {
			this.content.put(Integer.valueOf(id), Integer.valueOf(1));
		} else {
			previousValue = Integer.valueOf(previousValue.intValue() + 1);
			this.content.put(Integer.valueOf(id), previousValue);
		}
		nbArticles++;
	}

	public void removeBook(int id) {
		Integer previousValue = this.content.get(id);
		if (previousValue != null) {
			nbArticles--;
			previousValue = Integer.valueOf(previousValue.intValue() - 1);
			if (previousValue.intValue() == 0) {
				this.content.remove(id);
			}else {
				
				this.content.put(Integer.valueOf(id), previousValue);
			}
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Map<Integer, Integer> getContent() {
		return content;
	}

	public int getNbArticles() {
		return nbArticles;
	}

}
