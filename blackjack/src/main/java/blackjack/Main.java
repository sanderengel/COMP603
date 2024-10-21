package blackjack;

import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author jasseldoong
 * @author sanderengelthilo
 */
public class Main {
	
    public static void main(String[] args) {
		
		// Define default starting balance
		Double defaultStartingBalance = 1000.0;
		
		// Create instance of InputHandler
		InputHandler inputHandler = new InputHandler();
		
		// Connect to DB
		DBHandler.initializeDB();
		
		System.out.println("Welcome to BlackJack!");
		System.out.println("You can quit the game at any time by entering 'Q' or 'QUIT'.\n");
		
		// Get player name
		String playerName = inputHandler.getName();
		
//		// Create instance of Player and Dealer
//		Player player = new Player(playerName, startingBalance);
//		Dealer dealer = new Dealer();

		// Get player if already in database, otherwise create new player
		Player player = PlayerDBHandler.getPlayer(playerName);
		if (player == null) {
			// Player does not exist, initialize new one
			System.out.print("We see this is your first time here! You start with a balance of " + defaultStartingBalance + ".\n");
			player = new Player(playerName, defaultStartingBalance, 1, 0, 0);
		} else {
			// Player already exists
			System.out.println("Welcome back! Your current balance with us is " + player.getBalance() + ".");
			// Ask if player wants to check their statistics
			System.out.println("Before we begin, would you like to check your records with us? (Y or N)");
			if (inputHandler.getConfirmation()) {
				OutputHandler.displayPlayerStatistics(player);
				System.out.println("Would you like to also check records for specific hands? (Y or N)");
				if (inputHandler.getConfirmation()) {
					OutputHandler.displayGameStatistics(player);
				}
			}
		}
		
		// Store player's starting balance
		double startingBalance = player.getBalance();
		
		// Create instance of Dealer
		Dealer dealer = new Dealer();
		
		// Create instance of Blackjack class
		Blackjack blackjack = new Blackjack(player, dealer, inputHandler);
		
		// Create game log
		GameLog gameLog = new GameLog(null, playerName, startingBalance);
		
		// Create list to store logs of played hands
		List<HandLog> hands = new ArrayList<>(); 
		
		System.out.println("Are you ready? (Y or N)");
		
		// Run games continuously
		while (true) {
			// Check if player wants to play (again)
			if (!inputHandler.getConfirmation()) {
				break;
			}
			
			// Get player bet
			double bet = inputHandler.getBet(player.getBalance());
			System.out.println("Great, your bet is $" + OutputHandler.toIntIfPossible(bet) + ". Let's begin!");
			
			// Initialize new handLog
			HandLog handLog = new HandLog(player.getBalance(), bet);
			
			// Initialize new gamestate
			Gamestate gamestate = new Gamestate();
			
			// play hand of blackjack, updating all needed information inside gamestate
			blackjack.playHand(gamestate);

			// Update player balance
			player.adjustBalance(bet, gamestate.getPayoutMultiplier());
			
			// Update player played and won hands
			player.incrementHandsPlayed();
			player.updateHandsWon(gamestate.getPayoutMultiplier());
			
			// Pass information to hand log
			handLog.fillHandLog(player, dealer, gamestate);

			// Append hand log to list of played hands
			hands.add(handLog);
			
			// Display result and player balance
			OutputHandler.displayResult(gamestate);
			OutputHandler.displayPlayerBalance(player);
			
			// If enough funds, ask to play again
			if (player.getBalance() > 0) {
				// Actual confirmation is checked on start of loop again
				System.out.println("\nWould you like to play again? (Y or N)");
			} else {
				System.out.println("\nSorry, you are out of money. Game over.");
				break;
			}
		}
		
		// Save hand list to game log and save game log
		gameLog.setHands(hands);
		gameLog.saveGameLog();
		
		// Update player information in database
		PlayerDBHandler.addOrUpdatePlayer(player);
    }
}
