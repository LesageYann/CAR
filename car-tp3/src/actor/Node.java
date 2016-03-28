package actor;
import java.util.LinkedList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;
import message.Msg;
import message.StartMsg;

/**
 * 
 * Classe représentant un noeud d'un arbre
 * 
 * @author Antoine PETIT et Yann LESAGE
 *
 */
public class Node extends UntypedActor {
	
	
	private List<ActorRef> children;
	private String name;	
	private LinkedList<Integer> idLastMesage;
	
	/**
	 * Constructeur du noeud
	 * 
	 * @param name le nom du noeud
	 * @param children la liste des ses enfants
	 */
	public Node(String name, List<ActorRef> children) {
		this.name = name;
		this.children = children;
		this.idLastMesage= new LinkedList<Integer>();
	}
	
	/**
	 * Renvoie le message tels quel aux enfants du noeud
	 * 
	 * @param message le message qui doit être diffusé aux enfants
	 */
	protected void forwardtoChildren(Object message){
		for (ActorRef node : children) {
			node.forward(message, getContext());
		}
	}
	
	/**
	 * Fait le traitement pour passer d'un StartMsg à un Msg
	 * 
	 * @param message le message a traiter
	 */
	protected void processMsg(Msg message){
		System.out.println("Le message : \"" + message.getMsg() + "\" reçu par le noeud " + name);
		this.forwardtoChildren(new Msg(message.getId(),message.getMsg()));
	}
	
	/**
	 * Traitement de la réception d'un message.
	 * 
	 * Affiche le message puis le renvoie à ses enfants si ce message n'a pas déjà était reçu
	 * 
	 * @param message le message a traiter
	 */
	public void onReceive(Object message) throws InterruptedException {
		if (message instanceof Msg) {
			Msg messageString = (Msg) message;
			if (this.alreadyRead(messageString)){
				return;
			}
			if (message instanceof StartMsg){
				StartMsg m = (StartMsg) message;
				System.out.println("startmessage reçu par le noeud " + name);
				if(m.isStartNode(name)){
					this.processMsg(m);
				}else{
					this.forwardtoChildren(message);
				}
			} else{
      			this.processMsg(messageString);
			}
		} else {
			unhandled(message);
		}
	}

	/**
	 * Méthode permettant de savoir si un message a déjà était lu par ce noeud
	 * 
	 * @param message le message lu
	 * @return Vrai si le message a déjà était reçu, faux sinon
	 */
	private boolean alreadyRead(Msg message) {
		if(this.idLastMesage.contains(message.getId())){
			return true;
		}else{
			if(this.idLastMesage.size()>10){
				this.idLastMesage.remove();
			}
			this.idLastMesage.add(message.getId());
			return false;
		}
	}

}
