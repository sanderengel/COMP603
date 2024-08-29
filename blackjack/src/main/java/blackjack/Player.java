package blackjack;

/**
 *
 * @author jasseldoong
 * @author sanderengelthilo
 */
public class Player extends Person {
	private double balance;
	
	public Player(String name, double startingBalance) {
		super(name);
		this.balance = startingBalance;
	}
	
	@Override
	public void adjustBalance(double bet, double payoutMultiplier) {
		this.balance += bet * payoutMultiplier;
	}

	@Override
	public double getBalance() {
		return balance;
	}
}

//public class Player {
//    private final String name;
//	private double balance;
//	private Hand hand;
//    
//    public Player(String name, double startingBalance) {
//        this.name = name;
//        this.balance = startingBalance;
//    }
//	
//	public void addHand() {
//		this.hand = new Hand();
//	}
//    
//    public void addCard(Card card) {
//        hand.addCard(card);
//    }
//	
//	public void adjustBalance(double bet, double payoutMultiplier) {
//		this.balance += bet * payoutMultiplier;
//	}
//	
//	public double getBalance() {
//		return balance;
//	}
//    
//    public boolean isBust() {
//        return hand.isBust();
//    }
//	
//	public int getSum() {
//        return hand.getSum();
//    }
//    
//    public String getName() {
//        return name;
//    }
//    
//    public Hand getHand() {
//        return hand;
//    }
//    
//	@Override
//    public String toString() {
//        return name + "'s hand: " + hand;
//    }
//    
//}
