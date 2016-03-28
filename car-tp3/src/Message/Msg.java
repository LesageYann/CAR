package Message;

public class Msg {
	protected String message;
	protected int id;

	public Msg(int id, String message){
		this.id = id;
		this.message = message;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getMsg(){
		return this.message;
	}
}
