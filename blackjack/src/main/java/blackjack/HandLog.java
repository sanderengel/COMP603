package blackjack;

/**
 *
 * @author sanderengelthilo
 */
public class HandLog {
	private final String gameTimestamp;
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
	
	// Constructor with all parameters
	// Used for DB fetching
	public HandLog(String gameTimestamp, double balanceBeforeHand, double amountBetted, String result, 
			String playerHand, String dealerHand, int playerHandSum, int dealerHandSum, 
			boolean playerNatural, boolean dealerNatural, double balanceAfterHand) {
		this.gameTimestamp = gameTimestamp;
		this.balanceBeforeHand = balanceBeforeHand;
		this.amountBetted = amountBetted;
		this.result = result;
		this.playerHand = playerHand;
		this.dealerHand = dealerHand;
		this.playerHandSum = playerHandSum;
		this.dealerHandSum = dealerHandSum;
		this.playerNatural = playerNatural;
		this.dealerNatural = dealerNatural;
		this.balanceAfterHand = balanceAfterHand;
	}
	
	// Constructor with only gameTimestamp, balanceBeforeHand and amountBetted parameters
	// Used to log new hands
	public HandLog(String gameTimestamp, double balanceBeforeHand, double amountBetted) {
		// Balance before hand is played and amount betted is determined before hand is played
		this.gameTimestamp = gameTimestamp;
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
	
	// Setters for all variables
	public void setResult(String result) {
		this.result = result;
	}

	public void setPlayerHand(String playerHand) {
		this.playerHand = playerHand;
	}

	public void setDealerHand(String dealerHand) {
		this.dealerHand = dealerHand;
	}

	public void setPlayerHandSum(int playerHandSum) {
		this.playerHandSum = playerHandSum;
	}

	public void setDealerHandSum(int dealerHandSum) {
		this.dealerHandSum = dealerHandSum;
	}

	public void setPlayerNatural(boolean playerNatural) {
		this.playerNatural = playerNatural;
	}

	public void setDealerNatural(boolean dealerNatural) {
		this.dealerNatural = dealerNatural;
	}

	public void setBalanceAfterHand(double balanceAfterHand) {
		this.balanceAfterHand = balanceAfterHand;
	}
	
	// Getters for all variables
	public String getGameTimestamp() {
		return gameTimestamp;
	}
	
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
				"gameTimestamp=" + gameTimestamp +
                ", balanceBeforeHand=" + balanceBeforeHand +
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
