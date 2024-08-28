package blackjack;

/**
 *
 * @author jasseldoong
 */
public class Dealer extends Player {
    private Card hiddenCard;
    
    public Dealer(String name) {
        super(name, 0.0); // Dealer has no balance
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
    
//    public void makeMove(Deck deck, OutputHandler outputHandler) {
//        while (getSum() < 17) {
//            Card newCard = deck.dealCard();
//            addCard(newCard);
//        }
//        outputHandler.revealHiddenCard(hiddenCard);
//    }
    
//    @Override
//    public String toString() {
//        return "Dealer's hand: "+ getHand();
//	}
}
