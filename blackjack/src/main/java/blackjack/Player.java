/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;

/**
 *
 * @author jasseldoong
 */
public class Player {
    private String name;
    private Hand hand;
    
    public Player(String name) {
        this.name = name;
        hand = new Hand();
    }
    
    public void addCard(Card card) {
        hand.addCard(card);
    }
    
    public int getSum() {
        return hand.getSum();
    }
    
    public boolean isBust() {
        return hand.isBust();
    }
    
    public String getName() {
        return name;
    }
    
    public Hand getHand() {
        return hand;
    }
    
    public String toString() {
        return name + "'s hand: " + hand;
    }
    
}
