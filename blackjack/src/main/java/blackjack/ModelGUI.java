package blackjack;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sanderengelthilo
 */
public class ModelGUI {
	private Player player;
	private final Blackjack blackjack;
	private final Dealer dealer;
	private final boolean isNewPlayer;
	
	public ModelGUI(String playerName, double defaultStartingBalance) {
		// Check if the player already exists in the database
		player = PlayerDBHandler.getPlayer(playerName);
		
		if (player == null) {
			// Player does not exist, initialize a new one
			// System.out.println("We see this is your first time here! You start with a balance of " + defaultStartingBalance + ".");
            player = new Player(playerName, defaultStartingBalance, 0, 0, 0);
            isNewPlayer = true;
		} else {
			// Player already exists
			// System.out.println("Welcome back! Your current balance with us is " + player.getBalance() + ".");
            isNewPlayer = false;
		}
		
		// Initialize dealer and blackjack game
		dealer = new Dealer();
		blackjack = new Blackjack(player, dealer, new InputHandler());
	}
	
	// Method to check if player is new
	public boolean isNewPlayer() {
		return isNewPlayer;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Blackjack getBlackjack() {
		return blackjack;
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
}
