// NOT DONE AT ALLLLLL

package blackjack;

import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author sanderengelthilo
 */
public class Blackjack {
	private Deck deck;
	private Dealer dealer;
	private Player player;
	private Gamestate gameState;
	// private InputHandler inputHandler;
	private OutputHandler outputHandler;
	
	public void BlackJackGame() {
		// Load deck
		try {
			// Load resource file
			InputStream inputStream = Blackjack.class.getResourceAsStream("/cards.txt");

			if (inputStream == null) {
				throw new IOException("Resource file not found");
			}

			// Initialize new deck with input stream
			deck = new Deck(inputStream);

			// Shuffle deck
			deck.shuffle();

			// Print cards in deck for testing
			for (Card card : deck.getCards()) {
				System.out.println(card);
			}

		} catch (IOException e) {
			System.out.println("Error loading deck: " + e.getMessage());
		}
	}
	
	



//	// Initialize other classes
//	public BlackJackGame() {
//		dealer = new Dealer("Dealer");
//		player = new Player("Player");
//	}
	
	public static void main(String[] args) {
		BlackJackGame();
	}
}
