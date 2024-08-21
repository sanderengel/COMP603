/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;

/**
 *
 * @author jasseldoong
 */
public class Dealer extends Player {
    private Card hiddenCard;
    
    public Dealer(String name) {
        super(name);
    }
    
    public void sethiddenCard(Card card) {
        hiddenCard = card;
        addCard(card);
    }
    
    public void makeMove(Deck deck, OutputHandler outputHandler) {
        while (getSum() < 17) {
            Card newCard = deck.dealCard();
            addCard(newCard);
        }
        outputHandler.revealHiddenCard(hiddenCard);
    }
    
    @Override
    public String toString() {
        return "Dealer's hand: "+ getHand();
}
}
