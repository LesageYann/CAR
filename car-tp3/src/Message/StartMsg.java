package Message;

public class StartMsg extends Msg{
	private String startNode;

	public StartMsg(int id, String startNodeName, String message){
		super(id, message);
		this.startNode= startNodeName;
	}
	
	public Boolean isStartNode(String name){
		return this.startNode.equals(name);
	}
}
