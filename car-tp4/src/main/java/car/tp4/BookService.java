package car.tp4;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Classe tentant d'utiliser l'entity manager
 * @author Antoine PETIT et Yann LESAGE
 *
 */
@Stateless
public class BookService {
	
    @PersistenceContext(unitName = "book-pu")
    private EntityManager em; 
    
    /**
     * Ajoute le livre en base
     * @param book le livre à ajouter
     */
    public void addBook(BookDAO book){
    	em.persist(book);
    }
    
    /**
     * Retourne tout les livres présent en base
     * @return l'ensemble des livres présent en base
     */
    @SuppressWarnings("unchecked")
	public List<BookDAO> getAllBooks(){
    	Query query = em.createQuery("SELECT * FROM BookDAO");
    	return (List<BookDAO>) query.getResultList();
    }

}
