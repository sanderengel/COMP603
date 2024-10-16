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
	
	// Override balance and hands played related methods
	@Override
	public void adjustBalance(double bet, double payoutMultiplier) {
		// Do nothing
	}
	
	@Override
	public double getBalance() {
		return 0.0;
	}
	
	@Override
	public void incrementHandsPlayed() {
		// Do nothing
	}
	
	@Override
	public int getHandsPlayed() {
		return 0;
	}
}
