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
	
	// Abstract methods for balance operations
	public abstract void adjustBalance(double bet, double payoutMultiplier);
	public abstract double getBalance();
}
