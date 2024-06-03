
/*
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
//import java.nio.file.Path;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
*/

/**
 * Class for representing a chess.com profile.
 * @author Duncan Brown
 *
 */
/*
public class ChessDotComProfile extends Profile {
	
	public ChessDotComProfile(String username) {
		super(username);
	}
	
	
	
	/*
	private int bullet;
	private int blitz;
	private int rapid;
	
	public ChessDotComProfile(String username, int bullet, int blitz, int rapid) {
		this.username = username;
		
		this.bullet = bullet;
		this.blitz = blitz;
		this.rapid = rapid;
	}
	
	public String getUsername() {
		return username;
	}

	public int getBullet() {
		return bullet;
	}

	public int getBlitz() {
		return blitz;
	}

	public int getRapid() {
		return rapid;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setBullet(int bullet) {
		this.bullet = bullet;
	}

	public void setBlitz(int blitz) {
		this.blitz = blitz;
	}

	public void setRapid(int rapid) {
		this.rapid = rapid;
	}
	
	/**
	 * Exports the profile to a txt document at the specified directory.
	 * Will create the directory if it does not exist.
	 * @param directory
	 */
	/*
	public void export(String directory) {
		String path = directory;
		String filename = "chessdotcom.txt";
		
		List<String> lines = Arrays.asList(username, ""+bullet, ""+blitz, ""+rapid);
		File file = new File(path);
		file.mkdirs();
		file = new File(path + filename);
		
		try {
			Files.write(file.toPath(), lines, StandardCharsets.UTF_8);
			System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> File \'" + filename + "\' created succesfully");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	*/
	
	/**
	 * Returns a new ChessDotComProfile based on a json (String format) from the API of their site, along with the username.
	 * @param String json 
	 * @return ChessDotComProfile
	 */
	/*
	public static ChessDotComProfile fromJSON(String json, String username) {
		ChessDotComProfile profile = ChessDotComProfile.fromJSON((JSONObject)(JSONValue.parse(json)));
		profile.setUsername(username);
		return (profile);
	}
	*/
	
	/**
	 * Returns a new ChessDotComProfile based on a json (JSONObject format) from the API of their site. Leaves username set to null.
	 * @param JSONObject json
	 * @return ChessDotComProfile
	 */
	/*
	public static ChessDotComProfile fromJSON(JSONObject json) {
		int bullet = 0;
		int blitz = 0;
		int rapid = 0;
		
		if (json.containsKey("chess_bullet"))
			bullet = (int)(long)((JSONObject)((JSONObject)json.get("chess_bullet")).get("last")).get("rating");
			
		if (json.containsKey("chess_blitz"))
			blitz = (int)(long)((JSONObject)((JSONObject)json.get("chess_blitz")).get("last")).get("rating");
			
		if (json.containsKey("chess_rapid"))
			rapid = (int)(long)((JSONObject)((JSONObject)json.get("chess_rapid")).get("last")).get("rating");
		
		return new ChessDotComProfile(null, bullet, blitz, rapid);
	}
	*/
	
	/**
	 * Returns a new ChessDotComProfile based on a File.
	 * @param File
	 * @return ChessDotComProfile
	 */
	/*
	public static ChessDotComProfile fromFile(File file) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(file.toPath());
		}
		catch (IOException e) {
			e.printStackTrace();
		}

		return new ChessDotComProfile(lines.get(0), Integer.parseInt(lines.get(1)), Integer.parseInt(lines.get(2)), Integer.parseInt(lines.get(3)));
	}
	*/
	
	/*
	public String toString() {
		return "ChessDotComProfile{" + username + ", " + bullet + ", " + blitz + ", " + rapid + "}";
	}
	*/
	/*
	public static void main(String args[]) {
		Profile p = new ChessDotComProfile("d");
		System.out.println(p.toString());
	}
}
*/