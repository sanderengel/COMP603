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
	private final JPanel mainPanel, labelPanel, nameInputPanel, betInputPanel, playerCardPanel, dealerCardPanel;
    private JPanel actionPanel;
    private final JLabel firstLabel, secondLabel;
    private final JTextField nameInput, betInput;
	private final JButton nameButton, viewRecordsButton, viewGamesButton, viewHandsButton, startPlayingButton, betButton, hitButton, standButton;
	private final JTextArea textArea;
	private final JScrollPane scrollPane;

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
		
		// Create start playing button for later		
		startPlayingButton = new JButton("Start Playing");
		
		// Create hit and stand buttons for later
		hitButton = new JButton("Hit");
		standButton = new JButton("Stand");
		
		// Create cards panels
		playerCardPanel = new JPanel(new FlowLayout());
		dealerCardPanel = new JPanel(new FlowLayout());
		
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
	
	public void updateHand(String summaryString, String actionString) {
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
			firstLabel.setText(summaryString);
			secondLabel.setText(actionString);
			labelPanel.add(secondLabel);

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
	
	public void updateCards(JPanel cardPanel, List<String> cardImagePaths) {
		// Clear previous cards
		cardPanel.removeAll();
		
		// Add new cards
		for (String cardImagePath : cardImagePaths) {
			// cardImagePath = "/cards/PNG-cards-1.3/2-Ccopy.png"; // For bug fixing
			URL resourceURL = getClass().getResource(cardImagePath);
			System.out.println(resourceURL);
			if (resourceURL == null) {
				System.out.println("Resource not found: " + cardImagePath);
			} else {
				System.out.println("Resource found: " + cardImagePath);
				ImageIcon cardIcon = new ImageIcon(resourceURL);
				JLabel cardLabel = new JLabel(cardIcon);
				cardPanel.add(cardLabel);
			}
		}
		
		// Refresh panel
		cardPanel.revalidate();
		cardPanel.repaint();
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
	
	public JPanel getPlayerCardPanel() {
		return playerCardPanel;
	}
	
	public JPanel getDealerCardPanel() {
		return dealerCardPanel;
	}

}
