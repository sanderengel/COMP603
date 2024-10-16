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
		
		Double startingBalance = 1000.0;
		
		System.out.println("Welcome to BlackJack!");
		System.out.println("You start with a balance of $" + OutputHandler.toIntIfPossible(startingBalance) + ".");
		System.out.println("You can quit the game at any time by entering 'Q' or 'QUIT'.\n");
		
		// Create instance of InputHandler
		InputHandler inputHandler = new InputHandler();
		
		// Get player name
		String playerName = inputHandler.getName();
		
		// Create instance of Player and Dealer
		Player player = new Player(playerName, startingBalance);
		Dealer dealer = new Dealer();
		
		// Create instance of Blackjack class
		Blackjack blackjack = new Blackjack(player, dealer, inputHandler);
		
		// Create list to store logs of played hands
		List<HandLog> handsPlayed = new ArrayList<>(); 
		
		System.out.println("Nice to have you, " + player.getName() + ". Are you ready? (Y or N)");
		
		// Run games continuously
		while (true) {
			// Check if player wants to play (again)
			if (!inputHandler.getStartConfirm()) {
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
			
			// Incremenet player played hands
			player.incrementHandsPlayed();
			
			// Pass information to hand log
			handLog.fillHandLog(player, dealer, gamestate);

			// Append hand log to list of played hands
			handsPlayed.add(handLog);
			
			// Display result and player balance
			OutputHandler.displayResult(gamestate);
			OutputHandler.displayPlayerBalance(player);
			
			// If enough funds, ask to play again
			if (player.getBalance() > 0) {
				System.out.println("\nWould you like to play again? (Y or N)");
			} else {
				System.out.println("\nSorry, you are out of money. Game over.");
				break;
			}
		}
		
		// Log and store game to file
		GameLog gameLog = new GameLog(playerName, startingBalance, handsPlayed);
		gameLog.saveGameLog();
    }
}
