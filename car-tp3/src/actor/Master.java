package actor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import message.Msg;
import message.StartMsg;

/**
 * Classe mère 
 * Elle a pour objectif d'être au dessus de notre arbre.
 * 
 * @author Antoine PETIT et Yann LESAGE
 */
public class Master extends UntypedActor {

	private ActorSystem system = ActorSystem.create("Application-Akka");
	private ActorRef nodeRouter;
	private Map<String, ActorRef> nodes;

	/**
	 * Constructeur de la classe mère
	 * 
	 * Génère l'arbre et place l'ensemble de ses noeuds dans une table de hashage
	 */
	public Master() {
		nodes = new HashMap<String, ActorRef>();
		nodes.put("6", system.actorOf(Props.create(Node.class, "6", new ArrayList<ActorRef>())));
		List<ActorRef> listFour = new ArrayList<ActorRef>();
		listFour.add(nodes.get("6"));
		nodes.put("4", system.actorOf(Props.create(Node.class, "4", listFour)));
		nodes.put("3", system.actorOf(Props.create(Node.class, "3", new ArrayList<ActorRef>())));
		List<ActorRef> listFive = new ArrayList<ActorRef>();
		listFive.add(nodes.get("6"));
		List<ActorRef> listTwo = new ArrayList<ActorRef>();
		listTwo.add(nodes.get("4"));
		listTwo.add(nodes.get("3"));
		nodes.put("5", system.actorOf(Props.create(Node.class, "5", listFive)));
		nodes.put("2", system.actorOf(Props.create(Node.class, "2", listTwo)));
		List<ActorRef> listOne = new ArrayList<ActorRef>();
		listOne.add(nodes.get("2"));
		listOne.add(nodes.get("5"));

		nodes.put("1", system.actorOf(Props.create(Node.class, "1", listOne)));
	}

	/**
	 * Traitement de la réception d'un message.
	 * 
	 * Affiche le message puis le renvoie au premier noeud de l'arbre
	 * 
	 * @param message le message a traiter
	 */
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof Msg) {
			Msg messageString = (Msg) message;
			System.out.println("Message reçu par le master : \"" + messageString.getMsg()+ "\"");
			nodes.get("1").forward(message, getContext());
		} else if (message instanceof StartMsg) {
			System.out.println("StartMsg reçu par le master ");
			nodes.get("1").forward(message, getContext());
		} else {
			unhandled(message);
		}

	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("Application-Akka");
		ActorRef master = system.actorOf(Props.create(Master.class));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Bienvenue sur l'application Akka.");
		String message = "";
		if (args.length > 0)
			System.out.println("Le noeud de départ est le noeud suivant : " +  args[0]);
		System.out.println("Veuillez saisir le message que vous souhaitez envoyer aux différents noeuds :");
		int id = 0;
		try {
			while ((message = br.readLine()) != null) {
				if ("QUIT".equals(message.toUpperCase()))
					break;
				if (args.length > 0) {
					System.out.println("Le noeud de départ est le noeud suivant : " + args[0]);
					master.tell(new StartMsg(id, args[0], message), ActorRef.noSender());
				} else {
					master.tell(new Msg(id, message), ActorRef.noSender());
				}
				System.out.println("Veuillez saisir le message que vous souhaitez envoyer aux différents noeuds : ");
				id++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Merci d'avoir utilisé l'application.\nAu revoir.");
	}
}