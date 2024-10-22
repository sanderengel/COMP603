package blackjack;

import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sanderengelthilo
 */
public class ModelGUI {
	private Player player;
	private Blackjack blackjack;
	private boolean isNewPlayer;
	private final Dealer dealer;
	private final double defaultStartingBalance;
	
	public ModelGUI() {
		dealer = new Dealer();
		defaultStartingBalance = 1000.0;
		
		// Connect to DB
		DBHandler.initializeDB();
//		// Check if the player already exists in the database
//		player = PlayerDBHandler.getPlayer(playerName);
//		
//		if (player == null) {
//			// Player does not exist, initialize a new one
//			// System.out.println("We see this is your first time here! You start with a balance of " + defaultStartingBalance + ".");
//            player = new Player(playerName, defaultStartingBalance, 0, 0, 0);
//            isNewPlayer = true;
//		} else {
//			// Player already exists
//			// System.out.println("Welcome back! Your current balance with us is " + player.getBalance() + ".");
//            isNewPlayer = false;
//		}
//		
//		// Initialize dealer and blackjack game
//		dealer = new Dealer();
//		blackjack = new Blackjack(player, dealer, new InputHandler());
		
	}
	
	public boolean checkIfNewPlayer(String playerName) {
		// Check if the player already exists in the database
        player = PlayerDBHandler.getPlayer(playerName);

        if (player == null) {
            // New player
            player = new Player(playerName, defaultStartingBalance, 0, 0, 0);
			PlayerDBHandler.addOrUpdatePlayer(player); // Add new player to DB
            isNewPlayer = true;
        } else {
            // Existing player
            isNewPlayer = false;
        }
        
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
