package blackjack;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;
/**
 *
 * @author sanderengelthilo
 */
public class ViewGUI {
    private final JFrame frame;
	private final JPanel mainPanel, labelPanel, nameInputPanel, betInputPanel, playerCardPanel, dealerCardPanel, playerCardImagePanel, dealerCardImagePanel, gameOverPanel;
    private JPanel actionPanel;
    private final JLabel firstLabel, secondLabel, gameOverText;
    private final JTextField nameInput, betInput, playerTextTop, playerTextBottom, dealerTextTop, dealerTextBottom;
	private final JButton nameButton, viewRecordsButton, viewGamesButton, viewHandsButton, startPlayingButton, playAgainButton, betButton, hitButton, standButton;
	private final JTextArea textArea;
	private final JScrollPane scrollPane;
	private final int cardWidth, cardHeight;

    public ViewGUI() {
		
		// PREP
		
        // Create the frame
        frame = new JFrame("Blackjack Game");
        frame.setSize(600, 300);

		// Create main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
		
		// Create vertical JPanel to hold labels
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

		// Create panels for name input/buttons, aligned at the center
        nameInputPanel = new JPanel();
        nameInputPanel.setLayout(new FlowLayout());
		betInputPanel = new JPanel();
		betInputPanel.setLayout(new FlowLayout());
		
        // Create components
        firstLabel = new JLabel("Welcome to BlackJack!");
        secondLabel = new JLabel("What is your name?");
		nameInput = new JTextField(10);
        nameButton = new JButton("Enter");
		
		// Center-align the labels within the BoxLayout
        firstLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Create JTextArea with player statistics
		textArea = new JTextArea(10, 10);
		textArea.setEditable(false); // Read-only
		scrollPane = new JScrollPane(textArea); // Create JScrollPane for textArea
		
		// Create statistics buttons for later
		viewRecordsButton = new JButton("View Statistics");
		viewGamesButton = new JButton("View Games");
		viewHandsButton = new JButton("View Hands");
		
		// Create betting components for later
		betInput = new JTextField(10);
		betButton = new JButton("Bet");
		
		// Create start playing and play again buttons for later		
		startPlayingButton = new JButton("Start Playing");
		playAgainButton = new JButton("Play Again");
		
		// Create hit and stand buttons for later
		hitButton = new JButton("Hit");
		standButton = new JButton("Stand");
		
		// Define card dimensions
		cardWidth = 80;
		cardHeight = 120;
		
		// Create cards panels
		playerCardPanel = new JPanel();
		playerCardPanel.setLayout(new BoxLayout(playerCardPanel, BoxLayout.Y_AXIS));
		dealerCardPanel = new JPanel();
		dealerCardPanel.setLayout(new BoxLayout(dealerCardPanel, BoxLayout.Y_AXIS));
		
		// Create card image panels
		playerCardImagePanel = new JPanel(new OverlappingCardLayout(cardWidth/2));
		dealerCardImagePanel = new JPanel(new OverlappingCardLayout(cardWidth/2));
		
		// Create labels for above and below the cards
		playerTextTop = new JTextField("Player's Cards");
		playerTextTop.setPreferredSize(new Dimension(200, 30));
		playerTextTop.setEditable(false);

		playerTextBottom = new JTextField();
		playerTextBottom.setPreferredSize(new Dimension(200, 30));
		playerTextBottom.setEditable(false);

		dealerTextTop = new JTextField("Dealer's Cards");
		dealerTextTop.setPreferredSize(new Dimension(200, 30));
		dealerTextTop.setEditable(false);

		dealerTextBottom = new JTextField();
		dealerTextBottom.setPreferredSize(new Dimension(200, 30));
		dealerTextBottom.setEditable(false);
		
		// Add components to card panels
		playerCardPanel.add(playerTextTop);
		playerCardPanel.add(playerCardImagePanel);
		playerCardPanel.add(playerTextBottom);
		dealerCardPanel.add(dealerTextTop);
		dealerCardPanel.add(dealerCardImagePanel);
		dealerCardPanel.add(dealerTextBottom);
		
		// Create panel with text field for when player is out of money
		gameOverText = new JLabel("Sorry, you are out of money. Game over.");
		gameOverPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        gameOverPanel.add(gameOverText);
		
		// INITIAL FRAME SETUP
		
		// Add labels to labelPanel
		labelPanel.add(firstLabel);
		labelPanel.add(secondLabel);
		
		// Add input/buttons to input panels
		nameInputPanel.add(nameInput);
        nameInputPanel.add(nameButton);
		betInputPanel.add(betInput);
		betInputPanel.add(betButton);
		
        // Add subpanels to the main panel
        mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(nameInputPanel, BorderLayout.CENTER);

        // Add mainPanel to the frame
        frame.add(mainPanel);

        // Make frame visible
        frame.setVisible(true);
    }

    // Method to update the panel for new/returning players
    public void updatePlayerStatus(String firstLabelText, String secondLabelText, boolean isNewPlayer) {
        // Remove inputPanel after player enters their name
		mainPanel.remove(nameInputPanel);

        firstLabel.setText(firstLabelText);
		secondLabel.setText(secondLabelText);

		// Create action panel for buttons
        actionPanel = new JPanel(new FlowLayout());
		
        // Add view buttons only for returning players
        if (!isNewPlayer) {
            actionPanel.add(viewRecordsButton);
			actionPanel.add(viewGamesButton);
			actionPanel.add(viewHandsButton);
        }
		
		// Add "Start Playing" button for both new and returning players
        actionPanel.add(startPlayingButton);

        // Add the action panel below the introPanel
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // Refresh the frame to display the updated components
        frame.revalidate();
        frame.repaint();
    }
	
	// Method to update the panel to show records for player
	public void updateViewRecords(String RecordsString) {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}

			// Update label text
			firstLabel.setText("Your records with us are as follows:");
			secondLabel.setText("");

			// Update textArea
			textArea.setText(RecordsString);

			// Add scrollPane if not done already
			if (scrollPane.getParent() == null) {
				mainPanel.add(scrollPane, BorderLayout.CENTER);
			}

			// Create new action panel with buttons
			actionPanel = new JPanel(new FlowLayout());
			actionPanel.add(viewGamesButton);
			actionPanel.add(viewHandsButton);
			actionPanel.add(startPlayingButton);

			// Add the action panel to the main panel
			mainPanel.add(actionPanel, BorderLayout.SOUTH);

			// Refresh the frame to display the updated components
			mainPanel.revalidate();
			mainPanel.repaint();
		});
	}

	// Method to update the panel to show played games
	public void updateViewGames(String GamesString) {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}

			// Update label text
			firstLabel.setText("You have played the following games:");
			secondLabel.setText("");

			// Create JTextArea with game statistics
			textArea.setText(GamesString);

			// Add scrollPane if not done already
			if (scrollPane.getParent() == null) {
				mainPanel.add(scrollPane, BorderLayout.CENTER);
			}

			// Create new action panel with buttons
			actionPanel = new JPanel(new FlowLayout());
			actionPanel.add(viewRecordsButton);
			actionPanel.add(viewHandsButton);
			actionPanel.add(startPlayingButton);

			// Add the action panel to the main panel
			mainPanel.add(actionPanel, BorderLayout.SOUTH);

			// Refresh the frame to display the updated components
			mainPanel.revalidate();
			mainPanel.repaint();
		});
	}

	// Method to update the panel to show played hands
	public void updateViewHands(String HandsString) {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}

			// Update first label text
			firstLabel.setText("You have played the following hands:");
			secondLabel.setText("");

			// Create JTextArea with hand statistics
			textArea.setText(HandsString);

			// Add scrollPane if not done already
			if (scrollPane.getParent() == null) {
				mainPanel.add(scrollPane, BorderLayout.CENTER);
			}

			// Create new action panel with buttons
			actionPanel = new JPanel(new FlowLayout());
			actionPanel.add(viewRecordsButton);
			actionPanel.add(viewGamesButton);
			actionPanel.add(startPlayingButton);

			// Add the action panel to the main panel
			mainPanel.add(actionPanel, BorderLayout.SOUTH);

			// Refresh the frame to display the updated components
			mainPanel.revalidate();
			mainPanel.repaint();
		});
	}

	public void updateGetBet() {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}
			
			// Remove scrollPane if it exists
			if (scrollPane != null) {
				mainPanel.remove(scrollPane);
			}
			
			// Remove card panels if they exist
			if (playerCardPanel != null) {
				mainPanel.remove(playerCardPanel);
			}
			if (dealerCardPanel != null) {
				mainPanel.remove(dealerCardPanel);
			}

			// Update first label text
			firstLabel.setText("How much would you like to bet?");
			secondLabel.setText("");

			// Add betInputPanel to main
			mainPanel.add(betInputPanel, BorderLayout.CENTER);
			
			// Refresh the frame to display the updated components
			mainPanel.revalidate();
			mainPanel.repaint();
		});
	}
	
	public void updateInvalidBet(String errorMessage) {
		// Update second label text
		secondLabel.setText(errorMessage);
		
		// Refresh the frame to display the updated components
		mainPanel.revalidate();
		mainPanel.repaint();
	}
	
	public void updateHand() {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}
			
			// Remove betInputPanel if it exists
			if (betInputPanel != null && betInputPanel.getParent() != null) {
				mainPanel.remove(betInputPanel);
			}

			// Update label text
			firstLabel.setText("Hit or Stand?");
			secondLabel.setText("");
			
			// Add card panels to main panel
			mainPanel.add(playerCardPanel, BorderLayout.EAST);
			mainPanel.add(dealerCardPanel, BorderLayout.WEST);

			// Create new action panel with buttons
			actionPanel = new JPanel(new FlowLayout());
			actionPanel.add(hitButton);
			actionPanel.add(standButton);

			// Add the action panel to the main panel
			mainPanel.add(actionPanel, BorderLayout.SOUTH);

			// Refresh the frame to display the updated components
			mainPanel.revalidate();
			mainPanel.repaint();
		});
	}
	
	public void updateDealersTurn() {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}

			// Update label text
			firstLabel.setText("Stood. Dealer's turn...");
			secondLabel.setText("");

			// Refresh the frame to display the updated components
			mainPanel.revalidate();
			mainPanel.repaint();
		});
	}
	
	public void updateCards(JPanel cardImagePanel, List<String> cardImagePaths) {
		SwingUtilities.invokeLater(() -> {
			// Clear previous cards
			cardImagePanel.removeAll();

			// Add new cards
			for (String cardImagePath : cardImagePaths) {
				URL resourceURL = getClass().getResource(cardImagePath);
				if (resourceURL == null) {
					System.out.println("Resource not found: " + cardImagePath);
				} else {
					// Load and scale the image
					ImageIcon cardIcon = new ImageIcon(resourceURL);
					Image scaledImage = cardIcon.getImage().getScaledInstance(cardWidth, cardHeight, Image.SCALE_SMOOTH);
					ImageIcon scaledIcon = new ImageIcon(scaledImage);

					// Add the scaled image to the label and the card image panel
					JLabel cardLabel = new JLabel(scaledIcon);
					cardImagePanel.add(cardLabel);
				}
			}

			// Refresh panel
			cardImagePanel.revalidate();
			cardImagePanel.repaint();
			
		});
	}

	public void endHand(String firstLabelText, String secondLabelText, boolean outOfMoney) {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}

			// Update label text
			firstLabel.setText(firstLabelText);
			secondLabel.setText(secondLabelText);

			if (outOfMoney) {
				mainPanel.add(gameOverPanel, BorderLayout.SOUTH);
			} else {
				// Create new action panel with button
				actionPanel = new JPanel(new FlowLayout());
				actionPanel.add(playAgainButton);

				// Add the action panel to the main panel
				mainPanel.add(actionPanel, BorderLayout.SOUTH);
			}

			// Refresh the frame to display the updated components
			mainPanel.revalidate();
			mainPanel.repaint();
		});
	}


    // Getter methods for frame and buttons to allow Controller to add functionality
	public JFrame getFrame() {
		return frame;
	}
	
	public JLabel getFirstLabel() {
		return firstLabel;
	}
	
    public JButton getNameButton() {
        return nameButton;
    }

    public JTextField getNameInput() {
        return nameInput;
    }

    public JButton getStartPlayingButton() {
        return startPlayingButton;
    }
	
	public JButton getPlayAgainButton() {
		return playAgainButton;
	}

    public JButton getViewRecordsButton() {
        return viewRecordsButton;
    }
	
	public JButton getViewGamesButton() {
		return viewGamesButton;
	}
	
	public JButton getViewHandsButton() {
		return viewHandsButton;
	}
	
	public JButton getBetButton() {
		return betButton;
	}
	
	public JTextField getBetInput() {
		return betInput;
	}
	
	public JButton getHitButton() {
		return hitButton;
	}
	
	public JButton getStandButton() {
		return standButton;
	}
	
	public JPanel getPlayerCardImagePanel() {
		return playerCardImagePanel;
	}
	
	public JPanel getDealerCardImagePanel() {
		return dealerCardImagePanel;
	}
	
	public JTextField getPlayerTextBottom() {
		return playerTextBottom;
	}
	
	public JTextField getDealerTextBottom() {
		return dealerTextBottom;
	}

}
