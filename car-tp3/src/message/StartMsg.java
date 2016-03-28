package message;

/**
 * Classe repr�sentant un message ne devant pas �tre trait�
 * 
 * @author Antoine PETIT et Yann LESAGE
 *
 */
public class StartMsg extends Msg{
	private String startNode;

	/**
	 * Cr�� un message StartMsg
	 * 
	 * @param id l'id du message 
	 * @param startNodeName le noeud � partir duquel la diffusion doit commancer
	 * @param message le message
	 */
	public StartMsg(int id, String startNodeName, String message){
		super(id, message);
		this.startNode= startNodeName;
	}
	
	/**
	 * Indique si le nom du noeud est celui du noeud � partir duquel on doit commancer la diffusion
	 * 
	 * @param name le nom du noeud
	 * @return Vrai si le nom du noeud correspond, faux sinon
	 */
	public Boolean isStartNode(String name){
		return this.startNode.equals(name);
	}
}
