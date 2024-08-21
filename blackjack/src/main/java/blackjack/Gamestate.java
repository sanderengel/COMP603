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
public class Gamestate {
    
    // Instance variables
    private boolean gameOver;
    private double payout;
	private String resultText;
    
    // Constructor, initialise gameOver to false
    public Gamestate() {
        gameOver = false;
    }
    
    // Set the state of the game to over and record the result
    public void setGameState(double payout, String resultText) {
        this.gameOver = true;
        this.payout = payout;
		this.resultText = resultText;
    }
    
    // Check if the game is over
    public boolean isGameOver() {
        return gameOver;
    }
    
	// Checks for and handles cases where player has natural
	public void natural(Player player, Dealer dealer) {
		// Check if player has natural
		if (player.getSum() == 21) { // Player has natural
			if (dealer.getSum() == 21) { // Dealer also has natural
				setGameState(1.0, "Both " + player.getName() + " and " + dealer.getName() + " have naturals, it's a tie!");
			} else {
				setGameState(2.5, player.getName() + " has a natural win!");
			}		
		} else if (dealer.getSum() == 21) {
			setGameState(0.0, dealer.getName() + " has a natural win!");
		}
	}
	
    // Determines winner base on player's and dealer's cards on hand
    public void winner(Player player, Dealer dealer) {
        if(dealer.isBust()) {
            setGameState(2.0, dealer.getName() + " busts! " + player.getName() + " wins!");
        } else if( player.getSum() > dealer.getSum()) {
            setGameState(2.0, player.getName() + " wins!");
        } else if(player.getSum() < dealer.getSum()) {
            setGameState(0.0, player.getName() + " loses!");
        } else{
            setGameState(1.0, "Both " + player.getName() + " and " + dealer.getName() + " have " + player.getSum() + ", it's a tie!");
        }
    }
    
    // Resulting payout
    public double getPayout() {
        return payout;
    }
	
	// Result text
	public String getResultText() {
		return resultText;
	}
}
