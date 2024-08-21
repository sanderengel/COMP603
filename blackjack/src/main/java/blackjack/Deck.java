package blackjack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.util.Collections;
import java.io.InputStreamReader;
/**
 *
 * @author sanderengelthilo
 */
public class Deck {
	// Initialize list of cards
	private final List<Card> cards = new ArrayList<>();
	
	// Constructor to load deck from InputStream
	public Deck(InputStream inputStream) throws IOException {
		loadDeckFromFile(inputStream);
	}
	
	// Load deck of cards from file
	private void loadDeckFromFile(InputStream inputStream) throws IOException {
		try (var br = new BufferedReader(new InputStreamReader(inputStream))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				cards.add(new Card(parts[0], parts[1]));
			}
		}
	}
	
	// Return cards in deck
	public List<Card> getCards() {
		return cards;
	}
	
	// Shuffle deck
	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	// Deal card
	public Card dealCard() {
		return cards.remove(0); // Deal top card
	}
}
