package Message;

public class StartMsg {
	private String startNode;
	private String message;

	public StartMsg(String startNodeName, String message){
		this.startNode= startNodeName;
		this.message = message;
	}
	
	public Boolean isStartNode(String name){
		return this.startNode.equals(name);
	}
	
	public String getMsg(){
		return this.message;
	}
}
