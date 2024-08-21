/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;

/**
 *
 * @author jasseldoong
 */
public class Gamestate {
    
    // Instance variables
    private boolean gameOver;
    private String result;
    
    public Gamestate() {
        gameOver = false;
    }
    
    public void setGameState(String result) {
        this.gameOver = true;
        this.result = result;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
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
    
    public String getResult() {
        return result;
    }
}
