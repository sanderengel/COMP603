package blackjack;

/**
 *
 * @author jasseldoong
 * @author sanderengelthilo
 */
public class Main {
    public static void main(String[] args) {
		
		Double startingBalance = 1000.0;
		
		System.out.println("Welcome to BlackJack!");
		System.out.println("You start with a balance of $" + startingBalance + ".\n");
		
		// Create instances of InputHandler and OutputHandler
		InputHandler inputHandler = new InputHandler();
		OutputHandler outputHandler = new OutputHandler();
		
		// Get player name
		String playerName = inputHandler.getName();
		
		// Create instance of Player
		Player player = new Player(playerName, startingBalance);
		
		// Create instance of Blackjack class
		Blackjack blackjack = new Blackjack(player, inputHandler, outputHandler);
		
		System.out.println("Nice to have you, " + player.getName() + ". Are you ready? (Y or N)");
		
		// Run games until player runs out of balance
		while (player.getBalance() > 0) {
			// Check if player wants to play (again)
			if (!inputHandler.getStartConfirm()) {
				break;
			}
			
			// Get player bet
			double bet = inputHandler.getBet(player.getBalance());
			System.out.println("Great, your bet is $" + bet + ". Let's begin!");
			
			// Play blackjack and get payout multiplier
			double payoutMultiplier = blackjack.playGame();
			
			// Update and display player balance
			player.adjustBalance(bet, payoutMultiplier);
			outputHandler.displayPlayerBalance(player);
			
			// Ask to play again
			System.out.println("\nWould you like to play again? (Y or N)");
		}
    }
}
