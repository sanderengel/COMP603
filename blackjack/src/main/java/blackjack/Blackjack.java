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
	
	public Blackjack() {
		
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

		} catch (IOException e) {
			System.out.println("Error loading deck: " + e.getMessage());
		}
	}
	
	public void playGame() {
		
		System.out.println("New hand started. Dealing cards...");
		
		// Deal first cards
		player.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard()); // Dealer's first card is visible
		
		// Deal second cards
		player.addCard(deck.dealCard());
		dealer.setHiddenCard(deck.dealCard()); // Dealer's second card is hidden
		
		// Print hands
		outputHandler.displayInitialHands(player, dealer);
		System.out.println("");
		
		// Check for naturals
		gameState.natural(player, dealer, outputHandler);
		
		// The play, only happens if no naturals occour
		if (!gameState.isGameOver()) {
			
			// Player's turn
			OUTER:
			while (player.getSum() < 21) {
				String action = inputHandler.getAction();
				switch (action) {
					case "H":
					case "HIT":
						// Deal new card to player
						player.addCard(deck.dealCard());
						outputHandler.displayPlayerHand(player);
						break;
					case "S":
					case "STAND":
						break OUTER;
					default:
						System.out.println("Please input a valid answer (H or S)");
						break;
				}
			}
			
			// Check if player has busted
			if (player.isBust()) {
				gameState.playerBust(player, dealer);
			} else {
				// Player has stood, dealer reveal hidden card
				outputHandler.revealHiddenCard(dealer);
				
				// Dealer's turn
				while (dealer.getSum() < 17) {
					System.out.println("Dealer hits...");
					dealer.addCard(deck.dealCard());
					outputHandler.displayDealerHand(dealer);
				}
                                gameState.winner(player, dealer);
			}
			
		}
		// Last part which is missing
		// Return payout and result
                outputHandler.displayResult(gameState);
	}
	
	// Main method to test the game
    public static void main(String[] args) {
        Blackjack game = new Blackjack(); // Create a new Blackjack game
        game.playGame(); // Play a single round of Blackjack
    }
}
