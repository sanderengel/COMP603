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
        
        public Card getVisibleCard() {
        // Ensure the hand is not null and has at least one card
        if (hand != null && !hand.getCards().isEmpty()) {
            return hand.getCards().get(0); // Assuming the first card is the visible one
            }
            return null; // Return null if there are no cards
        }

        
        public void revealHiddenCard() {
            if(hiddenCard != null){
                addCard(hiddenCard);
                hiddenCard = null;
            }
        }
	
	// Override player related methods
	@Override
	public void adjustBalance(double bet, double payoutMultiplier) {
		// Do nothing
	}
	
	@Override
	public double getBalance() {
		return 0.0;
	}
	
	@Override
	public boolean outOfMoney() {
		return true; // Dealer never has money
	}
	
	@Override
	public void incrementGamesPlayed() {
		// Do nothing
	}
	
	@Override
	public int getGamesPlayed() {
		return 0;
	}
	
	@Override
	public void incrementHandsPlayed() {
		// Do nothing
	}
	
	@Override
	public int getHandsPlayed() {
		return 0;
	}
	
	@Override
	public void updateHandsWon(double payoutMultiplier) {
		// Do nothing
	}
	
	@Override
	public int getHandsWon() {
		return 0;
	}
}
