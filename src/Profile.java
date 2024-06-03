
public class Profile implements Comparable<Profile>{
	private String username;
	private Site site;
	
	public Profile(Site site, String username) {
		this.username = username;
		this.site = site;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Site getSite() {
		return site;
	}
	
	public String toString() {
		return this.getSite().name() + "::" + this.getUsername();
	}
	
	@Override
	public boolean equals(Object other) {
		return (other instanceof Profile && (this.getSite() == ((Profile)other).getSite()));
	}
	
	//not really used
	@Override
	public int compareTo(Profile other) {
		return this.getUsername().compareTo(other.getUsername());
	}
}
