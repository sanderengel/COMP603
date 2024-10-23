package blackjack;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class GameDBHandler {
		
	// Method to insert only the timestamp
    public static void insertGameTimestamp(String timestamp) throws SQLException {
        try (Connection conn = DBHandler.connect()) {
            String insertSQL = "INSERT INTO Game (Timestamp) VALUES (?)";
            try (PreparedStatement pstatement = conn.prepareStatement(insertSQL)) {
                pstatement.setString(1, timestamp);
                pstatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update the game with the rest of the information
    public static void updateGame(GameLog gameLog) throws SQLException {
        try (Connection conn = DBHandler.connect()) {
            String updateSQL = "UPDATE Game SET "
                    + "PlayerName = ?, "
                    + "StartingBalance = ?, "
                    + "NumberHands = ?, "
                    + "NumberHandsWon = ?, "
                    + "EndingBalance = ? "
                    + "WHERE Timestamp = ?";
            try (PreparedStatement pstatement = conn.prepareStatement(updateSQL)) {
                pstatement.setString(1, gameLog.getPlayerName());
                pstatement.setDouble(2, gameLog.getStartingBalance());
                pstatement.setInt(3, gameLog.getNumHands());
                pstatement.setInt(4, gameLog.getNumHandsWon());
                pstatement.setDouble(5, gameLog.getEndingBalance());
                pstatement.setString(6, gameLog.getTimestamp()); // Use the timestamp to identify the game
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
                double startingBalance = rs.getDouble("StartingBalance");
				int numHands = rs.getInt("Numberhands");
				int numHandsWon = rs.getInt("Numberhandswon");
				double endingBalance = rs.getDouble("Endingbalance");

                // Create a new GameLog object and add it to the list
                GameLog gameLog = new GameLog(timestamp, playerName, startingBalance, numHands, numHandsWon, endingBalance);
                gameLogs.add(gameLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameLogs;
	}
}
