package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;
/**
 *
 * @author sanderengelthilo
 */
public class ViewGUI {
    private final JFrame frame;
	private final JPanel mainPanel, labelPanel, nameInputPanel, betInputPanel, playerCardPanel, dealerCardPanel, playerCardImagePanel, dealerCardImagePanel;
    private JPanel actionPanel;
    private final JLabel firstLabel, secondLabel;
    private final JTextField nameInput, betInput, playerTextTop, playerTextBottom, dealerTextTop, dealerTextBottom, gameOverText;
	private final JButton nameButton, viewRecordsButton, viewGamesButton, viewHandsButton, startPlayingButton, playAgainButton, betButton, hitButton, standButton;
	private final JTextArea textArea;
	private final JScrollPane scrollPane;
	private final int cardWidth, cardHeight;

    public ViewGUI() {
		
		// PREP
		
        // Create the frame
        frame = new JFrame("Blackjack Game");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 300);

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
		
		// Create cards panels
		playerCardPanel = new JPanel();
		playerCardPanel.setLayout(new BoxLayout(playerCardPanel, BoxLayout.Y_AXIS));
		dealerCardPanel = new JPanel();
		dealerCardPanel.setLayout(new BoxLayout(dealerCardPanel, BoxLayout.Y_AXIS));
		
		// Create card image panels
		playerCardImagePanel = new JPanel(new OverlappingCardLayout());
		dealerCardImagePanel = new JPanel(new OverlappingCardLayout());
		
		// Create text fields for above and below the cards, make them read-only
		playerTextTop = new JTextField("Player's Cards", 15); // Top text field for player
		playerTextBottom = new JTextField("", 15); // Bottom text field for player
		dealerTextTop = new JTextField("Dealer's Cards", 15); // Top text field for dealer
		dealerTextBottom = new JTextField("", 15); // Bottom text field for dealer
		playerTextTop.setEditable(false);
		playerTextBottom.setEditable(false);
		dealerTextTop.setEditable(false);
		dealerTextBottom.setEditable(false);
		
		// Add components to card panels
		playerCardPanel.add(playerTextTop);
		playerCardPanel.add(playerCardImagePanel);
		playerCardPanel.add(playerTextBottom);
		dealerCardPanel.add(dealerTextTop);
		dealerCardPanel.add(dealerCardImagePanel);
		dealerCardPanel.add(dealerTextBottom);
		
		// Define card dimensions
		cardWidth = 60;
		cardHeight = 90;
		
		// Create text field for when player is out of money
		gameOverText = new JTextField("Sorry, you are out of money. Game over.");
		gameOverText.setEditable(false);
		
		// INITIAL FRAME SETUP
		
		// Center-align the labels within the BoxLayout
        firstLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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
    public void updatePlayerStatus(boolean isNewPlayer, double balance) {
        // Remove inputPanel after player enters their name
		mainPanel.remove(nameInputPanel);

        // Message for new or returning players
        if (isNewPlayer) {
            firstLabel.setText("We see this is your first time here!");
			secondLabel.setText("You start with a balance of $" + balance + ".");
        } else {
            firstLabel.setText("Welcome back!");
			secondLabel.setText("Your current balance with us is $" + balance + ".");
        }

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

			// Update first label text
			firstLabel.setText("Your records with us are as follows:");

			// Remove second label text
			labelPanel.remove(secondLabel);

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

			// Update first label text
			firstLabel.setText("You have played the following games:");

			// Remove second label text
			labelPanel.remove(secondLabel);

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

			// Remove second label text
			labelPanel.remove(secondLabel);

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

			// Remove second label text
			labelPanel.remove(secondLabel);

			// Add betInputPanel to main
			mainPanel.add(betInputPanel, BorderLayout.CENTER);
			
			// Refresh the frame to display the updated components
			mainPanel.revalidate();
			mainPanel.repaint();
		});
	}
	
	public void updateInvalidBet(String errorMessage) {
		// Add and update second label text
		labelPanel.add(secondLabel);
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
			
			// Add text fields to card panels
			playerCardPanel.add(playerTextTop, BorderLayout.NORTH);
			playerCardPanel.add(playerTextBottom, BorderLayout.SOUTH);
			dealerCardPanel.add(dealerTextTop, BorderLayout.NORTH);
			dealerCardPanel.add(dealerTextBottom, BorderLayout.SOUTH);
			
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
	
	public void updateSumText(JTextField textBottomField, String sumText) {
		textBottomField.setText(sumText);
		textBottomField.revalidate();
		textBottomField.repaint();
	}

	public void endHand(String topText, boolean outOfMoney) {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}

			// Update label text
			firstLabel.setText(topText);

			if (outOfMoney) {
				gameOverText.setAlignmentX(Component.CENTER_ALIGNMENT);
				mainPanel.add(gameOverText, BorderLayout.SOUTH);
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
	
//	public JPanel getPlayerCardPanel() {
//		return playerCardPanel;
//	}
//	
//	public JPanel getDealerCardPanel() {
//		return dealerCardPanel;
//	}
	
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
