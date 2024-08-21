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
	
	// Return value
	public int getValue() {
		if (rank.equals("A")) {
			return 11;
		} else if ("JQK".contains(rank)) {
			return 10;
		}
		return Integer.parseInt(rank);
	}
	
	public boolean isAce() {
		return rank.equals("A");
	}
	
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
	
}
