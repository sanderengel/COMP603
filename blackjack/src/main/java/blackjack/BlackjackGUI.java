package blackjack;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class BlackjackGUI extends JFrame {

    private Player player;
    private Dealer dealer;
    private Blackjack blackjack;
    private JLabel playerCardsLabel;
    private JLabel dealerCardsLabel;
    private JLabel balanceLabel;
    private JLabel resultLabel;
    private JButton hitButton;
    private JButton standButton;
    private double bet;
    private double defaultStartingBalance = 1000.0;
    
    // Game state objects
    private Gamestate gamestate;

    public BlackjackGUI() {
        setTitle("Blackjack Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void createAndShowGUI() {
        // Set layout for the window
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1));
        
        JLabel nameLabel = new JLabel("Enter your name:");
        JTextField nameField = new JTextField();
        JButton startButton = new JButton("Start Game");

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(startButton);

        add(inputPanel, BorderLayout.CENTER);

        // When player presses the start button, start the game
        startButton.addActionListener(e -> {
            String playerName = nameField.getText();
            if (!playerName.isEmpty()) {
                startGame(playerName);
                inputPanel.setVisible(false);
            }
        });

        setVisible(true);
    }

    private void startGame(String playerName) {
        // Initialize Player, Dealer, and Blackjack objects
        player = new Player(playerName, defaultStartingBalance, 1, 0, 0);
        dealer = new Dealer();
        blackjack = new Blackjack(player, dealer, null); // null for now as inputHandler is not needed with GUI

        // Setup GUI for blackjack game
        setupGameInterface();

        // Ask player for their bet
        askForBet();
    }

    private void setupGameInterface() {
        // Create main game area
        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 2));

        // Player and dealer hands display
        playerCardsLabel = new JLabel("Player's Cards: ");
        dealerCardsLabel = new JLabel("Dealer's Cards: ");

        gamePanel.add(playerCardsLabel);
        gamePanel.add(dealerCardsLabel);

        // Player's balance display
        balanceLabel = new JLabel("Balance: $" + player.getBalance());
        gamePanel.add(balanceLabel);

        // Result label to display win/loss
        resultLabel = new JLabel("");
        gamePanel.add(resultLabel);

        // Add game panel to the frame
        add(gamePanel, BorderLayout.CENTER);

        // Add buttons for hit and stand
        JPanel actionPanel = new JPanel();
        hitButton = new JButton("Hit");
        standButton = new JButton("Stand");

        actionPanel.add(hitButton);
        actionPanel.add(standButton);
        add(actionPanel, BorderLayout.SOUTH);

        // Disable buttons until cards are dealt
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        // Add listeners for hit and stand actions
        hitButton.addActionListener(e -> playerHit());
        standButton.addActionListener(e -> playerStand());

        revalidate();
        repaint();
    }

    private void askForBet() {
        String betString = JOptionPane.showInputDialog(this, "Enter your bet (Balance: $" + player.getBalance() + "):", "Place Bet", JOptionPane.PLAIN_MESSAGE);
        try {
            bet = Double.parseDouble(betString);
            if (bet > player.getBalance() || bet <= 0) {
                JOptionPane.showMessageDialog(this, "Invalid bet! Please enter a valid amount.");
                askForBet();
            } else {
                startNewHand();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            askForBet();
        }
    }

    private void startNewHand() {
        gamestate = new Gamestate();
        blackjack.playHand(gamestate); // Play hand (dealer and player draw cards)

        // Update GUI to display initial hands
        playerCardsLabel.setText("Player's Cards: " + player.getHand().toString() + " (Sum: " + player.getSum() + ")");
        dealerCardsLabel.setText("Dealer's Visible Card: " + dealer.getVisibleCard().toString());

        hitButton.setEnabled(true);
        standButton.setEnabled(true);

        revalidate();
        repaint();
    }

    private void playerHit() {
        player.addCard(blackjack.deck.dealCard()); // Deal a card to the player
        playerCardsLabel.setText("Player's Cards: " + player.getHand().toString() + " (Sum: " + player.getSum() + ")");
        
        if (player.isBust()) {
            endRound("Bust! Dealer Wins.");
        }
        revalidate();
        repaint();
    }

    private void playerStand() {
        hitButton.setEnabled(false);
        standButton.setEnabled(false);

        // Dealer's turn logic
        dealer.revealHiddenCard(); // Reveal dealer's hidden card
        dealerCardsLabel.setText("Dealer's Cards: " + dealer.getHand().toString() + " (Sum: " + dealer.getSum() + ")");

        while (dealer.getSum() < 17) {
            dealer.addCard(blackjack.deck.dealCard()); // Dealer hits
            dealerCardsLabel.setText("Dealer's Cards: " + dealer.getHand().toString() + " (Sum: " + dealer.getSum() + ")");
        }

        // Determine the winner
        if (dealer.isBust()) {
            endRound("Dealer Bust! You Win!");
        } else if (player.getSum() > dealer.getSum()) {
            endRound("You Win!");
        } else {
            endRound("Dealer Wins.");
        }

        revalidate();
        repaint();
    }

    private void endRound(String result) {
        resultLabel.setText(result);

        // Update player's balance based on the result
        if (result.contains("Win")) {
            player.adjustBalance(bet, 1.0); // Payout of 1:1
        } else {
            player.adjustBalance(bet, -1.0); // Player loses bet
        }

        balanceLabel.setText("Balance: $" + player.getBalance());

        // Ask if the player wants to play again
        if (player.getBalance() > 0) {
            int playAgain = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Play Again", JOptionPane.YES_NO_OPTION);
            if (playAgain == JOptionPane.YES_OPTION) {
                askForBet();
            } else {
                JOptionPane.showMessageDialog(this, "Thanks for playing!");
                System.exit(0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "You are out of money. Game over.");
            System.exit(0);
        }
    }
}


/*
package blackjack;

import java.util.List;
import java.util.ArrayList;

public class Main {
	
    public static void main(String[] args) {
		
		// Define default starting balance
		Double defaultStartingBalance = 1000.0;
		
		// Create instance of InputHandler
		InputHandler inputHandler = new InputHandler();
		
		// Connect to DB
		DBHandler.initializeDB();
		
		System.out.println("Welcome to BlackJack!");
		System.out.println("You can quit the game at any time by entering 'Q' or 'QUIT'.\n");
		
		// Get player name
		String playerName = inputHandler.getName();
		
//		// Create instance of Player and Dealer
//		Player player = new Player(playerName, startingBalance);
//		Dealer dealer = new Dealer();

		// Get player if already in database, otherwise create new player
		Player player = PlayerDBHandler.getPlayer(playerName);
		if (player == null) {
			// Player does not exist, initialize new one
			System.out.print("We see this is your first time here! You start with a balance of " + defaultStartingBalance + ".\n");
			player = new Player(playerName, defaultStartingBalance, 1, 0, 0);
		} else {
			// Player already exists
			System.out.println("Welcome back! Your current balance with us is " + player.getBalance() + ".");
			// Ask if player wants to check their statistics
			System.out.println("Before we begin, would you like to check your records with us? (Y or N)");
			if (inputHandler.getConfirmation()) {
				OutputHandler.displayPlayerStatistics(player);
				System.out.println("Would you like to also check records for specific hands? (Y or N)");
				if (inputHandler.getConfirmation()) {
					OutputHandler.displayGameStatistics(player);
				}
			}
		}
		
		// Store player's starting balance
		double startingBalance = player.getBalance();
		
		// Create instance of Dealer
		Dealer dealer = new Dealer();
		
		// Create instance of Blackjack class
		Blackjack blackjack = new Blackjack(player, dealer, inputHandler);
		
		// Create game log
		GameLog gameLog = new GameLog(null, playerName, startingBalance);
		
		// Create list to store logs of played hands
		List<HandLog> hands = new ArrayList<>(); 
		
		System.out.println("Are you ready? (Y or N)");
		
		// Run games continuously
		while (true) {
			// Check if player wants to play (again)
			if (!inputHandler.getConfirmation()) {
				break;
			}
			
			// Get player bet
			double bet = inputHandler.getBet(player.getBalance());
			System.out.println("Great, your bet is $" + OutputHandler.toIntIfPossible(bet) + ". Let's begin!");
			
			// Initialize new handLog
			HandLog handLog = new HandLog(player.getBalance(), bet);
			
			// Initialize new gamestate
			Gamestate gamestate = new Gamestate();
			
			// play hand of blackjack, updating all needed information inside gamestate
			blackjack.playHand(gamestate);

			// Update player balance
			player.adjustBalance(bet, gamestate.getPayoutMultiplier());
			
			// Update player played and won hands
			player.incrementHandsPlayed();
			player.updateHandsWon(gamestate.getPayoutMultiplier());
			
			// Pass information to hand log
			handLog.fillHandLog(player, dealer, gamestate);

			// Append hand log to list of played hands
			hands.add(handLog);
			
			// Display result and player balance
			OutputHandler.displayResult(gamestate);
			OutputHandler.displayPlayerBalance(player);
			
			// If enough funds, ask to play again
			if (player.getBalance() > 0) {
				// Actual confirmation is checked on start of loop again
				System.out.println("\nWould you like to play again? (Y or N)");
			} else {
				System.out.println("\nSorry, you are out of money. Game over.");
				break;
			}
		}
		
		// Save hand list to game log and save game log
		gameLog.setHands(hands);
		gameLog.saveGameLog();
		
		// Update player information in database
		PlayerDBHandler.addOrUpdatePlayer(player);
    }
}

*/