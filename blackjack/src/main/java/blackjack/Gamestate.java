package blackjack;

/**
 *
 * @author Jassel Doong
 * @author Sander Thilo
 */
public class Gamestate {
    
    // Instance variables
    private boolean gameOver;
    private double payoutMultiplier;
	private String resultText;
	
	// Bools for control of naturals
	private boolean playerNatural;
	private boolean dealerNatural;
    
    // Constructor, initialise booleans to false
    public Gamestate() {
        gameOver = false;
		playerNatural = false;
		dealerNatural = false;
    }
    
    // Set the state of the game to over and record the result
    private void setState(double payoutMultiplier, String resultText) {
        this.gameOver = true;
        this.payoutMultiplier = payoutMultiplier;
		this.resultText = resultText;
    }
    
    // Check if the game is over
    public boolean isGameOver() {
        return gameOver;
    }
	
	public void playerBust(Player player, Dealer dealer) {
		setState(-1.0, player.getName() + " busted! " + dealer.getName() + " wins!");
	}
    
	// We should move this function to Blackjack.java
	public void natural(Player player, Dealer dealer, OutputHandler outputHandler) {
		
		// Check if player has natural
		if (player.getSum() == 21) {
			outputHandler.printNatural(player);
			playerNatural = true;
		}
		
		// Check if dealer has Ace or 10-card
		if (dealer.getHand().getCards().get(0).getValue() >= 10) {
			System.out.println(dealer.getName() + " might have a natural. Checking hidden card...");
			if (dealer.getSum() == 21) {
				outputHandler.printNatural(dealer);
				dealerNatural = true;
			} else {
				System.out.println("They did not have a natural...\n");
			}
		}
		
		// Cross check for naturals
		if (playerNatural) {
			if (dealerNatural) {
				setState(0.0, "Both " + player.getName() + " and " + dealer.getName() + " have naturals, it's a tie!");
			} else {
				setState(1.5, player.getName() + " has a natural win!");
			}
		} else if (dealerNatural) {
			setState(-1.0, dealer.getName() + " has a natural win!");
		}
		
	}
	
    // Determines winner base on player's and dealer's cards on hand
    public void winner(Player player, Dealer dealer) {
		System.out.println("");
        if(dealer.isBust()) {
            setState(1.0, dealer.getName() + " busts! " + player.getName() + " wins!");
        } else if (player.getSum() > dealer.getSum()) {
            setState(1.0, player.getName() + " wins!");
        } else if (player.getSum() < dealer.getSum()) {
            setState(-1.0, player.getName() + " loses!");
        } else {
            setState(0.0, "Both " + player.getName() + " and " + dealer.getName() + " have " + player.getSum() + ", it's a tie!");
        }
    }
    
    // Resulting payout
    public double getPayoutMultiplier() {
        return payoutMultiplier;
    }
	
	// Result text
	public String getResultText() {
		return resultText;
	}
}
