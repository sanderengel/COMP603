package blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.List;
/**
 *
 * @author sanderengelthilo
 */
public class ViewGUI {
    private final JFrame frame;
	private final JPanel mainPanel, labelPanel, inputPanel;
    private JPanel actionPanel;
    private final JLabel firstLabel, secondLabel;
    private final JTextField nameInput;
	private final JButton nameButton, viewRecordsButton, viewGamesButton, viewHandsButton, startPlayingButton;
	private final JTextArea textArea;
	private final JScrollPane scrollPane;

    public ViewGUI() {
        // Create the frame
        frame = new JFrame("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(550, 300);

		// Create main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
		
		// Create vertical JPanel to hold labels
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

		// Create a panel for input/button, aligned at the center
        inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
		
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
		
		// Create start playing button for later		
		startPlayingButton = new JButton("Start Playing");
		
		// Center-align the labels within the BoxLayout
        firstLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        secondLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Add labels to labelPanel
		labelPanel.add(firstLabel);
		labelPanel.add(secondLabel);
		
		// Add input/button to introInputPanel
		inputPanel.add(nameInput);
        inputPanel.add(nameButton);
		
        // Add subpanels to the main panel
        mainPanel.add(labelPanel, BorderLayout.NORTH);
		mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Add mainPanel to the frame
        frame.add(mainPanel);

        // Make frame visible
        frame.setVisible(true);
    }

    // Method to update the panel for new/returning players
    public void updatePlayerStatus(boolean isNewPlayer, double balance) {
        // Remove inputPanel after player enters their name
		mainPanel.remove(inputPanel);

		// Create action panel for buttons
        actionPanel = new JPanel(new FlowLayout());

        // Message for new or returning players
        if (isNewPlayer) {
            firstLabel.setText("We see this is your first time here!");
			secondLabel.setText("You start with a balance of $" + balance + ".");
        } else {
            firstLabel.setText("Welcome back!");
			secondLabel.setText("Your current balance with us is $" + balance + ".");
        }

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
	public void updateViewRecords(Player player) {
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
			textArea.setText(formatPlayerStatistics(player));

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


	public void updateViewGames(Player player) {
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
			textArea.setText(formatGameStatistics(player));

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

	public void updateViewHands(Player player) {
		SwingUtilities.invokeLater(() -> {
			// Remove actionPanel if it exists
			if (actionPanel != null) {
				mainPanel.remove(actionPanel);
			}

			// Update first label text
			firstLabel.setText("You have played the following hands:");

			// Remove second label text
			labelPanel.remove(secondLabel);

			// Create JTextArea with game statistics
			textArea.setText(formatHandStatistics(player));

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

	public void updateStartPlaying() {

	}
	
	// Method to format player statistics similar to OutputHandler
	private String formatPlayerStatistics(Player player) {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ").append(player.getName()).append("\n");
		sb.append("Current balance: $").append(player.getBalance()).append("\n");
		sb.append("Total games played: ").append(player.getGamesPlayed()).append("\n");
		sb.append("Total hands played: ").append(player.getHandsPlayed()).append("\n");
		sb.append("Total hands won: ").append(player.getHandsWon()).append("\n");
		return sb.toString();
	}
	
	// Method to format games statistics similar to OutputHandler
	private String formatGameStatistics(Player player) {
		StringBuilder sb = new StringBuilder();
		List<GameLog> gameLogs = GameDBHandler.getGames(player.getName());

		// Check if the player has played any games
		if (gameLogs.isEmpty()) {
			sb.append("You have not played any games.\n");
			return sb.toString(); // Return early if no games are found
		}

		// Iterate through each game log and format the statistics
		for (int i = 0; i < gameLogs.size(); i++) {
			GameLog gameLog = gameLogs.get(i);
			sb.append("Game number: ").append(i + 1).append("\n");
			sb.append("Timestamp: ").append(gameLog.getTimestamp()).append("\n");
			sb.append("Starting balance: $").append(gameLog.getStartingBalance()).append("\n");
			sb.append("Number of hands played: ").append(gameLog.getNumHands()).append("\n");
			sb.append("Number of hands won: ").append(gameLog.getNumHandsWon()).append("\n");
			sb.append("Ending balance: $").append(gameLog.getEndingBalance()).append("\n");
			sb.append("\n"); // Add a blank line between game logs
		}

		return sb.toString();
	}

	private String formatHandStatistics(Player player) {
		StringBuilder sb = new StringBuilder();
		List<HandLog> handLogs = HandDBHandler.getHands(player.getName());

		// Check if the player has played any hands
		if (handLogs.isEmpty()) {
			sb.append("You have not played any hands.\n");
			return sb.toString(); // Return early if no hands are found
		}

		// Iterate through each hand log and format the statistics
		for (int i = 0; i < handLogs.size(); i++) {
			HandLog handLog = handLogs.get(i);
			sb.append("Hand number: ").append(i + 1).append("\n");
			sb.append("Game timestamp: ").append(handLog.getGameTimestamp()).append("\n");
			sb.append("Balance before hand: $").append(handLog.getBalanceBeforeHand()).append("\n");
			sb.append("Result: ").append(handLog.getResult()).append("\n");
			sb.append("Player's hand: ").append(handLog.getPlayerHand()).append("\n");
			sb.append("Dealer's hand: ").append(handLog.getDealerHand()).append("\n");
			sb.append("Sum of player's hand: ").append(handLog.getPlayerHandSum()).append("\n");
			sb.append("Sum of dealer's hand: ").append(handLog.getDealerHandSum()).append("\n");
			sb.append("Player had natural: ").append(handLog.isPlayerNatural()).append("\n");
			sb.append("Dealer had natural: ").append(handLog.isDealerNatural()).append("\n");
			sb.append("Balance after hand: $").append(handLog.getBalanceAfterHand()).append("\n");
			sb.append("\n"); // Add a blank line between hand logs
		}

		return sb.toString();
	}

    // Getter methods for buttons to allow Controller to add functionality
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

}
