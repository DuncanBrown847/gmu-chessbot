
import discord4j.common.util.Snowflake;
import java.io.File;
import java.util.List;
import java.util.Arrays;

/**
 * Represents a combined lichess/chess.com profile.
 * @author Duncan Brown
 *
 */
public class DualProfile {
	private static final String DEFAULT_DIRECTORY = "./Profiles"; //relative directory
	
	private Snowflake id;
	
	private ChessDotComProfile chessDotCom = null;
	private LichessProfile lichess = null;
	
	/**
	 * Constructor method for a DualProfile.
	 * @param id A Snowflake (Discord account ID).
	 * @param chessDotCom A ChessDotComProfile. Can be null.
	 * @param lichess A LichessProfile. Can be null.
	 */
	public DualProfile(Snowflake id, ChessDotComProfile chessDotCom, LichessProfile lichess) {
		this.id = id;
		this.chessDotCom = chessDotCom;
		this.lichess = lichess;
	}
	
	/**
	 * Empty constructor.
	 */
	public DualProfile() {
		
	}
	
	public Snowflake getId() {
		return id;
	}

	public ChessDotComProfile getChessDotCom() {
		return chessDotCom;
	}

	public LichessProfile getLichess() {
		return lichess;
	}

	public void setId(Snowflake id) {
		this.id = id;
	}

	public void setChessDotCom(ChessDotComProfile chessDotCom) {
		this.chessDotCom = chessDotCom;
	}

	public void setLichess(LichessProfile lichess) {
		this.lichess = lichess;
	}
	
	public boolean isEmpty() {
		return (chessDotCom == null && lichess == null);
	}
	
	/**
	 * Updates the ratings.
	 * @param handler RequestHandler used to process the HTTP requests.
	 */
	public boolean update(RequestHandler handler) {
		if (chessDotCom != null) {
			try {
				String chessDotComJSON = handler.getChessDotCom(chessDotCom.getUsername());
				if (chessDotComJSON == null) {
					System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG:UPDATE> ERROR: null return from RequestHandler.getChessDotCom()");
					return false;
				}
					
				chessDotCom = ChessDotComProfile.fromJSON(chessDotComJSON, chessDotCom.getUsername());
			} catch (RequestHandlerException e) {
				e.printStackTrace();
				//this would be a weird error
			}
		}
		
		if (lichess != null) {
			try {
				String lichessJSON = handler.getLichess(lichess.getUsername());
				if (lichessJSON == null) {
					System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG:UPDATE> ERROR: null return from RequestHandler.getLichess()");
					return false;
				}
				
				lichess = LichessProfile.fromJSON(lichessJSON);
			} catch (RequestHandlerException e) {
				e.printStackTrace();
				//this would be a weird error
			}
		}
		
		return true;
	}
	
	/**
	 * Exports all present profiles to the default directory.
	 */
	public void export() {
		if (chessDotCom != null)
			chessDotCom.export(DEFAULT_DIRECTORY + "/" + id.asString() + "/");
		
		if (lichess != null)
			lichess.export(DEFAULT_DIRECTORY + "/" + id.asString() + "/");
	}
	
	public void deleteChessDotCom() {
		this.chessDotCom = null;
		if (new File(DEFAULT_DIRECTORY + "/" + id.asString() + "/chessdotcom.txt").delete())
			System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> File \'" + "chessdotcom.txt" + "\' deleted succesfully");
	}
	
	public void deleteLichess() {
		this.lichess = null;
		if (new File(DEFAULT_DIRECTORY + "/" + id.asString() + "/lichess.txt").delete())
			System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> File \'" + "chessdotcom.txt" + "\' deleted succesfully");
	}
	
	/**
	 * Complete deletion of the profile in ROM.
	 */
	public void deleteAll() {
		this.deleteChessDotCom();
		this.deleteLichess();
		
		if (new File(DEFAULT_DIRECTORY + "/" + id.asString()).delete())
			System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Profile deletion successful");
	}
	
	public String toString() {
		return "DualProfile(" + id.asString() + "){" + chessDotCom + ", " + lichess + "}";
	}
	
	public static DualProfile[] loadProfiles() {
		String[] ids = new File(DEFAULT_DIRECTORY).list();
		if (ids == null)
			return new DualProfile[0];
		
		DualProfile[] result = new DualProfile[ids.length];
		
		for(int i = 0; i < ids.length; i++) {
			String[] filenames = new File(DEFAULT_DIRECTORY + "/" + ids[i]).list();
			List<String> files = Arrays.asList(filenames);
			
			ChessDotComProfile chessDotCom = null;
			LichessProfile lichess = null;
			
			if (files.contains("chessdotcom.txt"))
				chessDotCom = ChessDotComProfile.fromFile(new File(DEFAULT_DIRECTORY + "/" + ids[i] + "/chessdotcom.txt"));
			
			if (files.contains("lichess.txt"))
				lichess = LichessProfile.fromFile(new File(DEFAULT_DIRECTORY + "/" + ids[i] + "/lichess.txt"));
				
			result[i] = new DualProfile(Snowflake.of(ids[i]), chessDotCom, lichess);
			System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Profile [Snowflake{" + ids[i] + "}] loaded successfully");
		}
		
		return result;
	}
}
