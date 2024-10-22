package blackjack;

/**
 *
 * @author jasseldoong
 * @author sanderengelthilo
 */
public class Player extends Person {
	private double balance;
	private int gamesPlayed;
	private int handsPlayed;
	private int handsWon;
	
	public Player(String name, double balance, int gamesPlayed, int handsPlayed, int handsWon) {
		super(name);
		this.balance = balance;
		this.gamesPlayed = gamesPlayed;
		this.handsPlayed = handsPlayed;
		this.handsWon = handsWon;
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
	public void incrementGamesPlayed() {
		this.gamesPlayed++;
	}
	
	@Override
	public int getGamesPlayed() {
		return gamesPlayed;
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


