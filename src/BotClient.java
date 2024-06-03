import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import java.util.Map.Entry;

public class BotClient {
	private SuperProfileManager superProfiles;
	private RequestHandler requestHandler; 
	
	private static final String TOKEN = ""; //put discord secret token here.
	
	private DiscordClient client;
	private GatewayDiscordClient gateway;
	private MessageChannel channel;
	
	private ArrayList<Snowflake> owners;
	
	private char prefix;
	
	public BotClient() {
		superProfiles = new SuperProfileManager();
		requestHandler = new RequestHandler();
		owners = new ArrayList<Snowflake>();
		prefix = '!';
	}
	
	public void debug() {
		System.out.println(superProfiles);
	}
	
	public void start() {
		superProfiles.importAll();
		client = DiscordClient.create(TOKEN);
		gateway = client.login().block();
		
		//owners.add(Snowflake.of("")); //main
		//owners.add(Snowflake.of("")); //alt
	}
	
	private String helpRegister() {
		return prefix + "register expected use:```!register [site] [username]```Try " + prefix + "help for more info";
	}
	
	private void register(Snowflake id, Site site, String username) {
		try {
			requestHandler.getProfile(site, username);
		} catch (UserNotFoundException e) {
			channel.createMessage("Could not find this user! Did you misspell your username?").block();
			e.printStackTrace();
			return;
		} catch (Exception e) {
			channel.createMessage("Oops, some sort of error occured! Please try again...").block();
			e.printStackTrace();
			return;
		}
		
		superProfiles.addProfile(id, site, username);
		channel.createMessage("Profile registered successfully!").block();
	}
	
	private int[] parseChessDotCom(String jsonString) {
		int[] result = new int[4];
		JSONObject jsonObject = null;
		
		try {
			jsonObject = (JSONObject)(new JSONParser().parse(jsonString));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		String s = (String)((JSONObject)((JSONObject)jsonObject.get("chess_blitz")).get("last")).get("rating");
		System.out.println(s);
		return new int[]{1, 2, 3, 4};
	}
	
	public void subscribe() {
		gateway.on(MessageCreateEvent.class).subscribe(event -> { //wait for events in active discords
			final Message message = event.getMessage(); //get message event
			channel = message.getChannel().block(); //for sending response
			String[] args = message.getContent().toLowerCase().split(" "); //message_args[0] is the command, [1] is first argument, [2] is second, etc... (lower case)
			
			if (args.length > 0 && args[0].length() > 0 && args[0].charAt(0) == prefix) { //if 1st argument begins with prefix...
				args[0] = args[0].substring(1); //removes prefix from arg
				
				if (args[0].equals("register")) { //register
					if (args.length != 3) {
						channel.createMessage("```register expected use:\n\t!register [site] [username]\n\nTry " + prefix + "help for more info```").block(); //small usage message
					}
					else {
						if (args[1].equals("chess.com")) {
							register(message.getAuthor().get().getId(), Site.CHESSDOTCOM, args[2]);
						}
						else if (args[1].equals("lichess")) {
							register(message.getAuthor().get().getId(), Site.LICHESS, args[2]);
						}
						else {
							channel.createMessage("The site you entered is not recognized!").block();
						}
					}
				}
				
				else if (args[0].equals("profile")) {
					for (Entry<Site, String> entry : superProfiles.get(message.getAuthor().get().getId()).entrySet()) {
						
						String response = null;
						try {
							response = requestHandler.getProfile(entry);
						} catch (Exception e) {
							e.printStackTrace();
						}

						parseChessDotCom(response);
					}
				}
				
				else if (owners.contains(message.getAuthor().get().getId())) { //owner commands
					if (args[0].equals("debug")) {
						debug();
					}
					else if (args[0].equals("op")) { //op
						
					}
					else if (args[0].equals("shutdown")) { //shutdown
						channel.createMessage("Bye!").block();
						gateway.logout().block();
					}
				}
			}
		
		});
		gateway.onDisconnect().block();
	}
	
	public void stop() {
		superProfiles.exportAll();
	}
	
	public static void main(String[] args) {
		BotClient bc = new BotClient();
		bc.start();
		bc.subscribe();
		bc.stop();
	}
}
