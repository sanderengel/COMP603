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
    public void displayPlayerHand(Player player) {
        System.out.println(player);
    }
    
    public void displayDealerVisibleCard(Dealer dealer) {
        System.out.println("Dealer's visible  card: " + dealer.getHand().getCards().get(0));
    }
    
    public void displayInitialHands(Player player, Dealer dealer) {
        displayPlayerHand(player);
        displayDealerVisibleCard(dealer);
    }
    
    public void revealHiddenCard(Card hiddenCard) {
        System.out.println("Dealer's hidden card: "+ hiddenCard);
    }
    
    public void displayResult (Gamestate gameState) {
        System.out.println(gameState.getResult());
    }
    
}
