
public class RequestHandlerException extends Exception {
	private String msg;
	
	static final long serialVersionUID = 0x01197279;
	
	public RequestHandlerException(String msg) {
		this.msg = msg;
	}
	
	public RequestHandlerException() {
		this.msg = null;
	}
	
	public String getMessage() {
		return msg;
	}
	
	@Override
	public String toString() {
		return msg;
	}
}
