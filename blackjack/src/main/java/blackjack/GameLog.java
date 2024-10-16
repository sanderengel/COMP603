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
	private final List<HandLog> hands;
	
	public GameLog(String playerName, double startingBalance, List<HandLog> hands) {
		this.timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
		this.playerName = playerName;
		this.startingBalance = startingBalance;
		this.hands = hands;		
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
	
	@Override
	public String toString() {
		return "GameLog{" +
				"timestamp='" + timestamp + "\'" +
				", playerName='" + playerName + "\'" +
				", startingBalance=" + startingBalance +
                ", hands=" + hands +
                '}';
	}
}
