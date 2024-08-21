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
    private String result;
    
    // Constructor, initialise gameOver to false
    public Gamestate() {
        gameOver = false;
    }
    
    // Set the state of the game to over and record the result
    public void setGameState(String result) {
        this.gameOver = true;
        this.result = result;
    }
    
    // Check if the game is over
    public boolean isGameOver() {
        return gameOver;
    }
    
    // Determines winner base on player's and dealer's cards on hand
    public void winner(Player player, Dealer dealer) {
        if(dealer.isBust()) {
            setGameState("Dealer busts!"+ player.getName());
        } else if( player.getSum() > dealer.getSum()) {
            setGameState(player.getName() +" win");
        } else if(player.getSum() < dealer.getSum()) {
            setGameState(player.getName() +" lose");
        } else{
            setGameState("tie");
        }
    }
    
    // Result of the game
    public String getResult() {
        return result;
    }
}
