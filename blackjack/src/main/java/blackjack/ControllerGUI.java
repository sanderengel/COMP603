package blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
		
		// Add WindowListener to execute code after window is closed
        view.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                endGame();
            }
        });

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
		view.getHitButton().addActionListener((ActionEvent e) -> {
			handleHit();
		});
		view.getStandButton().addActionListener((ActionEvent e) -> {
			handleStand();
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
		model.startGame();
		view.updateGetBet();
	}
	
	private void handleBet() {
		double betAmount = 0.0;
		
		// Get bet from the input field
		try {
			betAmount = Double.parseDouble(view.getBetInput().getText().trim());
		} catch (NumberFormatException e) {
			// Case where input is not a valid number
			invalidBet("Please enter a numeric bet value.");
			return;
		}
		
		// Check bet is positive
		if (betAmount <= 0) {
			invalidBet("Please enter a positive number.");
			return;
		}
		
		// Check bet does not exceed balance
		if (betAmount > model.getPlayer().getBalance()) {
			invalidBet("Please enter a bet that does not exceed your balance of $" + model.getPlayer().getBalance() + ".");
			return;
		}
		
		// Bet is valid, continue to play hand
		playHand(betAmount);
		
	}
	
	private void invalidBet(String errorMessage) {
		view.updateInvalidBet(errorMessage);
	}
	
	private void playHand(double bet) {
		
		// Set bet and deal hand in model
		model.setBet(bet);
		model.dealHand();
		
		// Update view to show blackjack playing interface
		view.updateHand("Here's a summary.", "Do something?");
		
		// Update cards
		updateCards(true); // Update with hidden card
		
	}
	
	private void updateCards(boolean hiddenCard) {
		// Define path to images
		String cardImagePath = "/cards/PNG-cards-1.3/";
		
		// Get player and dealer cards
		List<Card> playerCards = model.getPlayer().getHand().getCards();
		List<Card> dealerCards = model.getDealer().getHand().getCards();
		
		// Convert player cards to list of image paths
		List<String> playerCardImagePaths = new ArrayList<>();
		for (Card card : playerCards) {
			playerCardImagePaths.add(cardImagePath + card.toStringForImage() + ".png");
		}
		
		// Convert dealer cards to list of strings for images, handling hidden card if applicable
		List<String> dealerCardImagePaths = new ArrayList<>();
		if (hiddenCard) {
			dealerCardImagePaths.add(cardImagePath + dealerCards.get(0).toStringForImage() + ".png"); // Add first card (visible)
			dealerCardImagePaths.add(cardImagePath + "card-back.png"); // Add second card (hidden)
		} else {
			for (Card card : dealerCards) {
			dealerCardImagePaths.add(card.toStringForImage());
			}
		}
			
		// Update view
		view.updateCards(view.getPlayerCardPanel(), playerCardImagePaths);
		view.updateCards(view.getDealerCardPanel(), dealerCardImagePaths);
	}
	
	private void handleHit() {
		// Write method
	}
	
	private void handleStand() {
		// Write method
	}
	
	// Save information to logs and DB and close program
	private void endGame() {
		model.endGame();
		System.exit(0);
	}
}

