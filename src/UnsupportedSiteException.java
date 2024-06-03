
public class UnsupportedSiteException extends Exception {
	private String msg;
	
	static final long serialVersionUID = 0x01197279;
	
	public UnsupportedSiteException(String msg) {
		this.msg = msg;
	}
	
	public UnsupportedSiteException() {
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