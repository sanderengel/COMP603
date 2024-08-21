package blackjack;

import java.util.ArrayList;

public class Hand {
    
    // Instance variables
    private int sum;
    private int aceCount;
    private ArrayList<Card> cards;

    // Constructor
    public Hand() {
        this.cards = new ArrayList<>();
        this.sum = 0;
        this.aceCount = 0;
    }
    
    // Adds a card to the hand and updates the sum
    // Checks if the card is an Ace which calls ForAces methods to handle special case of Ace, increments aceCount as well
    public void addCard(Card card) {
        this.cards.add(card);
        this.sum += card.getValue();

        if(card.isAce()) {
            this.aceCount++;
        }

        ForAces();
    }
    
    // Checks the sum if it exceeds 21 and if there are Aces in hand
    // Converts Aces from the value of 11 to 1 until the sum is 21 or less, or if no Aces left to convert
    private void ForAces() {
        while(this.sum > 21 && this.aceCount > 0) {
            this.sum -= 10;
            this.aceCount--;
        }
    }
    
    // Total sum of cards in hand
    public int getSum() {
        return sum;
    }

    // Check is hand is bust
    public boolean isBust() {
        return this.sum > 21;
    }

    // Returns a copy of list of cards in hand
    public ArrayList<Card> getCards() {
        return new ArrayList<>(this.cards);
    }

    // String representation of hand, listing the cards
    @Override
    public String toString() {
        return this.cards.toString();
    }
}