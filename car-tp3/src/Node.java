import java.util.LinkedList;
import java.util.List;

import Message.Msg;
import Message.StartMsg;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;

public class Node extends UntypedActor {
	
	
	private List<ActorRef> children;
	private String name;	
	private LinkedList<Integer> idLastMesage;
	
	public Node(String name, List<ActorRef> children) {
		this.name = name;
		this.children = children;
		this.idLastMesage= new LinkedList<Integer>();
	}
	
	protected void forwardtoChildren(Object message){
		for (ActorRef node : children) {
			node.forward(message, getContext());
		}
	}
	
	protected void processMsg(Msg message){
		System.out.println("Le message : \"" + message.getMsg() + "\" reçu par le noeud " + name);
		this.forwardtoChildren(message);
	}
	
	public void onReceive(Object message) throws InterruptedException {
		if (message instanceof Msg) {
			Msg messageString = (Msg) message;
			if (this.alreadyRead(messageString)){
				return;
			}
			if (message instanceof StartMsg){
				StartMsg m = (StartMsg) message;
				System.out.println("startmesage reçu par le noeud " + name);
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
