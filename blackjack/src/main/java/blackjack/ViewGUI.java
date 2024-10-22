package blackjack;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author sanderengelthilo
 */
public class ViewGUI {
    private JFrame frame;
    private JPanel mainPanel, labelPanel, inputPanel, actionPanel;
    private JLabel firstLabel, secondLabel;
    private JTextField nameInput;
    private JButton nameButton, startPlayingButton, viewRecordsButton;

    public ViewGUI() {
        // Create the frame
        frame = new JFrame("Blackjack Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

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
        // Remove introInputPanel after player enters their name
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

        // Add "Start Playing" button for both new and returning players
        startPlayingButton = new JButton("Start Playing");
        actionPanel.add(startPlayingButton);

        // Add "View Records" button only for returning players
        if (!isNewPlayer) {
            viewRecordsButton = new JButton("View Records");
            actionPanel.add(viewRecordsButton);
        }

        // Add the action panel below the introPanel
        mainPanel.add(actionPanel, BorderLayout.SOUTH);

        // Refresh the frame to display the updated components
        frame.revalidate();
        frame.repaint();
    }
	
	// Method to update the panel to show records for player
	public void updateViewRecords(Player player) {
		// Write function next
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
}
