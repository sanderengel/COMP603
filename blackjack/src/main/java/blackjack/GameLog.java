package blackjack;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sanderengelthilo
 */
public class GameLog {
	private final String timestamp;
	private final String playerName;
	private final double startingBalance;
	private int numHands;
	private int numHandsWon;
	private double endingBalance;
	private List<HandLog> hands;
	
	// Constructor with all parameters
	// Used for DB fetching
	public GameLog(String timestamp, String playerName, double startingBalance, int numHands, int numHandsWon, double endingBalance) {
        this.timestamp = timestamp;
        this.playerName = playerName;
        this.startingBalance = startingBalance;
		this.numHands = numHands;
		this.numHandsWon = numHandsWon;
		this.endingBalance = endingBalance;
    }
	
	// Constructor with only playerName and startingBalance parameters
	// Used to log new games
	public GameLog(String playerName, double startingBalance) {
        this.timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        this.playerName = playerName;
        this.startingBalance = startingBalance;
		this.numHands = 0;        // Initialize to 0 by default
		this.numHandsWon = 0;     // Initialize to 0 by default
		this.endingBalance = 0.0; // Initialize to 0 by default
    }
	
	public void setHands(List<HandLog> hands) {
		this.hands = hands;
		this.numHands = hands.size();
	}
	
	public void setEndingBalance(double endingBalance) {
		this.endingBalance = endingBalance;
	}
	
	public void updateHandsWon(double payoutMultiplier) {
		if (payoutMultiplier > 0) {
			this.numHandsWon++;
		}
	}
	
	public void saveGameLog() {
		// Ensure directory exists
		String targetDir = "target/logs";
		File logDir = new File(targetDir);
		if (!logDir.exists()) {
			logDir.mkdirs(); // Make directory if it doesn't exist already
		}
		
		// Create the log file with timestamp in filename
		String filename = targetDir + "/game_log_" + timestamp + ".json";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try (FileWriter writer = new FileWriter(filename)) {
			gson.toJson(this, writer);
		} catch (IOException e) {
		}
	}
	
	// Getters
	public String getTimestamp() {
		return timestamp;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public double getStartingBalance() {
		return startingBalance;
	}
	
	public int getNumHands() {
		return numHands;
	}
	
	public int getNumHandsWon() {
		return numHandsWon;
	}
	
	public double getEndingBalance() {
		return endingBalance;
	}
 	
	@Override
	public String toString() {
		return "GameLog{" +
				"timestamp='" + timestamp + "\'" +
				", playerName='" + playerName + "\'" +
				", startingBalance=" + startingBalance + "\'" +
				", numberOfHands=" + numHands + "\'" +
				", numberOfHandsWon=" + numHandsWon + "\'" +
                ", hands=" + hands +
                '}';
	}
}
