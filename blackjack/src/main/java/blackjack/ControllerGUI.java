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

        // Add an ActionListener to the nameButton
        view.getNameButton().addActionListener((ActionEvent e) -> {
			handleNameInput();
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
		
		view.updatePlayerStatus(isNewPlayer, model.getPlayer().getBalance());
    }
}

