package blackjack;

/**
 *
 * @author jasseldoong
 * @author sanderengelthilo
 */
public class Player extends Person {
	private double balance;
	private int handsPlayed;
	
	public Player(String name, double startingBalance) {
		super(name);
		this.balance = startingBalance;
		this.handsPlayed = 0;
	}
	
	@Override
	public void adjustBalance(double bet, double payoutMultiplier) {
		this.balance += bet * payoutMultiplier;
	}

	@Override
	public double getBalance() {
		return balance;
	}
	
	@Override
	public void incrementHandsPlayed() {
		this.handsPlayed++;
	}
	
	@Override
	public int getHandsPlayed() {
		return handsPlayed;
	}
}


