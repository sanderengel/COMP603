package blackjack;

import java.sql.Connection;
import java.sql.DriverManager;
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
		System.out.println("Connected to database at: " + DB_URL);
		return conn;
		// return DriverManager.getConnection(DB_URL);
	} 
	
	public static void initializeDB() {
		try (Connection conn = connect(); Statement statement = conn.createStatement()) {
			// Create player table if it doesn't already exist
			String createTableSQL = "CREATE TABLE Players ("
					+ "Name VARCHAR(50) PRIMARY KEY, "
					+ "Balance DOUBLE, "
					+ "GamesPlayed INT, "
					+ "HandsPlayed INT, "
					+ "HandsWon INT)";
			statement.executeUpdate(createTableSQL);
			System.out.println("Players table created successfully.");
		} catch (SQLException e) {
			if (e.getSQLState().equals("X0Y32")) {
				// X0Y32 means the table already exists
				System.out.println("Players table already exists");
			} else {
				// Print the full stack trace for unexpected errors
				e.printStackTrace();
			}
		}
	}
}
