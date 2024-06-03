import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;

import discord4j.common.util.Snowflake;
import java.io.File;

/**
 * Manages everyone who registers' various profiles.
 * @author Duncan Brown
 *
 */
public class SuperProfileManager {
	private static final String DEFAULT_DIRECTORY = "./Profiles/"; //relative directory
	
	private HashMap<Snowflake, SuperProfile> superProfiles;
	private String workingDirectory;
	
	public SuperProfileManager() {
		this(DEFAULT_DIRECTORY);
	}
	
	public SuperProfileManager(String workingDirectory) {
		superProfiles = new HashMap<>();
		this.workingDirectory = workingDirectory;
	}
	
	public Set<Entry<Snowflake, SuperProfile>> entrySet() {
		return superProfiles.entrySet();
	}
	
	public void addProfile(Snowflake id, Site site, String username) {
		SuperProfile superProfile = new SuperProfile();
		superProfile.put(site, username);
		superProfiles.put(id, superProfile);
	}
	
	public SuperProfile get(Snowflake id) {
		return superProfiles.get(id);
	}
	
	public void exportAll() {
		for (Snowflake id :superProfiles.keySet())
			superProfiles.get(id).export(id, workingDirectory);
	}
	
	public void importAll() {
		String[] filenames = new File(workingDirectory).list();
		
		for (String filename : filenames) {
			superProfiles.put(Snowflake.of(filename.substring(0, filename.length() - 4)), SuperProfile.fromFile(workingDirectory + filename));
		}
	}
	
	public String toString() {
		return entrySet().toString();
	}
	
	public static void main(String args[]) {
		SuperProfileManager spm = new SuperProfileManager();
		//String user = "candsbrown";
		//Site s = Site.CHESSDOTCOM;
		//Snowflake myId = Snowflake.of("393627109307711489");
		//spm.addProfile(myId, s, user);
		//spm.exportAll();
		
		
		spm.importAll();
		System.out.println(spm.get(Snowflake.of("393627109307711489")));
	}
}
