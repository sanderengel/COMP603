package blackjack;

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
