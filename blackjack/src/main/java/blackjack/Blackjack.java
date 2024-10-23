package blackjack;

import java.io.IOException;
import java.io.InputStream;

public class Blackjack {
	Deck deck;
	private Dealer dealer; // provided by Main
	private Player player; // Provided by Main
	private InputHandler inputHandler; // Provided by Main
	
	public Blackjack(Player player, Dealer dealer, InputHandler inputHandler) { //, OutputHandler outputHandler) {
		
		// Assign passed instances to global variables
		this.inputHandler = inputHandler;
		this.dealer = dealer;
		this.player = player;
		
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
	
	public void playHand(Gamestate gamestate) {
		
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
		OutputHandler.displayInitialHands(player, dealer);
		System.out.println("");
		
		// Check for naturals
		gamestate.natural(player, dealer);
		
		// The play, only happens if no naturals occour
		if (!gamestate.isGameOver()) {
			
			// Player's turn
			OUTER:
			while (player.getSum() < 21) {
				String action = inputHandler.getAction();
				switch (action) {
					case "H":
					case "HIT":
						// Deal new card to player
						player.addCard(deck.dealCard());
						OutputHandler.displayHand(player);
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
				gamestate.playerBust(player, dealer);
			} else {
				// Player has stood, dealer reveal hidden card
				OutputHandler.revealHiddenCard(dealer);
				
				// Dealer's turn
				while (dealer.getSum() < 17) {
					System.out.println("Dealer hits...");
					dealer.addCard(deck.dealCard());
					OutputHandler.displayHand(dealer);
				}
                gamestate.winner(player, dealer);
			}
		}
	}
}