package car.tp4;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Classe décrivant un panier contenant des livres, chaque livre peut être possédé en une quantité particulière
 * 
 * @author Antoine PETIT et Yann LESAGE
 *
 */
@Entity
public class Panier {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private Map<Integer, Integer> content;
	private int nbArticles = 0;

	/**
	 * Constructeur de la classe Panier
	 */
	public Panier() {
		this.content = new HashMap<Integer, Integer>();
	}

	/**
	 * Ajoute le livre au panier
	 * @param id id du livre
	 */
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
	
	/**
	 * Retire un livre du panier
	 * @param id id du livre
	 */
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
	
	/**
	 * Retourne l'id du panier
	 * @return l'id du panier
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set l'id du panier
	 * @param id id du panier
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Retourne la map représentant le contenu du panier
	 * @return la map représentant le contenu du panier
	 */
	public Map<Integer, Integer> getContent() {
		return content;
	}

	/**
	 * Retourne la quantité total de livre contenu dans le panier
	 * @return
	 */
	public int getNbArticles() {
		return nbArticles;
	}

}
