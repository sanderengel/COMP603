package blackjack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author sanderengelthilo
 */
public class PlayerDBHandler {
	
	public static void addOrUpdatePlayer(Player player) {
		try (Connection conn = DBHandler.connect()) {
			// Check if player already exists
			String query = "SELECT * FROM Players WHERE Name = ?";
			try (PreparedStatement pstatement = conn.prepareStatement(query)) {
				pstatement.setString(1, player.getName());
				ResultSet rs = pstatement.executeQuery();
				
				if (rs.next()) {
					// Player exists, update info
					updatePlayer(conn, player); //, rs.getInt("GamesPlayed"), rs.getInt("HandsPlayed"), rs.getInt("HandsWon"));
				} else {
					// Player doesn't exist, insert new record
					insertPlayer(conn, player);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private static void insertPlayer(Connection conn, Player player) throws SQLException {
		String insertSQL = "INSERT INTO Players (Name, Balance, GamesPlayed, HandsPlayed, HandsWon) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement pstatement = conn.prepareStatement(insertSQL)) {
			pstatement.setString(1, player.getName());
			pstatement.setDouble(2, player.getBalance());
			pstatement.setInt(3, player.getGamesPlayed()); // Should always be 1
			pstatement.setInt(4, player.getHandsPlayed());
			pstatement.setInt(5, player.getHandsWon());
		}
	}
	
	private static void updatePlayer(Connection conn, Player player) throws SQLException { //, int gamesPlayed, int handsPlayed, int handsWon) throws SQLException {
		String updateSQL = "UPDATE Players SET Balance = ?, GamesPlayed = ?, HandsPlayed = ?, HandsWon = ? WHERE Name = ?";
		try (PreparedStatement pstatement = conn.prepareStatement(updateSQL)) {
			pstatement.setDouble(1, player.getBalance());
			pstatement.setInt(2, player.getGamesPlayed() + 1); // Update games played
			pstatement.setInt(3, player.getHandsPlayed()); // Update hands played
			pstatement.setInt(4, player.getHandsWon()); // Update hands won
			pstatement.setString(5, player.getName());
			pstatement.executeUpdate();
		}
	}
	
	public static Player getPlayer(String name) {
		try (Connection conn = DBHandler.connect()) {
			String query = "SELECT * FROM Players WHERE Name = ?";
			try (PreparedStatement pstatement = conn.prepareStatement(query)) {
				pstatement.setString(1, name);
				ResultSet rs = pstatement.executeQuery();
				
				if (rs.next()) {
					// Return existing player
					return new Player(
							rs.getString("Name"),
							rs.getDouble("Balance"),
							rs.getInt("GamesPlayed"),
							rs.getInt("HandsPlayed"),
							rs.getInt("HandsWon")
					);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
