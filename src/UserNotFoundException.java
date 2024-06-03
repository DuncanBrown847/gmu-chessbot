
public class UserNotFoundException extends Exception {
	private String msg;
	
	static final long serialVersionUID = 0x01197279;
	
	public UserNotFoundException(String msg) {
		this.msg = msg;
	}
	
	public UserNotFoundException() {
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
