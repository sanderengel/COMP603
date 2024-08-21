package blackjack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collections;
/**
 *
 * @author sanderengelthilo
 */
public class Deck {
	// Initialize list of cards
	private final List<Card> cards = new ArrayList<>();
	
	public Deck(String filename) throws IOException {
		loadDeckFromFile(filename);
	}
	
	// Load deck of cards from file
	private void loadDeckFromFile(String filename) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(" ");
				cards.add(new Card(parts[0], parts[1]));
			}
		}
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
