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
					updatePlayer(conn, player, rs.getInt("GamesPlayed"), rs.getInt("HandsPlayed"));
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
		String insertSQL = "INSERT INTO Players (Name, Balance, GamesPlayed, HandsPlayed) VALUES (?, ?, ?, ?)";
		try (PreparedStatement pstatement = conn.prepareStatement(insertSQL)) {
			pstatement.setString(1, player.getName());
			pstatement.setDouble(2, player.getBalance());
			pstatement.setInt(3, 1); // Games played starts at 1
			pstatement.setInt(4, player.getHandsPlayed());
		}
	}
}
