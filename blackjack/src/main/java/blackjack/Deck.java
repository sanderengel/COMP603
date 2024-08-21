package blackjack;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sanderengelthilo
 */
public class Deck {
	// Initialize list of cards
	private list<Card> cards = new ArrayList<>();
	
	public Deck(String filename) throws IOException {
		loadDeckFromFile(filename);
	}
	
	public void loadDeckFromFile
}
