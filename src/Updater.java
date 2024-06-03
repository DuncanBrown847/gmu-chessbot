import java.util.HashMap;
import discord4j.common.util.Snowflake;

public class Updater extends Thread {
	private HashMap<Snowflake, DualProfile> profiles;
	private RequestHandler handler;
	
	private int timeIntervalMinutes;
	
	private int count;
	
	public Updater (HashMap<Snowflake, DualProfile> profiles, RequestHandler handler, int timeIntervalMinutes) {
		this.profiles = profiles;
		this.handler = handler;
		this.timeIntervalMinutes = timeIntervalMinutes;
		
		this.count = 0;
		
		this.setDaemon(true);
		this.setName("Updater");
		
		System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG><THREAD:"+ this.getName() + "> Thread initialized with " + this.timeIntervalMinutes + "-minute interval");
	}
	
	public Updater (HashMap<Snowflake, DualProfile> profiles, RequestHandler handler) {
		this(profiles, handler, 5);
	}
	
	@Override
	public void run() {
		System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG><THREAD:"+ this.getName() + "> Starting thread");
		
		while(true) {
			try {
				Thread.sleep(1000 * 60 * timeIntervalMinutes); //timeIntervalMinutes minutes, default 5min
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG><THREAD:"+ this.getName() +"> Starting update cycle (" + count + ")...");
			for (DualProfile prof : profiles.values()) {
				System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG><THREAD:"+ this.getName() +"> Updating " + prof.getId() + "...");
				
				//throttling -_-
				try {
					Thread.sleep(1000); //1 second
				} catch (InterruptedException e) {
					e.printStackTrace(); //shouldn't happen
				}
				
				//if 429...
				if (!prof.update(handler)) {
					System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG><THREAD:"+ this.getName() +"> Throttling...");
					try {
						Thread.sleep(1000 * 60); //wait 1 minute...
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					prof.update(handler); //try again
				}
				prof.export();
			}
			System.out.println("<" + java.time.LocalTime.now().toString().substring(0, 8) +"><LOG><THREAD:"+ this.getName() +">Completed update cycle (" + count + ").");
		}
	}
}
