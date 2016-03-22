import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;

public class Node extends UntypedActor {
	
	
	private List<ActorRef> children;
	private String name;	
	
	public Node(String name, List<ActorRef> children) {
		this.name = name;
		this.children = children;
	}
	
	public void onReceive(Object message) throws InterruptedException {
		if (message instanceof String) {
			String messageString = (String) message; 
			System.out.println("Le message : \"" + messageString + "\" reçu par le noeud " + name);
			for (ActorRef node : children) {
				node.forward(message, getContext());
			}
		}
		else {
			unhandled(message);
		}
	}

}
