package message;

/**
 * Classe représentant un message devant être traité
 * 
 * @author Antoine PETIT et Yann LESAGE
 *
 */
public class Msg {
	protected String message;
	protected int id;

	/**
	 * Constructeur de la classe Msg
	 * 
	 * @param id l'id du message
	 * @param message le message en lui même
	 */
	public Msg(int id, String message){
		this.id = id;
		this.message = message;
	}
	
	/**
	 * Retourne l'id du message
	 * 
	 * @return l'id du message
	 */
	public int getId(){
		return this.id;
	}
	
	/**
	 * Retourne le message 
	 * 
	 * @return le message
	 */
	public String getMsg(){
		return this.message;
	}
}
