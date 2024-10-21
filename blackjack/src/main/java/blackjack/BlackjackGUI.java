package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BlackjackGUI extends JFrame {
    private JTextArea textArea;          // For displaying messages
    private JTextField betField;         // For entering bets
    private JButton startButton;          // Button to start a new game
    private JButton hitButton;            // Button to hit
    private JButton standButton;          // Button to stand
    private InputHandler inputHandler;    // To handle inputs
    private Player player;                // Player instance
    private Dealer dealer;                // Dealer instance
    private Blackjack blackjack;          // Blackjack instance
    private GameLog gameLog;              // Game log instance
    private List<HandLog> hands;         // To store hands
    private double defaultStartingBalance = 1000.0; // Default starting balance

    public BlackjackGUI() {
        // Setup the frame
        setTitle("Blackjack Game");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        
        betField = new JTextField(10);
        inputPanel.add(new JLabel("Enter your bet:"));
        inputPanel.add(betField);
        
        startButton = new JButton("Start Game");
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");
        hitButton.setEnabled(false);
        standButton.setEnabled(false);
        
        inputPanel.add(startButton);
        inputPanel.add(hitButton);
        inputPanel.add(standButton);
        
        add(inputPanel, BorderLayout.SOUTH);
        
        // Event listeners
        startButton.addActionListener(new StartGameListener());
        hitButton.addActionListener(new HitListener());
        standButton.addActionListener(new StandListener());

        // Initialize other components
        inputHandler = new InputHandler();
        hands = new ArrayList<>();
    }

    // Action listener for starting the game
    private class StartGameListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Connect to the database
            DBHandler.initializeDB();
            
            // Get player name
            String playerName = inputHandler.getName();  // Get player name through input handler
            
            // Get player from database
            player = PlayerDBHandler.getPlayer(playerName);
            if (player == null) {
                // Player does not exist, initialize new one
                textArea.append("Welcome to Blackjack!\n");
                textArea.append("We see this is your first time here! You start with a balance of " + defaultStartingBalance + ".\n");
                player = new Player(playerName, defaultStartingBalance, 1, 0, 0);
            } else {
                // Player already exists
                textArea.append("Welcome back! Your current balance with us is " + player.getBalance() + ".\n");
            }
            
            // Initialize Dealer and Blackjack
            dealer = new Dealer();
            blackjack = new Blackjack(player, dealer, inputHandler);
            gameLog = new GameLog(null, playerName, player.getBalance());
            textArea.append("Are you ready to play? (Y or N)\n");

            // Enable the hit and stand buttons
            hitButton.setEnabled(true);
            standButton.setEnabled(true);
        }
    }

    // Action listener for hitting
    private class HitListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Logic for hitting
            // Update game state, player balance, etc.
            textArea.append("Player hits.\n");
            // Call appropriate methods in blackjack to handle the hit
            blackjack.hit();
            // Display results
            displayGameStatus();
        }
    }

    // Action listener for standing
    private class StandListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Logic for standing
            textArea.append("Player stands.\n");
            // Call appropriate methods in blackjack to handle the stand
            blackjack.stand();
            // Display results
            displayGameStatus();
        }
    }

    private void displayGameStatus() {
        // Update the GUI with the current game status
        // Display the player's balance, dealer's hand, results, etc.
        OutputHandler.displayPlayerBalance(player);  // Custom method to output balance to textArea
        // ... (more status updates)
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI gui = new BlackjackGUI();
            gui.setVisible(true);
        });
    }
}
