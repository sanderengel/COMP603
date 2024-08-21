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
	private InputHandler inputHandler;
	private OutputHandler outputHandler;
	private int bet;
	
	public void Blackjack() {
		
		// Initialize components
		inputHandler = new InputHandler();
		outputHandler = new OutputHandler();
		gameState = new Gamestate();
		
		// Initialize dealer and player
		dealer = new Dealer("Dealer");
		player = new Player("Player");
		
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
	
	public void playGame() {
		
		// Deal first cards
		player.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard()); // Dealer's first card is visible
		
		// Deal second cards
		player.addCard(deck.dealCard());
		dealer.sethiddenCard(deck.dealCard()); // Dealer's second card is hidden
		
		// Check for naturals
		gameState.natural(player, dealer);
		
		// The play
		if (!gameState.isGameOver()) {
			while (!player.isBust()) {
				String action = inputHandler.getAction();
				// CONTINUE
			}
		}
			
	}
}
	



//	// Initialize other classes
//	public BlackJackGame() {
//		dealer = new Dealer("Dealer");
//		player = new Player("Player");
//	}
	
//	public static void main(String[] args) {
//		BlackJackGame();
//	}
//}
