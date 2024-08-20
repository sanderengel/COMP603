package blackjack;

/**
 *
 * @author sanderengelthilo
 */
public class Card {
	private String rank;
	private String suit;
	
	public Card(String rank, String suit) {
		this.rank = rank;
		this.suit = suit;
	}

	/**
	 * @return the rank
	 */
	public String getRank() {
		return rank;
	}

	/**
	 * @return the suit
	 */
	public String getSuit() {
		return suit;
	}
	
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
	
}
