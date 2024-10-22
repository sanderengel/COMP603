package blackjack;

import java.awt.event.ActionEvent;
/**
 *
 * @author sanderengelthilo
 */
public class ControllerGUI {
	private final ModelGUI model;
	private final ViewGUI view;
	
	public ControllerGUI(ModelGUI model, ViewGUI view) {
		this.model = model;
		this.view = view;
		
		view.getBetButton().addActionListener((ActionEvent e) -> {
			placeBet();
		});
		
		// Check if player is new and update view accordingly
		if (model.isNewPlayer()) {
			view.updateGameResult("We see this is your first time here! You start with a balance of " + model.getPlayer().getBalance() + ".");
		} else {
			view.updateGameResult("Welcome back! Your current balance is " + model.getPlayer().getBalance() + ".");
		}
	}
	
	private void placeBet() {
		try {
			double bet = Double.parseDouble(view.getBetInput().getText());
			if (bet > model.getPlayer().getBalance()) {
				view.updateGameResult("Sorry, you are out of money. Game over.");
			} else {
				Gamestate gamestate = new Gamestate();
				model.getBlackjack().playHand(gamestate);
				model.getPlayer().adjustBalance(bet, gamestate.getPayoutMultiplier());
				view.updateBalance(model.getPlayer().getBalance());
				view.updateGameResult("Round completed!");
			}
		} catch (NumberFormatException e) {
			view.updateGameResult("Invalid bet amount.");
		}
	}
}
