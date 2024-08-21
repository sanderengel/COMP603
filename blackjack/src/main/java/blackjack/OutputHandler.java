/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;

/**
 *
 * @author Jassel Doong
 * @author Sander Thilo
 */
public class OutputHandler {
	public void displaySum(Player player) { // Used for both player and dealer
		System.out.println("Sum of " + player.getName() + "'s cards: " + player.getSum());
	}
	
	public void printNatural(Player player) { // Used for both player and dealer
		System.out.println(player.getName() + " has a natural!");
	}
	
    public void displayPlayerHand(Player player) {
        System.out.println("\n" + player);
//		System.out.println("Sum of " + player.getName() + "'s cards: " + player.getSum());
		displaySum(player);
	}
	
	public void displayDealerHand(Dealer dealer) {
		System.out.println("\n" + dealer);
//		System.out.println("Sum of " + dealer.getName() + "'s cards: " + dealer.getSum());
		displaySum(dealer);
	}
    
    public void displayDealerVisibleCard(Dealer dealer) {
        System.out.println("Dealer's visible  card: " + dealer.getHand().getCards().get(0));
		int dealerFirstCardValue = dealer.getHand().getCards().get(0).getValue();
		System.out.println("Value of " + dealer.getName() + "'s visible card: " + dealerFirstCardValue);
	}
    
    public void displayInitialHands(Player player, Dealer dealer) {
        displayPlayerHand(player);
        displayDealerVisibleCard(dealer);
    }
    
//    public void revealHiddenCard(Card hiddenCard) {
//        System.out.println("Dealer's hidden card: " + hiddenCard);
//    }
	
	public void revealHiddenCard(Dealer dealer) {
		System.out.println("\n" + dealer.getName() + "'s hidden card: " + dealer.getHiddenCard());
		displaySum(dealer);
	}
    
    public void displayResult (Gamestate gameState) {
        System.out.println(gameState.getResultText());
    }
}
