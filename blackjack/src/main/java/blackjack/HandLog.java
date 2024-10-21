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
	
	// Getters for all variables
    public double getBalanceBeforeHand() {
        return balanceBeforeHand;
    }

    public double getAmountBetted() {
        return amountBetted;
    }

    public String getResult() {
        return result;
    }

    public String getPlayerHand() {
        return playerHand;
    }

    public String getDealerHand() {
        return dealerHand;
    }

    public int getPlayerHandSum() {
        return playerHandSum;
    }

    public int getDealerHandSum() {
        return dealerHandSum;
    }

    public boolean isPlayerNatural() {
        return playerNatural;
    }

    public boolean isDealerNatural() {
        return dealerNatural;
    }

    public double getBalanceAfterHand() {
        return balanceAfterHand;
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
