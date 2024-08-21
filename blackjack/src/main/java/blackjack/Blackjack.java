package blackjack;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author sanderengelthilo
 */
public class Blackjack {
	public static void main(String[] args) {
		try {
			// Load resource file
			InputStream inputStream = Blackjack.class.getResourceAsStream("/cards.txt");
			
			if (inputStream == null) {
				throw new IOException("Resource file not found");
			}
			
			// Initialize new deck with input stream
			Deck deck = new Deck(inputStream);
			
			for (Card card : deck.getCards()) {
				System.out.println(card);
			}
			
		} catch (IOException e) {
			System.out.println("Error loading deck: " + e.getMessage());
		}
	}
}
