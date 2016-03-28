import java.util.List;

import Message.StartMsg;
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
	
	protected void forwardtoChildren(Object message){
		for (ActorRef node : children) {
			node.forward(message, getContext());
		}
	}
	
	protected void processMsg(String messageString){
		System.out.println("Le message : \"" + messageString + "\" reçu par le noeud " + name);
		this.forwardtoChildren(messageString);
	}
	
	public void onReceive(Object message) throws InterruptedException {
		if (message instanceof String) {
			String messageString = (String) message; 
			this.processMsg(messageString);
		} else if (message instanceof StartMsg){
			StartMsg m = (StartMsg) message;
			System.out.println("startmesage reçu par le noeud " + name);
			if(m.isStartNode(name)){
				this.processMsg(m.getMsg());
			}else{
				this.forwardtoChildren(message);
			}
		}
		else {
			unhandled(message);
		}
	}

}
