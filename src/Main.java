
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;
import reactor.core.publisher.Flux;

public final class Main {
	/**
	 * Main.
	 * @param args Not used.
	 */
	private static final String REGISTER_HELP_MESSAGE = "```!register usage:\n-------------------------------\n!register [chess.com username] [lichess username]\n!register [chess.com/lichess username]\t<-\t(if your username is identical on both sites)\n!register chess.com [chess.com username]\n!register lichess [lichess username]```";
	private static final String UNREGISTER_HELP_MESSAGE = "```!unregister usage:\n-------------------------------\n!unregister\n!unregister [site]\t<-\t\'chess.com\' or \'lichess\', will unregister you only from the site you specify```";
	private static final String PROFILE_HELP_MESSAGE = "```!profile usage:\n-------------------------------\n!profile\t<-\tdisplays your profile\n!profile [mentions]\t<-\tdisplays all profiles of mentioned users, in no particular order\n\tyou can mention any number of users for [mentions]```";
	
	private static final String TOKEN = ""; //put discord secret token here.
	
	/*
	private static void addDual(RequestHandler requestHandler, Snowflake authorID, String chessDotComUsername, String lichessUsername, HashMap<Snowflake, DualProfile> profiles) throws RequestHandlerException {
		addChessDotCom(requestHandler, authorID, chessDotComUsername, profiles);
		addLichess(requestHandler, authorID, lichessUsername, profiles);
	}
	*/
	/**
	 * Creates and adds a ChessDotComProfile to the profiles HashMap, creating a DualProfile if necessary.
	 * @param channel - MessageChannel
	 * @param requestHandler
	 * @param authorID
	 * @param profiles
	 */
	/*
	private static void addChessDotCom(RequestHandler requestHandler, Snowflake authorID, String username, HashMap<Snowflake, DualProfile> profiles) throws RequestHandlerException {
		String chessDotComJSON;
		chessDotComJSON = requestHandler.getChessDotCom(username);
		ChessDotComProfile newChessDotComProfile = ChessDotComProfile.fromJSON(chessDotComJSON, username);
		DualProfile newProfile = new DualProfile(authorID, newChessDotComProfile, null);
			
		if (profiles.containsKey(authorID))
			profiles.get(authorID).setChessDotCom(newChessDotComProfile);
		else
			profiles.put(authorID, newProfile);
		newProfile.export();
			
		//LOG
		//System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Successfully added chess.com profile [" + authorID + "]");
		//channel.createMessage("Registration successful!").block();
		//System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> ERROR: " + e);
			
		//channel.createMessage("No such username on chess.com!").block();
	}
	
	/**
	 * Creates and adds a LichessProfile to the profiles HashMap, creating a DualProfile if necessary.
	 * @param channel - MessageChannel
	 * @param requestHandler
	 * @param authorID
	 * @param profiles
	 */
	/*
	private static void addLichess(RequestHandler requestHandler, Snowflake authorID, String username, HashMap<Snowflake, DualProfile> profiles) throws RequestHandlerException {
		String lichessJSON;
		lichessJSON = requestHandler.getLichess(username);
		LichessProfile newLichessProfile = LichessProfile.fromJSON(lichessJSON);
		DualProfile newProfile = new DualProfile(authorID, null, newLichessProfile);
		
		if (profiles.containsKey(authorID))
			profiles.get(authorID).setLichess(newLichessProfile);
		else
			profiles.put(authorID, newProfile);
		newProfile.export();
			
		//LOG
		//System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Successfully added chess.com profile [" + authorID + "]");
		//channel.createMessage("Registration successful!").block();
		//System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> ERROR: " + e);
			
		//channel.createMessage("No such username on chess.com!").block();
	}
	
	/**
	 * Prints Creates an embed in channel.
	 * @param channel - MessageChannel to embed in.
	 * @param user - User to associate embed with.
	 * @param profile - A DualProfile to embed.
	 */
	/*
	private static void embedProfile(MessageChannel channel, User user, DualProfile profile) {
		if (profile.getChessDotCom() != null)
			embedChessDotCom(channel, user, profile.getChessDotCom());
		
		if (profile.getLichess() != null)
			embedLichess(channel, user, profile.getLichess());
	}
	
	/**
	 * Creates an embed in channel.
	 * @param channel - MessageChannel to embed in.
	 * @param user - User to associate embed with.
	 * @param id - A Snowflake. Used for footer.
	 * @param chessDotCom - A ChessDotComProfile to embed.
	 */
	/*
	private static void embedChessDotCom(MessageChannel channel, User user, ChessDotComProfile chessDotCom) {
		channel.createEmbed(spec -> 
		spec.setColor(Color.SEA_GREEN)
			.setTitle(chessDotCom.getUsername())
			.setUrl("https://www.chess.com/member/" + chessDotCom.getUsername())
			.setDescription(user.getTag() + "'s chess.com ratings")
			//.setImage(ANY_URL) aint work
			.addField("Bullet", ""+chessDotCom.getBullet(), true)
			.addField("Blitz", ""+chessDotCom.getBlitz(), true)
			.addField("Rapid", ""+chessDotCom.getRapid(), true)
			.setFooter(user.getId().toString(), null)
			.setTimestamp(Instant.now())
		).block();
	}
	
	/**
	 * Creates an embed in channel.
	 * @param channel - MessageChannel to embed in.
	 * @param user - User to associate embed with.
	 * @param id - A Snowflake. Used for footer.
	 * @param lichess - a LichessProfile to embed.
	 */
	/*
	private static void embedLichess(MessageChannel channel, User user, LichessProfile lichess) {
		channel.createEmbed(spec -> 
		spec.setColor(Color.BROWN)
			.setTitle(lichess.getUsername())
			.setUrl("https://lichess.org/@/" + lichess.getUsername())
			.setDescription(user.getTag() + "'s lichess ratings")
			//.setImage(ANY_URL)
			.addField("Bullet", ""+lichess.getBullet(), true)
			.addField("Blitz", ""+lichess.getBlitz(), true)
			.addField("Rapid", ""+lichess.getRapid(), true)
			.addField("Classical", ""+lichess.getClassical(), true)
			.setFooter(user.getId().toString(), null)
			.setTimestamp(Instant.now())
		).block();
	}
	
	/**
	 * Main.
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		//request handler initialization
		RequestHandler requestHandler = new RequestHandler();
		
		//discord ID to chess profile hashmap
		SuperProfileManager superProfiles = new SuperProfileManager();
		
		//loads profiles
		superProfiles.importAll();
		
		//starts updater thread //deprecated
		//Updater u = new Updater(profiles, requestHandler);
		//u.start();
		
		
		//owners array; owners have ultimate authority for important commands
		ArrayList<Snowflake> owners = new ArrayList<Snowflake>();
		//add like this: owners.add(Snowflake.of(<discord snowflake id>));
		
		//creates instance of bot and "logs in"
		final DiscordClient client = DiscordClient.create(TOKEN);
		final GatewayDiscordClient gateway = client.login().block();
		
		//message in chat event
		gateway.on(MessageCreateEvent.class).subscribe(event -> {
			//retrieves last message
			final Message message = event.getMessage();
			
			//message_args[0] is the command, [1] is first arg, [2] is second, etc...
			String[] messageArgs = message.getContent().split(" ");
			
			//if message is not an embed and is a command...
			if (messageArgs[0].length() > 0 && messageArgs[0].charAt(0) == '!') {
				final MessageChannel channel = message.getChannel().block();
				/*
				//register command
				if (messageArgs[0].equals("!register")) {
					if (messageArgs.length < 2)
						channel.createMessage("No arguments!\n" + REGISTER_HELP_MESSAGE).block();
					
					else if (messageArgs.length == 2) {
						try {
							Snowflake authorID = message.getAuthor().get().getId();
							//addDual(requestHandler, authorID, messageArgs[1], messageArgs[1], profiles);
							
							//LOG
							System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Successfully added dual profile [" + authorID + "]");
							channel.createMessage("Registration successful!").block();
						} catch (RequestHandlerException e) {
							System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> ERROR: " + e);
							channel.createMessage("Invalid username!\nThis may have happened because your usernames for chess.com and lichess are different.").block();
						}
					}
					
					else if (messageArgs.length == 3) {
						if (messageArgs[1].equals("chess.com")) {
							try {
								Snowflake authorID = message.getAuthor().get().getId();
								//addChessDotCom(requestHandler, authorID, messageArgs[2], profiles);
								
								//LOG
								System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Successfully added chess.com profile [" + authorID + "]");
								channel.createMessage("Registration successful!").block();
							} catch (RequestHandlerException e) {
								System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> ERROR: " + e);
								channel.createMessage("No such username on chess.com!").block();
							}
						}
						else if (messageArgs[1].equals("lichess")) {
							try {
								Snowflake authorID = message.getAuthor().get().getId();
								//(requestHandler, authorID, messageArgs[2], profiles);
								
								//LOG
								System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Successfully added lichess profile [" + authorID + "]");
								channel.createMessage("Registration successful!").block();
							} catch (RequestHandlerException e) {
								System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> ERROR: " + e);
								channel.createMessage("No such username on lichess!").block();
							}
						}
						//two usernames provided
						else {
							try {
								Snowflake authorID = message.getAuthor().get().getId();
								//addDual(requestHandler, authorID, messageArgs[1], messageArgs[2], profiles);
								
								//LOG
								System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Successfully added dual profile [" + authorID + "]");
								channel.createMessage("Registration successful!").block();
							} catch (RequestHandlerException e) {
								System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> ERROR: " + e);
								channel.createMessage("One or more username(s) are invalid!").block();
							}
						}
					}
					else if (messageArgs.length > 3) 
						channel.createMessage("Too many arguments!").block();
				}
				
				//profile command
				else if (messageArgs[0].equals("!profile")) {
					Snowflake authorID = message.getAuthor().get().getId();
					
					if (messageArgs.length == 1) {
						if (profiles.containsKey(authorID)) {
							//embedProfile(channel, message.getAuthor().get(), profiles.get(authorID));
						}
						else
							channel.createMessage("It seems you are not registered yet!").block();
					}
					
					else if (messageArgs.length > 1) {
						Set<Snowflake> userIDs = message.getUserMentionIds();
						Flux<User> users = message.getUserMentions(); //ugh
						
						int i = 0;
						for (Snowflake id : userIDs) {
							User mention = users.elementAt(i++).block();
							
							if (profiles.containsKey(id)) {
								//embedProfile(channel, mention, profiles.get(id));
							}
							
							else
								channel.createMessage("No profile found for " + mention.getTag() + "!").block();
						}
					}
					
					else
						channel.createMessage("Too many args!").block();
				}
				
				//unregister command
				else if (messageArgs[0].equals("!unregister")) {
					Snowflake authorID = message.getAuthor().get().getId();
					
					if (!profiles.containsKey(authorID))
						channel.createMessage("It seems you are not registered yet!").block();
					
					else if (messageArgs.length == 1) {
						profiles.remove(authorID).deleteAll(); //removes and deletes
							
						System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Successfully removed profile [" + authorID + "]");
						channel.createMessage("Unregistration successful!").block();
					}
					
					else if (messageArgs.length == 2) {
						if (messageArgs[1].equals("chess.com")) {
							DualProfile profile = profiles.get(authorID);
							
							if (profile != null) {
								profile.deleteChessDotCom();
							
								if (profile.isEmpty())
									profiles.remove(authorID).deleteAll(); //removes and deletes
								
								channel.createMessage("Chess.com unregistration successful!").block();
							}
							else
								channel.createMessage("You have not registered a chess.com account!").block();
						}
						
						else if (messageArgs[1].equals("lichess")) {
							DualProfile profile = profiles.get(authorID);
							
							if (profile != null) {
								profile.deleteLichess();
							
								if (profile.isEmpty())
									profiles.remove(authorID).deleteAll(); //removes and deletes
								
								channel.createMessage("Lichess unregistration successful!").block();
							}
							else
								channel.createMessage("You have not registered a lichess account!").block();
						}

						else
							channel.createMessage("Unknown argument \'" + messageArgs[1] + "\'!").block();
					}
					else
						channel.createMessage("Too many args!").block();
				}
				
				//help command
				else if (messageArgs[0].equals("!help")) {
					channel.createMessage(UNREGISTER_HELP_MESSAGE + "\n" + REGISTER_HELP_MESSAGE + "\n" + PROFILE_HELP_MESSAGE).block();
				}
				
				//shutdown command
				else if (messageArgs[0].equals("!shutdown")) {
					if (owners.contains(message.getAuthor().get().getId())) {
						channel.createMessage("Bye!").block();
						System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Shutting down...");
						gateway.logout().block();
					}
					else
						channel.createMessage("Nice try!").block();
				}
				
				else {
					channel.createMessage("Unknown command \'" + messageArgs[0] + "\'!").block();
				}
				*/
			}
		});
		gateway.onDisconnect().block();
		System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG> Shutdown complete");
	}
	
	
}
