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
	
	private static final String DB_URL = "jdbc:derby:BlackjackDB;create=true";
	
	public static Connection connect() throws SQLException {
		return DriverManager.getConnection(DB_URL);
	} 
	
	public static void initializeDB() {
		try (Connection conn = connect(); Statement statement = conn.createStatement()) {
			// Create player table if it doesn't already exist
			String createTableSQL = "CREATE TABLE Players ("
					+ "Name VARCHAR(50) PRIMARY KEY, "
					+ "Balance DOUBLE, "
					+ "GamesPlayed INT, "
					+ "HandsPlayed INT)";
			statement.executeUpdate(createTableSQL);
		} catch (SQLException e) {
			if (!e.getSQLState().equals("X0Y32")) {
				// X0Y32 means the table already exists, so we ignore that error
				e.printStackTrace();
			}
		}
	}
}
