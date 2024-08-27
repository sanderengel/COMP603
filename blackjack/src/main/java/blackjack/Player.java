/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;

/**
 *
 * @author jasseldoong
 * @author sanderengelthilo
 */
public class Player {
    private final String name;
    private Hand hand;
	private double balance;
    
    public Player(String name, double startingBalance) {
        this.name = name;
        this.balance = startingBalance;
		this.hand = new Hand(); // Initial hand setup
    }
	
	public void addHand() {
		this.hand = new Hand();
	}
    
    public void addCard(Card card) {
        hand.addCard(card);
    }
	
	public void adjustBalance(double bet, double payoutMultiplier) {
		this.balance += bet * payoutMultiplier;
	}
	
	public double getBalance() {
		return balance;
	}
    
    public boolean isBust() {
        return hand.isBust();
    }
	
	public int getSum() {
        return hand.getSum();
    }
    
    public String getName() {
        return name;
    }
    
    public Hand getHand() {
        return hand;
    }
    
	@Override
    public String toString() {
        return name + "'s hand: " + hand;
    }
    
}
