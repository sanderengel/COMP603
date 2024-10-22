package blackjack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
/**
 *
 * @author sanderengelthilo
 */
public class DBHandler {
	
	private static final String DB_URL = "jdbc:derby:BlackjackDB; create=true"; // Store DB in root dir
	
	public static Connection connect() throws SQLException {
		// System.out.println("Working directory: " + new java.io.File(".").getAbsolutePath());
		Connection conn = DriverManager.getConnection(DB_URL);
		// System.out.println("Connected to database at: " + DB_URL);
		return conn;
		// return DriverManager.getConnection(DB_URL);
	} 
	
	public static void initializeDB() {
		try (Connection conn = connect(); Statement statement = conn.createStatement()) {
			// Create player table if it doesn't already exist
			String createPlayerTableSQL = "CREATE TABLE Player ("
					+ "Name VARCHAR(50) PRIMARY KEY, "
					+ "Balance DOUBLE, "
					+ "GamesPlayed INT, "
					+ "HandsPlayed INT, "
					+ "HandsWon INT)";
			statement.executeUpdate(createPlayerTableSQL);
			// System.out.println("Players table created successfully.");
			
			// Create game table if it doesn't already exist
			String createGameTableSQL = "CREATE TABLE Game ("
					+ "Timestamp CHAR(19) PRIMARY KEY, "
					+ "PlayerName VARCHAR(50), "
					+ "StartingBalance DOUBLE, "
					+ "NumberHands INT, "
					+ "NumberHandsWon INT, "
					+ "EndingBalance DOUBLE, "
					+ "FOREIGN KEY (Playername) REFERENCES Player(Name))";
			statement.executeUpdate(createGameTableSQL);
			// System.out.println("Game table created successfully.");
			
			// Create Hand table if it doesn't already exist
			String createHandTableSQL = "CREATE TABLE Hand ("
					+ "HandID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY, " // Unique ID for each hand
					+ "GameTimestamp CHAR(19), " // Foreign key to reference the Game table
					+ "BalanceBeforeHand DOUBLE, "
					+ "AmountBetted DOUBLE, "
					+ "Result VARCHAR(100), "
					+ "PlayerHand VARCHAR(255), " // Store as text, format like "[3 of Spades, K of Hearts]"
					+ "DealerHand VARCHAR(255), "
					+ "PlayerHandSum INT, "
					+ "DealerHandSum INT, "
					+ "PlayerNatural BOOLEAN, "
					+ "DealerNatural BOOLEAN, "
					+ "BalanceAfterHand DOUBLE, "
					+ "FOREIGN KEY (GameTimestamp) REFERENCES Game(Timestamp)"
					+ ")";
			statement.executeUpdate(createHandTableSQL);
			// System.out.println("Hand table created successfully.");
			
		} catch (SQLException e) {
			if (e.getSQLState().equals("X0Y32")) {
				// X0Y32 means the table already exists
				// System.out.println("Players table already exists");
			} else {
				// Print the full stack trace for unexpected errors
				e.printStackTrace();
			}
		}
	}
}
