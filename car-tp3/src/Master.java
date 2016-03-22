import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Master extends UntypedActor {

	private ActorSystem system = ActorSystem.create("MyLittlePoney");
	private ActorRef nodeRouter;
	private Map<String, ActorRef> nodes; 

	public Master() {
		nodes = new HashMap<String, ActorRef>();
        nodes.put("6", system.actorOf(Props.create(Node.class, "6", new ArrayList<ActorRef>())));
		nodes.put("4", system.actorOf(Props.create(Node.class, "4", new ArrayList<ActorRef>())));
		nodes.put("3", system.actorOf(Props.create(Node.class, "3", new ArrayList<ActorRef>())));
		List<ActorRef> listFive = new ArrayList<ActorRef>();
		listFive.add(nodes.get("6"));
		List<ActorRef> listTwo = new ArrayList<ActorRef>();
		listTwo.add(nodes.get("4"));
		listTwo.add(nodes.get("3"));
		nodes.put("5", system.actorOf(Props.create(Node.class, "5",listFive)));
		nodes.put("2", system.actorOf(Props.create(Node.class, "2",listTwo)));
		List<ActorRef> listOne = new ArrayList<ActorRef>();
		listOne.add(nodes.get("2"));
		listOne.add(nodes.get("5"));

		nodes.put("1",system.actorOf(Props.create(Node.class, "1", listOne)));
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			String messageString = (String) message;
			System.out.println("Message reçu par le master :" + messageString);
			nodes.get("1").forward(message, getContext());
		} else {
			unhandled(message);
		}

	}

	public static void main(String[] args) {
		ActorSystem system = ActorSystem.create("MyLittlePoney");
		ActorRef master = system.actorOf(Props.create(Master.class));
		master.tell("lipatatorne", null);
	}
}