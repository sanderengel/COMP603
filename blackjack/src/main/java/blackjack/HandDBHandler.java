package blackjack;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author sanderengelthilo
 */
public class HandDBHandler {
	
	public static void insertHand(HandLog handLog, String timestamp) throws SQLException {
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
					+ "BalanceAfterHand)"
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			try (PreparedStatement pstatement = conn.prepareStatement(insertSQL)) {
				pstatement.setString(1, timestamp);
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
}
