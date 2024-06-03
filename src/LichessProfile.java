/*
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
*/
/**
 * Class for representing a lichess profile.
 * @author Duncan Brown
 *
 */
/*
public class LichessProfile extends Profile {
	
	public LichessProfile(String username) {
		super(username);
	}
	
	/*
	/**
	 * Exports the profile to a txt document at the specified directory.
	 * Will create the directory if it does not exist.
	 * @param directory
	 */
	/*
	public void export(String directory) {
		String path = directory;
		String filename = "lichess.txt";
		
		List<String> lines = Arrays.asList(username, ""+bullet, ""+blitz, ""+rapid, ""+classical);
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
	
	/*
	/**
	 * Returns a new LichessProfile based on a json (String format) from the API of their site.
	 * @param Stringjson
	 * @return LichessProfile
	 */
	/*
	public static LichessProfile fromJSON(String json) {
		return LichessProfile.fromJSON((JSONObject)JSONValue.parse(json));
	}
	*/
	
	/*
	/**
	 * Returns a new LichessProfile based on a json (JSONObject format) from the API of their site.
	 * @param JSONObject json
	 * @return LichessProfile
	 */
	/*
	public static LichessProfile fromJSON(JSONObject json) {
		return new LichessProfile((String) json.get("username"), (int)(long)((JSONObject)((JSONObject)json.get("perfs")).get("bullet")).get("rating"), (int)(long)((JSONObject)((JSONObject)json.get("perfs")).get("blitz")).get("rating"), (int)(long)((JSONObject)((JSONObject)json.get("perfs")).get("rapid")).get("rating"), (int)(long)((JSONObject)((JSONObject)json.get("perfs")).get("classical")).get("rating"));
	}
	*/
	
	/*
	/**
	 * Returns a new LichessProfile based on a File.
	 * @param File
	 * @return LichessProfile
	 */
	/*
	public static LichessProfile fromFile(File file) {
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(file.toPath());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return new LichessProfile(lines.get(0), Integer.parseInt(lines.get(1)), Integer.parseInt(lines.get(2)), Integer.parseInt(lines.get(3)), Integer.parseInt(lines.get(4)));
	}
	
	public String toString() {
		return "LichessProfile{" + username + ", " + bullet + ", " + blitz + ", " + rapid + ", " + classical + "}";
	}
	*/
	
//}