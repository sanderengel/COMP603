package blackjack;

/**
 *
 * @author sanderengelthilo
 */
public class Card {
	private final String rank;
	private final String suit;
	
	public Card(String rank, String suit) {
		this.rank = rank;
		this.suit = suit;
	}

	public String getRank() {
		return rank;
	}

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
	
	public String toStringForImage() {
		return rank + "-" + suit.charAt(0);
	}
	
	@Override
	public String toString() {
		return rank + " of " + suit;
	}
	
}


//