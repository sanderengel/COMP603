package blackjack;

/**
 *
 * @author jasseldoong
 */
public class Dealer extends Person {
	private Card hiddenCard;

	// Constructor, sets dealer's name to "Dealer"
	public Dealer() {
		super("Dealer");
	}
	
	public void setHiddenCard(Card card) {
		hiddenCard = card;
		addCard(card);
	}
	
	public Card getHiddenCard() {
		return hiddenCard;
	}
	
	// Override balance-related methods as Dealer has no balance
	@Override
	public void adjustBalance(double bet, double payoutMultiplier) {
		// Do nothing
	}
	
	@Override
	public double getBalance() {
		return 0.0;
	}
}
