package blackjack;

import java.util.List;

/**
 *
 * @author Jassel Doong
 * @author Sander Thilo
 */
public class OutputHandler {
	public static void displaySum(Person person) { // Used for both player and dealer
		System.out.println("Sum of " + person.getName() + "'s cards: " + person.getSum());
	}
	
	public static void printNatural(Person person) { // Used for both player and dealer
		System.out.println(person.getName() + " has a natural!");
	}
	
	public static void displayHand(Person person) { // Used for both player and dealer
		System.out.println("\n" + person);
		displaySum(person);
	}
    
    public static void displayDealerVisibleCard(Dealer dealer) {
        System.out.println("Dealer's visible  card: " + dealer.getHand().getCards().get(0));
		int dealerFirstCardValue = dealer.getHand().getCards().get(0).getValue();
		System.out.println("Value of " + dealer.getName() + "'s visible card: " + dealerFirstCardValue);
	}
    
    public static void displayInitialHands(Player player, Dealer dealer) {
        displayHand(player);
        displayDealerVisibleCard(dealer);
    }
	
	public static void revealHiddenCard(Dealer dealer) {
		System.out.println("\n" + dealer.getName() + "'s hidden card: " + dealer.getHiddenCard());
		displaySum(dealer);
	}
	
	public static void displayPlayerBalance(Player player) {
		System.out.println(player.getName() + "'s balance is now $" + toIntIfPossible(player.getBalance()) + ".");
	}
	
	public static void displayPlayerStatistics(Player player) {
		System.out.println("Your records with us are as follows:");
		System.out.println("Name: " + player.getName());
		System.out.println("Balance: " + player.getBalance());
		System.out.println("Total games played: " + player.getGamesPlayed());
		System.out.println("Total hands played: " + player.getHandsPlayed());
		System.out.println("Total hands won: " + player.getHandsWon());
		System.out.println("");
	}
    
	public static void displayGameStatistics(Player player) {
		List<GameLog> gameLogs = GameDBHandler.getGames(player.getName());
		
		// Check if the gameLogs list is empty
		if (gameLogs.isEmpty()) {
			System.out.println("You have not played any games.");
			System.out.println("");
			return; // Early return if no games are found
		}
		
		// Iterate through each game log
		for (int i = 0; i < gameLogs.size(); i++) {
			GameLog gameLog = gameLogs.get(i);
			System.out.println("Game number: " + (i+1));
			System.out.println("Timestamp: " + gameLog.getTimestamp());
			System.out.println("Starting balance: " + gameLog.getStartingBalance());
			System.out.println("Number of hands played: " + gameLog.getNumHands());
			System.out.println("Number of hands won: " + gameLog.getNumHandsWon());
			System.out.println("Ending balance: " + gameLog.getEndingBalance());
			System.out.println("");
		}
	}
	
	public static void displayHandStatistics(Player player) {
		List<HandLog> handLogs = HandDBHandler.getHands(player.getName());
		
		// Check if the handLogs list is empty
		if (handLogs.isEmpty()) {
			System.out.println("You have not played any hands.");
			System.out.println("");
			return; // Early return if no hands are found
		}
		
		// Iterate through each hand log
		for (int i = 0; i < handLogs.size(); i++) {
			HandLog handLog = handLogs.get(i);
			System.out.println("Hand number: " + (i+1));
			System.out.println("Game timestamp: " + handLog.getGameTimestamp());
			System.out.println("Balance before hand: " + handLog.getBalanceBeforeHand());
			System.out.println("Result: " + handLog.getResult());
			System.out.println("Player's hand: " + handLog.getPlayerHand());
			System.out.println("Dealer's hand: " + handLog.getDealerHand());
			System.out.println("Sum of player's hand: " + handLog.getPlayerHandSum());
			System.out.println("Sum of dealer's hand: " + handLog.getDealerHandSum());
			System.out.println("Player had natural: " + handLog.isPlayerNatural());
			System.out.println("Dealer had natural: " + handLog.isDealerNatural());
			System.out.println("Balance after hand: " + handLog.getBalanceAfterHand());
			System.out.println("");
		}
	}
	
    public static void displayResult(Gamestate gamestate) {
        System.out.println(gamestate.getResultText());
    }
	
	public static Number toIntIfPossible(double value) {
		if (value == (int) value) {
			return (int) value; // Return as int if double value is equivalent to an integer
		} else {
			return value; // Otherwise, return double as it is
		}
	}
}
