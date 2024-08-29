/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package blackjack;

/**
 *
 * @author sanderengelthilo
 */
public class HandLog {
	private final double balanceBeforeHand;
	private final double amountBetted;
	private String result;
	private String playerHand;
	private String dealerHand;
	private int playerHandSum;
	private int dealerHandSum;
	private boolean playerNatural;
	private boolean dealerNatural;
	private double balanceAfterHand;
	
	public HandLog(double balanceBeforeHand, double amountBetted) {
		// Balance before hand is played and amount betted is determined before hand is played
		this.balanceBeforeHand = balanceBeforeHand;
		this.amountBetted = amountBetted;
	}
	
	public void fillHandLog(Player player, Dealer dealer, Gamestate gamestate) {
		this.result = gamestate.getResultText();
		this.playerHand = player.getHand().toString();
		this.dealerHand = dealer.getHand().toString();
		this.playerHandSum = player.getSum();
		this.dealerHandSum = dealer.getSum();
		this.playerNatural = gamestate.isPlayerNatural();
		this.dealerNatural = gamestate.isDealerNatural();
		this.balanceAfterHand = player.getBalance();
	}
	
	@Override
	public String toString() {
        return "HandLog{" +
                "balanceBeforeHand=" + balanceBeforeHand +
                ", amountBetted=" + amountBetted +
                ", result='" + result + '\'' +
				", playerHand=" + playerHand +
				", dealerHand=" + dealerHand +
                ", playerHandSum=" + playerHandSum +
                ", dealerHandSum=" + dealerHandSum +
                ", dealerNatural=" + dealerNatural +
                ", playerNatural=" + playerNatural +
                ", balanceAfterHand=" + balanceAfterHand +
                '}';
    }
}
