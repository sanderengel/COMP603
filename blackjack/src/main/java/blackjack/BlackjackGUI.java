package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

/*
*
* @author jassel doong
*/

public class BlackjackGUI extends JFrame {
    private Player player;
    private Dealer dealer;
    private Blackjack blackjack;
    private InputHandler inputHandler;
    private List<HandLog> hands;

    private JLabel playerBalanceLabel;
    private JTextArea gameLogArea;
    private JButton playButton, quitButton, betButton;

    private JPanel playerCardsPanel;
    private JPanel dealerCardsPanel;

    public BlackjackGUI() {
        setTitle("BlackJack");
        setSize(600, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize game components
        DBHandler.initializeDB();
        inputHandler = new InputHandler();
        hands = new ArrayList<>();

        // Create UI elements
        createComponents();

        // Custom dialog for player name input
        PlayerNameDialog nameDialog = new PlayerNameDialog(this);
        nameDialog.setVisible(true);
        String playerName = nameDialog.getPlayerName();

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

        // Player and dealer cards panels
        playerCardsPanel = new JPanel();
        dealerCardsPanel = new JPanel();
        playerCardsPanel.setBorder(BorderFactory.createTitledBorder("Your Cards"));
        dealerCardsPanel.setBorder(BorderFactory.createTitledBorder("Dealer's Cards"));
        
        // Using FlowLayout for cards
        playerCardsPanel.setLayout(new FlowLayout());
        dealerCardsPanel.setLayout(new FlowLayout());
        
        add(playerCardsPanel, BorderLayout.WEST);
        add(dealerCardsPanel, BorderLayout.EAST);

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
        // Clear previous cards before playing a new hand
        playerCardsPanel.removeAll();
        dealerCardsPanel.removeAll();

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

        // Display the cards
        displayCards();

        if (player.getBalance() <= 0) {
            appendGameLog("Sorry, you are out of money. Game over.");
            playButton.setEnabled(false);
        }
    }

    private void placeBet() {
        double bet = inputHandler.getBet(player.getBalance());
        appendGameLog("Placed bet of $" + bet);
    }

    private void displayCards() {
        // Display player cards
        for (Card card : player.getHand().getCards()) {
            playerCardsPanel.add(new JLabel(card.toString())); // Assuming Card class has a proper toString method
        }

        // Display dealer cards
        for (Card card : dealer.getHand().getCards()) {
            dealerCardsPanel.add(new JLabel(card.toString())); // Assuming Card class has a proper toString method
        }

        playerCardsPanel.revalidate(); // Refresh the panel
        playerCardsPanel.repaint(); // Repaint to show the new cards
        dealerCardsPanel.revalidate(); // Refresh the panel
        dealerCardsPanel.repaint(); // Repaint to show the new cards
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
