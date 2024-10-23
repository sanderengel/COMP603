package blackjack;

/**
 *
 * @author sanderengelthilo
 */
public abstract class Person {
	protected final String name;
	protected Hand hand;
	
	public Person(String name) {
		this.name = name;
        //this.hand = new Hand(); // THIS LINE FOR SOME REASON BREAKS THIS WHOLE CLASS
	}
	
	public void addHand() {
		this.hand = new Hand();
	}
	
	public void addCard(Card card) {
		hand.addCard(card);
	}
	
	public boolean isBust() {
		return hand.isBust();
	}
	
	public int getSum() {
		return hand.getSum();
	}
	
	public String getName() {
		return name;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	@Override
	public String toString() {
		return name + "'s hand: " + hand;
	}
	
	// Abstract player specific methods
	public abstract void adjustBalance(double bet, double payoutMultiplier);
	public abstract double getBalance();
	public abstract void incrementGamesPlayed();
	public abstract int getGamesPlayed();
	public abstract void incrementHandsPlayed();
	public abstract int getHandsPlayed();
	public abstract void updateHandsWon(double payoutMultiplier);
	public abstract int getHandsWon();
}
