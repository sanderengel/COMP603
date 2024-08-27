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
	private Player player; // Provided by GameRunner
	private Gamestate gameState;
	private InputHandler inputHandler; // Provided by GameRunner
	private OutputHandler outputHandler; // Provided by GameRunner
	
	public Blackjack(Player player, InputHandler inputHandler, OutputHandler outputHandler) {
		
		// Assign passed instances to global variables
		this.inputHandler = inputHandler;
		this.outputHandler = outputHandler;
		this.player = player;
		
		// Initialize dealer and gamestate
		dealer = new Dealer("Dealer");
		
		// Load deck input stream
		try {
			// Load resource file
			InputStream inputStream = Blackjack.class.getResourceAsStream("/cards.txt");
			if (inputStream == null) {
				throw new IOException("Resource file not found");
			}

			// Initialize new deck with input stream
			deck = new Deck(inputStream);

		} catch (IOException e) {
			System.out.println("Error loading deck: " + e.getMessage());
		}
	}
	
	public double playGame() {
		
		// Initialize new gamestate
		gameState = new Gamestate();
		
		// Initialize new hand for player and dealer
		player.addHand();
		dealer.addHand();
		System.out.println("New hand started. Dealing cards...");
		
		// Reset and shuffle deck
		deck.reset();
		deck.shuffle();
		
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

		// Display result and return payout
        outputHandler.displayResult(gameState);
		return gameState.getPayoutMultiplier();
	}
}