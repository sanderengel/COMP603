package blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        // Add ActionListeners
        view.getNameButton().addActionListener((ActionEvent e) -> {
			handleNameInput();
		});
		view.getStartPlayingButton().addActionListener((ActionEvent e) -> {
			handleStartPlaying();
		});
		view.getViewRecordsButton().addActionListener((ActionEvent e) -> {
			handleViewRecords();
		});
		view.getViewGamesButton().addActionListener((ActionEvent e) -> {
			handleViewGames();
		});
		view.getViewHandsButton().addActionListener((ActionEvent e) -> {
			handleViewHands();
		});
		
    }

    // Method to handle name input and check if it's a new player
    private void handleNameInput() {
        // Get the name from the input field
        String playerName = view.getNameInput().getText().trim();

        // Check if name field is empty
        if (playerName.isEmpty()) {
            return;
        }

        // Check if the player is new or returning by passing the name to the model
        boolean isNewPlayer = model.checkIfNewPlayer(playerName);
		
		// Update GUI
		view.updatePlayerStatus(isNewPlayer, model.getPlayer().getBalance());
		
    }
	
	private void handleViewRecords() {
		view.updateViewRecords(model.getPlayer());
	}
	
	private void handleViewGames() {
		view.updateViewGames(model.getPlayer());
	}
	
	private void handleViewHands() {
		view.updateViewHands(model.getPlayer());
	}
	
	private void handleStartPlaying() {
		view.updateStartPlaying();
	}
	
}

