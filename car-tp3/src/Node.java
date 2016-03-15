import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedActor;

public class Node extends UntypedActor {
	private ActorSystem system= ActorSystem.create();
	private List<ActorRef> children;
	
	public void onReceive(Object message) throws InterruptedException {
		if (message instanceof String) {
			for (ActorRef node : children) {
				node.forward(message, getSystem());
			}
		}
		else {
			unhandled(message);
		}
	}

}
