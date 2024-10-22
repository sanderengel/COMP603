package blackjack;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author jasseldoong
 * @author sanderengelthilo
 */
public class Main {
	
    public static void main(String[] args) throws SQLException {
		
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
			player = new Player(playerName, defaultStartingBalance, 0, 0, 0);
		} else {
			// Player already exists
			System.out.println("Welcome back! Your current balance with us is " + player.getBalance() + ".");
			
			// Ask if player wants to check their overall statistics
			System.out.println("Before we begin, would you like to check your records with us? (Y or N)");
			if (inputHandler.getConfirmation()) {
				OutputHandler.displayPlayerStatistics(player);
				
				// Game statistics
				System.out.println("Would you like to also check records for games played? (Y or N)");
				if (inputHandler.getConfirmation()) {
					OutputHandler.displayGameStatistics(player);
				}
				
				// Hand statistics
				System.out.println("Would you like to also check records for hands played? (Y or N)");
				if (inputHandler.getConfirmation()) {
					OutputHandler.displayHandStatistics(player); // Write function
				}
				
			}
		}
		
		System.out.println("Are you ready to play? (Y or N)");
		// Check if player wants to play
		if (inputHandler.getConfirmation()) {
			
			// Store player's starting balance
			double startingBalance = player.getBalance();
			
			// Increment player's number of games played
			player.incrementGamesPlayed();

			// Create instance of Dealer
			Dealer dealer = new Dealer();

			// Create instance of Blackjack class
			Blackjack blackjack = new Blackjack(player, dealer, inputHandler);

			// Create game log
			GameLog gameLog = new GameLog(playerName, startingBalance);
			GameDBHandler.insertGameTimestamp(gameLog.getTimestamp()); // Insert initial game timestamp to DB

			// Create list to store logs of played hands
			List<HandLog> hands = new ArrayList<>(); 
			
			// Run games continuously
			while (true) {

				// Get player bet
				double bet = inputHandler.getBet(player.getBalance());
				System.out.println("Great, your bet is $" + OutputHandler.toIntIfPossible(bet) + ". Let's begin!");

				// Initialize new handLog
				HandLog handLog = new HandLog(gameLog.getTimestamp(), player.getBalance(), bet);

				// Initialize new gamestate
				Gamestate gamestate = new Gamestate();

				// play hand of blackjack, updating all needed information inside gamestate
				blackjack.playHand(gamestate);

				// Save payout multiplier
				double payoutMultipler = gamestate.getPayoutMultiplier();

				// Update player balance
				player.adjustBalance(bet, payoutMultipler);

				// Update player played and won hands
				player.incrementHandsPlayed();
				player.updateHandsWon(payoutMultipler);

				// Update number of won hands in game log
				gameLog.updateHandsWon(payoutMultipler);

				// Handle hand log
				handLog.fillHandLog(player, dealer, gamestate); // Pass information to hand log
				hands.add(handLog); // Append hand log to list of played hands
				HandDBHandler.insertHand(handLog); // Save hand log to DB

				// Display result and player balance
				OutputHandler.displayResult(gamestate);
				OutputHandler.displayPlayerBalance(player);

				// If enough funds, ask to play again
				if (player.getBalance() > 0) {
					System.out.println("\nWould you like to play again? (Y or N)");
					if (!inputHandler.getConfirmation()) {
						break;
					}
				} else {
					System.out.println("\nSorry, you are out of money. Game over.");
					break;
				}
			}

			// Save hand list and ending balance to game log
			gameLog.setHands(hands);
			gameLog.setEndingBalance(player.getBalance());

			// Save game log to .txt
			gameLog.saveGameLog();

			// Update game log to DB
			// Important this runs after player is added/updated
			GameDBHandler.updateGame(gameLog);
		}
		
		// Update player information in database, even if no games where played
		PlayerDBHandler.addOrUpdatePlayer(player);
    }
}
