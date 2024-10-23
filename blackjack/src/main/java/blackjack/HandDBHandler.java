package blackjack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HandDBHandler {
	
	public static void insertHand(HandLog handLog) throws SQLException {
		try (Connection conn = DBHandler.connect()) {
			String insertSQL = "INSERT INTO Hand ("
					+ "GameTimestamp, "
					+ "BalanceBeforeHand, "
					+ "AmountBetted, "
					+ "Result, "
					+ "PlayerHand, "
					+ "DealerHand, "
					+ "PlayerHandSum, "
					+ "DealerHandSum, "
					+ "PlayerNatural, "
					+ "DealerNatural, "
					+ "BalanceAfterHand) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstatement = conn.prepareStatement(insertSQL)) {
				pstatement.setString(1, handLog.getGameTimestamp());
				pstatement.setDouble(2, handLog.getBalanceBeforeHand());
				pstatement.setDouble(3, handLog.getAmountBetted());
				pstatement.setString(4, handLog.getResult());
				pstatement.setString(5, handLog.getPlayerHand());
				pstatement.setString(6, handLog.getDealerHand());
				pstatement.setInt(7, handLog.getPlayerHandSum());
				pstatement.setInt(8, handLog.getDealerHandSum());
				pstatement.setBoolean(9, handLog.isPlayerNatural());
				pstatement.setBoolean(10, handLog.isDealerNatural());
				pstatement.setDouble(11, handLog.getBalanceAfterHand());
				pstatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<HandLog> getHands(String playerName) {
		List<HandLog> handLogs = new ArrayList<>();
		String selectSQL = "SELECT h.* "
				+ "FROM Hand h "
				+ "JOIN Game g ON h.GameTimestamp = g.Timestamp "
				+ "JOIN Player p ON g.Playername = p.Name "
				+ "WHERE p.Name = ?";
		
		try (Connection conn = DBHandler.connect();
			PreparedStatement pstatement = conn.prepareStatement(selectSQL)) {
			pstatement.setString(1, playerName);
			ResultSet rs = pstatement.executeQuery();
			
			// Loop through the result set and create HandLog objects
			while (rs.next()) {
				String gameTimestamp = rs.getString("GameTimestamp");
				double balanceBeforeHand = rs.getDouble("BalanceBeforeHand");
				double amountBetted = rs.getDouble("AmountBetted");
				String result = rs.getString("Result");
				String playerHand = rs.getString("PlayerHand");
				String dealerHand = rs.getString("DealerHand");
				int playerHandSum = rs.getInt("PlayerHandSum");
				int dealerHandSum = rs.getInt("DealerHandSum");
				boolean playerNatural = rs.getBoolean("PlayerNatural");
				boolean dealerNatural = rs.getBoolean("DealerNatural");
				double balanceAfterHand = rs.getDouble("BalanceAfterHand");
				
				// Create a new HandLog object and add it to the list
				HandLog handLog = new HandLog(gameTimestamp, balanceBeforeHand, amountBetted, result, playerHand, dealerHand, playerHandSum, dealerHandSum, playerNatural, dealerNatural, balanceAfterHand);
				handLogs.add(handLog);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return handLogs;
	}
}
