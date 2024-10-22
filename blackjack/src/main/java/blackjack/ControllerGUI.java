package blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
			try {
				handleStartPlaying();
			} catch (SQLException ex) {
				Logger.getLogger(ControllerGUI.class.getName()).log(Level.SEVERE, null, ex);
			}
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
		view.getBetButton().addActionListener((ActionEvent e) -> {
			handleBet();
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

		// Register player to model
		model.registerPlayer(playerName);
		
		// Update GUI
		view.updatePlayerStatus(model.isNewPlayer(), model.getPlayer().getBalance());
		
    }
	
	private void handleViewRecords() {
		view.updateViewRecords(model.getPlayerStatistics());
	}
	
	private void handleViewGames() {
		view.updateViewGames(model.getGameStatistics());
	}
	
	private void handleViewHands() {
		view.updateViewHands(model.getHandStatistics());
	}
	
	private void handleStartPlaying() throws SQLException {
		// Start game
		// model.playGame();
		view.updateGetBet();
	}
	
	private void handleBet() {
		// Write this
	}
	
}

