import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map.Entry;

//import discord4j.common.util.Snowflake;

/**
 * A handler meant to make retrieving data from Lichess and Chess.com as simple as possible.
 * @author Duncan Brown
 */
public class RequestHandler {
	private HttpClient client;
	
	//private final String CHESSDOTCOM_ENDPOINT = "https://api.chess.com/";
	//private final String LICHESS_ENDPOINT = "https://lichess.org/api/";
	
	/**
	 * Constructor. Initializes client and some initial settings.
	 */
	public RequestHandler() {
		client = HttpClient.newBuilder()
				.followRedirects(HttpClient.Redirect.NORMAL)
				.build();
	}
	
	/**
	 * Helper method for generating GET requests
	 * @param uri - The uri for the request.
	 * @return The request.
	 */
	private HttpRequest generateRequest(String uri) {
		HttpRequest request = HttpRequest.newBuilder()
				.GET() //type (GET or POST) and content of request, usually format is specified by the API docs
				.header("accept", "application/json") //"accept" header, details that this type of data will be accepted
				.uri(URI.create(uri)) //URI of the resources to grab (URL of lichess info in this case)
				.build();
		
		return request;
	}
	
	/**
	 * Helper method that requests using the given URI. Expects to receive a JSON format string.
	 * @param URI online site to poll.
	 * @return JSON-format string of the response.
	 * @throws Any of the 3 exceptions that could occur during the process: IOException, InterruptedException, RequestHandlerException.
	 */
	private String getRequest(String URI) throws IOException, InterruptedException, UserNotFoundException, RequestHandlerException {
		HttpRequest request = generateRequest(URI);
		HttpResponse<String> response = null;
		
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException e) {
			throw e;
		} catch (InterruptedException e) {
			throw e;
		}
		
		if (response.statusCode() == 404)
			throw new UserNotFoundException("ERROR: Bad response (404) when requesting \"" + URI + "\"");
		else if (response.statusCode() != 200)
			throw new RequestHandlerException("ERROR: Unexpected response ("+ response.statusCode() +") when requesting \"" + URI + "\"");
		
		return response.body();
	}

	public String getProfile(Site site, String username) throws IOException, InterruptedException, UserNotFoundException, RequestHandlerException, UnsupportedSiteException {
		if (site == Site.CHESSDOTCOM)
			return getChessDotCom(username);
		else if (site == Site.LICHESS)
			return getLichess(username);
		else
			throw new UnsupportedSiteException("ERROR: Site (" + site.name() + ") not implemented yet");
	}
	
	public String getProfile(Entry<Site, String> profile) throws IOException, InterruptedException, UserNotFoundException, RequestHandlerException, UnsupportedSiteException {
		return getProfile(profile.getKey(), profile.getValue());
	}
	
	/**
	 * Gets Chess.com user info as a string in JSON format.
	 * @param username
	 * @return the user info
	 * @throws Any of the 3 exceptions that could occur during the process: IOException, InterruptedException, RequestHandlerException.
	 */
	public String getChessDotCom(String username) throws IOException, InterruptedException, UserNotFoundException, RequestHandlerException {
		return getRequest("https://api.chess.com/" + "pub/player/" + username + "/stats");
	}
	
	/**
	 * Gets Lichess user info as a string in JSON format.
	 * @param lichess The lichess profile to request
	 * @return the user info
	 * @throws Any of the 3 exceptions that could occur during the process: IOException, InterruptedException, RequestHandlerException.
	 */
	public String getLichess(String username) throws IOException, InterruptedException, UserNotFoundException, RequestHandlerException {
		return getRequest("https://lichess.org/api/" + "user/" + username);
	}
	
	//all one needs to do in order to add functionality for a new site is make a new method like the simple ones above!
	
	/**
	 * For basic testing.
	 * @param args
	 */
	public static void main(String[] args) {
		//ProfileManager man = new ProfileManager(Snowflake.of("393627109307711489"));
		//Profile f = new Profile(Site.CHESSCOM, "candsbrown");
		
		//man.addProfile(f);
		String t = "candsbrown";
		RequestHandler q = new RequestHandler();
		
		try {
			System.out.println(q.getLichess(t));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//System.out.println(f);
		//man.export();
	}
}
