package blackjack;

/**
 *
 * @author jasseldoong
 * @author sanderengelthilo
 */
public class Player extends Person {
	private double balance;
	private int handsPlayed;
	private int handsWon;
	
	public Player(String name, double startingBalance) {
		super(name);
		this.balance = startingBalance;
		this.handsPlayed = 0;
		this.handsWon = 0;
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
	
	@Override
	public void updateHandsWon(double payoutMultiplier) {
		if (payoutMultiplier > 0) {
			this.handsWon++;
		}
	}
	
	@Override
	public int getHandsWon() {
		return handsWon;
	}
}


