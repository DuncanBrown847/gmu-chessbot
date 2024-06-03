import discord4j.common.util.Snowflake;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Superprofiles contain all the profiles 
 * @author Duncan Brown
 *
 */
public class SuperProfile {

	private Map<Site, String> profiles;
	
	public SuperProfile() {
		profiles = new HashMap<>(2);
	}
	
	public Set<Entry<Site, String>> entrySet() {
		return profiles.entrySet();
	}
	
	public void put(Site site, String username) {
		profiles.put(site, username);
	}
	
	public void export(Snowflake id, String path) {
		List<String> strings = new ArrayList<String>(profiles.size());
		
		for (Entry<Site, String> p : entrySet()) {
		    strings.add(p.toString());
		}
		
		File filePath = new File(path); 
		filePath.mkdirs();
		File file = new File(path + id.asString() + ".txt");
		
		try {
			Files.write(file.toPath(), strings, StandardCharsets.UTF_8);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static SuperProfile fromFile(Snowflake id, String path) {
		return fromFile(path + id.asString() + ".txt");
	}
	
	public static SuperProfile fromFile(String path) {
		File file = new File(path);
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(file.toPath());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		SuperProfile result = new SuperProfile();
		
		for (String line : lines) {
			String[] splitLine = line.split("=");
			result.put(Site.valueOf(splitLine[0]), splitLine[1]);
		}
		
		return result;
	}
	
	
	public String toString() {
		return entrySet().toString();
	}
	
	/**
	 * For basic testing.
	 * @param args
	 */
	public static void main(String[] args) {
		/*
		
		ProfileManager man = new ProfileManager(Snowflake.of("393627109307711489"));
		Profile f = new ChessDotComProfile("candsbrown");
		
		man.addProfile(f);
		
		System.out.println(f);
		man.export();
		*/
		String DEFAULT_DIRECTORY = "./Profiles/"; //relative directory
		//profiles.add(new ChessDotComProfile(""));
		//profiles.add(new LichessProfile(""));
		
		SuperProfile profs = fromFile(DEFAULT_DIRECTORY + "393627109307711489.txt");
		//System.out.println(profs);
		
		
		System.out.println(profs.entrySet());
		
		//profs.export(Snowflake.of("393627109307711489"), DEFAULT_DIRECTORY);
		
		//SuperProfile n = fromFile(Snowflake.of("393627109307711489"), DEFAULT_DIRECTORY);
		//System.out.println(n);
	}
}
