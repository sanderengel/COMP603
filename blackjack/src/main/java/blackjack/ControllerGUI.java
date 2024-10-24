package blackjack;

import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class ControllerGUI {
    private final ModelGUI model;
    private final ViewGUI view;

    public ControllerGUI(ModelGUI model, ViewGUI view) {
        this.model = model;
        this.view = view;
		AddListeners();
    }
	
	private void AddListeners() {
		// Add WindowListener to execute code after window is closed
        view.getFrame().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
				try {
					endGame();
				} catch (SQLException ex) {
					Logger.getLogger(ControllerGUI.class.getName()).log(Level.SEVERE, null, ex);
				} finally {
					System.exit(0); // Close program
				}
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
		view.getPlayAgainButton().addActionListener((ActionEvent e) -> {
			handlePlayAgain();
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
			try {
				handleBet();
			} catch (SQLException ex) {
				Logger.getLogger(ControllerGUI.class.getName()).log(Level.SEVERE, null, ex);
			} catch (InterruptedException ex) {
				Logger.getLogger(ControllerGUI.class.getName()).log(Level.SEVERE, null, ex);
			}
		});
		view.getHitButton().addActionListener((ActionEvent e) -> {
			try {
				handleHit();
			} catch (SQLException ex) {
				Logger.getLogger(ControllerGUI.class.getName()).log(Level.SEVERE, null, ex);
			}
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
		
		// Message for new or returning players
		String firstLabelText;
		String secondLabelText;
        if (model.isNewPlayer()) {
            firstLabelText = "We see this is your first time here!";
			secondLabelText = "You start with a balance of $" + model.toIntIfPossible(model.getPlayer().getBalance()) + ".";
        } else {
            firstLabelText = "Welcome back!";
			secondLabelText = "Your current balance with us is $" + model.toIntIfPossible(model.getPlayer().getBalance()) + ".";
        }
		
		// Update GUI
		view.updatePlayerStatus(firstLabelText, secondLabelText, model.isNewPlayer(), model.getPlayer().outOfMoney());
		
    }
	
	private void handleViewRecords() {
		view.updateViewRecords(model.getPlayerStatistics(), model.getPlayer().outOfMoney());
	}
	
	private void handleViewGames() {
		view.updateViewGames(model.getGameStatistics(), model.getPlayer().outOfMoney());
	}
	
	private void handleViewHands() {
		view.updateViewHands(model.getHandStatistics(), model.getPlayer().outOfMoney());
	}
	
	private void handleStartPlaying() throws SQLException {
		// Start game
		model.startGame();
		
		// Ask for bet
		view.updateGetBet();
	}
	
	private void handlePlayAgain() {
		// Ask for bet
		view.updateGetBet();
	}
	
	private void handleBet() throws SQLException, InterruptedException {
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
			invalidBet("Please enter a bet that does not exceed your balance of $" + model.toIntIfPossible(model.getPlayer().getBalance()) + ".");
			return;
		}
		
		// Bet is valid, continue to play hand
		playHand(betAmount);
		
	}
	
	private void invalidBet(String errorMessage) {
		view.updateInvalidBet(errorMessage);
	}
	
	private void playHand(double bet) throws SQLException, InterruptedException {
		
		// Set bet and start hand in model
		model.startHand(bet);
		
		// Update view to show blackjack playing interface
		view.updateHand();
		
		// Update cards
		updateCards(true); // Update with hidden card
		
		// Check for natural win/loss
		if (model.checkNaturals()) {
			// Show all cards if dealer has natural
			if (model.getDealer().hasNatural()) {
				updateCards(false);
			}
			endHand();
		}
		
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
				dealerCardImagePaths.add(cardImagePath + card.toStringForImage() + ".png"); // Add all cards (visible)
			}
		}
			
		// Update view
		view.updateCards(view.getPlayerCardImagePanel(), playerCardImagePaths);
		view.updateCards(view.getDealerCardImagePanel(), dealerCardImagePaths);
		view.getPlayerTextBottom().setText("Sum of cards: " + model.getPlayer().getSum());
		if (hiddenCard) {
			int dealerFirstCardValue = model.getDealer().getHand().getCards().get(0).getValue();
			view.getDealerTextBottom().setText("Value of visible card: " + dealerFirstCardValue);
		} else {
			view.getDealerTextBottom().setText("Sum of cards: " + model.getDealer().getSum());
		}
		
	}
	
	private void handleHit() throws SQLException {
		model.playerHit();
		
		// Update cards
		updateCards(true);
		
		// Check if player is bust
		if (model.getPlayer().isBust()) {
			endHand();
		}
		
		// Check if player has 21, automatically stand
		else if (model.getPlayer().has21()) {
			handleStand();
		}
	}
	
	private void handleStand() {
		// Update cards to show hidden card and initial dealer state
		updateCards(false);
		view.updateDealersTurn();

		// Run dealer's turn logic in a background thread to avoid blocking the EDT
		SwingWorker<Void, Void> worker = new SwingWorker<>() {
			@Override
			protected Void doInBackground() throws Exception {
				// Dealer's turn logic, including hitting while dealer sum is less than 17
				while (model.getDealer().getSum() < 17) {
					
					// Sleep to simulate a delay between each dealer hit
					Thread.sleep(2000); // milliseconds
					model.dealerHit();

					// Use SwingUtilities.invokeLater to ensure that UI updates run on the EDT
					SwingUtilities.invokeLater(() -> updateCards(false));
					SwingUtilities.invokeLater(() -> view.getFirstLabel().setText("Dealer hitting..."));
				}
				return null;
			}

			@Override
			protected void done() {
				SwingUtilities.invokeLater(() -> view.getFirstLabel().setText("Dealer stands."));
				model.determineWinner();
				try {
					endHand();
				} catch (SQLException ex) {
					Logger.getLogger(ControllerGUI.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		};

		// Execute the worker in a background thread
		worker.execute();
	}

	
	private void endHand() throws SQLException {
		model.endHand();
		view.endHand(model.getResultText(), 
					"Your balance is now $" + model.toIntIfPossible(model.getPlayer().getBalance()) + ".", 
					(model.getPlayer().outOfMoney()));
	}
	
	// Save information to logs and DB and close program
	private void endGame() throws SQLException {
		model.endGame();
	}
}

