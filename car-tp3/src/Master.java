import java.util.ArrayList;
import java.util.List;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class Master extends UntypedActor {

	private ActorSystem system = ActorSystem.create("MyLittlePoney");
	private ActorRef nodeRouter;

	public Master() {
		ActorRef childSix = system.actorOf(Props.create(Node.class, "6", new ArrayList<ActorRef>()));
		ActorRef childFour = system
				.actorOf(Props.create(Node.class, "4", new ArrayList<ActorRef>() ));
		ActorRef childThree = system.actorOf(Props
				.create(Node.class, "3", new ArrayList<ActorRef>()));
		List<ActorRef> listFive = new ArrayList<ActorRef>();
		listFive.add(childSix);
		List<ActorRef> listTwo = new ArrayList<ActorRef>();
		listTwo.add(childFour);
		listTwo.add(childThree);
		ActorRef childFive = system.actorOf(Props.create(Node.class, "5",
				listFive));
		ActorRef childTwo = system.actorOf(Props.create(Node.class, "2",
				listTwo));
		List<ActorRef> listOne = new ArrayList<ActorRef>();
		listOne.add(childFive);
		listOne.add(childTwo);

		nodeRouter = system.actorOf(Props.create(Node.class, "1", listOne));
	}

	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof String) {
			String messageString = (String) message;
			System.out.println("Message reçu par le master :" + messageString);
			nodeRouter.forward(message, getContext());
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
