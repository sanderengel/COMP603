package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class BlackjackGUI extends JFrame {
    private Player player;
    private Dealer dealer;
    private Blackjack blackjack;
    private InputHandler inputHandler;
    private List<HandLog> hands;
    
    private JLabel playerBalanceLabel;
    private JTextArea gameLogArea;
    private JButton playButton, quitButton, betButton;

    public BlackjackGUI() {
        setTitle("BlackJack Game");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize game components
        DBHandler.initializeDB();
        inputHandler = new InputHandler();
        hands = new ArrayList<>();

        // Create UI elements
        createComponents();

        // Get player name from dialog
        String playerName = JOptionPane.showInputDialog(this, "Enter your name:");

        // Get player from DB or create new one
        player = PlayerDBHandler.getPlayer(playerName);
        if (player == null) {
            player = new Player(playerName, 1000.0, 1, 0, 0); // Default balance
        }
        dealer = new Dealer();
        blackjack = new Blackjack(player, dealer, inputHandler);

        updatePlayerBalance();
        appendGameLog("Welcome, " + player.getName() + "! Starting balance: $" + player.getBalance());
    }

    private void createComponents() {
        // Player balance label
        playerBalanceLabel = new JLabel("Balance: $1000");
        add(playerBalanceLabel, BorderLayout.NORTH);

        // Game log area
        gameLogArea = new JTextArea();
        gameLogArea.setEditable(false);
        add(new JScrollPane(gameLogArea), BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        playButton = new JButton("Play");
        quitButton = new JButton("Quit");
        betButton = new JButton("Place Bet");

        buttonPanel.add(betButton);
        buttonPanel.add(playButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Set up action listeners
        playButton.addActionListener(new PlayAction());
        quitButton.addActionListener(e -> System.exit(0));
        betButton.addActionListener(new BetAction());
    }

    private class PlayAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playGame();
        }
    }

    private class BetAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            placeBet();
        }
    }

    private void playGame() {
        // Game logic (playing a hand)
        Gamestate gamestate = new Gamestate();
        blackjack.playHand(gamestate);
        
        // Adjust balance based on result
        double bet = inputHandler.getBet(player.getBalance());
        player.adjustBalance(bet, gamestate.getPayoutMultiplier());

        // Log game result and update UI
        HandLog handLog = new HandLog(player.getBalance(), bet);
        handLog.fillHandLog(player, dealer, gamestate);
        hands.add(handLog);

        updatePlayerBalance();
        OutputHandler.displayResult(gamestate);
        appendGameLog("Player balance after game: $" + player.getBalance());

        if (player.getBalance() <= 0) {
            appendGameLog("Sorry, you are out of money. Game over.");
            playButton.setEnabled(false);
        }
    }

    private void placeBet() {
        double bet = inputHandler.getBet(player.getBalance());
        appendGameLog("Placed bet of $" + bet);
    }

    private void updatePlayerBalance() {
        playerBalanceLabel.setText("Balance: $" + player.getBalance());
    }

    private void appendGameLog(String text) {
        gameLogArea.append(text + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI mainGUI = new BlackjackGUI();
            mainGUI.setVisible(true);
        });
    }
}
