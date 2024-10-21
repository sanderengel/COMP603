package blackjack;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sanderengelthilo
 */
public class GameDBHandler {
	
	public static void insertGame(GameLog gameLog) throws SQLException {
		try (Connection conn = DBHandler.connect()) {
			String insertSQL = "INSERT INTO Game (Timestamp, Playername, Startingbalance) VALUES (?, ?, ?)";
			try (PreparedStatement pstatement = conn.prepareStatement(insertSQL)) {
				pstatement.setString(1, gameLog.getTimestamp());
				pstatement.setString(2, gameLog.getPlayerName());
				pstatement.setDouble(3, gameLog.getStartingBalance());
				pstatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<GameLog> getGames(String playerName) {
		List<GameLog> gameLogs = new ArrayList<>();
        String selectSQL = "SELECT * FROM Game WHERE PlayerName = ?";

        try (Connection conn = DBHandler.connect();
            PreparedStatement pstatement = conn.prepareStatement(selectSQL)) {
				pstatement.setString(1, playerName);
				ResultSet rs = pstatement.executeQuery();

				// Loop through the result set and create GameLog objects
				while (rs.next()) {
					String timestamp = rs.getString("Timestamp");
					String player = rs.getString("PlayerName");
					double startingBalance = rs.getDouble("StartingBalance");

					// Create a new GameLog object and add it to the list
					GameLog gameLog = new GameLog(timestamp, player, startingBalance);
					gameLogs.add(gameLog);
				}
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameLogs;
	}
}
