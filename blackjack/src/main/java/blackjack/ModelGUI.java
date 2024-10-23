package blackjack;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author sanderengelthilo
 */
public class ModelGUI {
	private Player player;
	private String playerName;
	private boolean isNewPlayer;
	private final Dealer dealer;
	private final double defaultStartingBalance;
	private double startingBalance;
	private String playerStatistics;
	private String gameStatistics;
	private String handStatistics;
	private Deck deck;
	private GameLog gameLog;
	private List<HandLog> hands;
	private HandLog handLog; // Will continuously update every hand
	private double bet;
	private double payoutMultiplier;
	private String resultText;
	
	public ModelGUI() {
		dealer = new Dealer();
		defaultStartingBalance = 1000.0;
		payoutMultiplier = 0.0;
		
		// Connect to DB
		DBHandler.initializeDB();
	}
	
	public void registerPlayer(String playerName) {
		this.playerName = playerName;
		
		// Fetch player
        player = PlayerDBHandler.getPlayer(playerName);

		// Check if the player already exists
        if (player == null) {
            // New player
            player = new Player(playerName, defaultStartingBalance, 0, 0, 0);
			PlayerDBHandler.addOrUpdatePlayer(player); // Add new player to DB
            isNewPlayer = true;
        } else {
            // Existing player
            isNewPlayer = false;
        }
		
		// Update player's starting balance
		startingBalance = player.getBalance();
		
		// Update statistics
		playerStatistics = formatPlayerStatistics();
		gameStatistics = formatGameStatistics();
		handStatistics = formatHandStatistics();
    }
	
	public void startGame() throws SQLException {
		// Increment player's number of games played
		player.incrementGamesPlayed();
		
		// Load new deck
		deck = loadDeck();
		
		// Create game log
		gameLog = new GameLog(playerName, startingBalance);
		GameDBHandler.insertGameTimestamp(gameLog.getTimestamp()); // Insert initial game timestamp to DB
		
		// Create list to store logs of played hands
		hands = new ArrayList<>();
	}
	
	private Deck loadDeck() {
		// Load deck input stream
		try {
			// Load resource file
			InputStream inputStream = Blackjack.class.getResourceAsStream("/cards.txt");
			if (inputStream == null) {
				throw new IOException("Resource file not found");
			}
			// return new deck with input stream
			deck = new Deck(inputStream);

		} catch (IOException e) {
			System.out.println("Error loading deck: " + e.getMessage());
		}
		
		return deck;
	}
	
	public void startHand(double bet) {
		this.bet = bet;
		player.incrementHandsPlayed(); // Increment player's number of hands played
		handLog = new HandLog(gameLog.getTimestamp(), player.getBalance(), bet); // Initialize new handLog
		dealCards(); // Deal cards
	}
	
	private void dealCards() {
		// Initialize new empty hands for player and dealer
		player.addHand();
		dealer.addHand();
		
		// Reset and shuffle deck
		deck.reset();
		deck.shuffle();
		
		// Deal first cards
		player.addCard(deck.dealCard());
		dealer.addCard(deck.dealCard()); // Dealer's first card is visible
		
		// Deal second cards
		player.addCard(deck.dealCard());
		dealer.setHiddenCard(deck.dealCard()); // Dealer's second card is hidden
	}
	
	public boolean checkNaturals() {
		
		// Cross check for naturals
		if (player.hasNatural()) {
			if (dealer.hasNatural()) {
				// Both player and dealer have naturals
				payoutMultiplier = 0.0;
				resultText = "Both you and " + player.getName() + " have naturals, it is a tie!";
			} else {
				// Only player has natural
				payoutMultiplier = 1.5;
				resultText = "You have a natural win!";
			}
		} else if (dealer.hasNatural()) {
			// Only dealer has natural
			payoutMultiplier = -1.0;
			resultText = dealer.getName() + " has a natural win...";
		} else {
			// No one has natural
			resultText = null;
			payoutMultiplier = 0.0;
			return false;
		}
		return true;
	}
	
	public void playerHit() {
		// Deal new card to player
		player.addCard(deck.dealCard());
		
		// Check if player is bust
		if (player.isBust()) {
			payoutMultiplier = -1.0;
			resultText = "You bust...";
		}
	}
	
	public void dealerHit() {
		dealer.addCard(deck.dealCard());
	}
	
	public void determineWinner() {
		// Dealer busts
		if (dealer.isBust()) {
			payoutMultiplier = 1.0;
			resultText = dealer.getName() + " busts, you win!";
		}
		// Player's sum is higher than dealer's
		else if (player.getSum() > dealer.getSum()) {
			payoutMultiplier = 1.0;
			resultText = "Your sum is higher than " + dealer.getName() + "'s, you win!";
		}
		// Player's sum is lower than dealer's
		else if (player.getSum() < dealer.getSum()) {
			payoutMultiplier = -1.0;
			resultText = dealer.getName() + "'s sum is higher than yours, you lose...";
		}
		// Same sum
		else {
			payoutMultiplier = 0.0;
			resultText = dealer.getName() + "'s sum and yours are equal, it's a draw.";
		}
	}
	
	public void endHand() throws SQLException {
		// Update player's balance
		player.adjustBalance(bet, payoutMultiplier);
		
		// Increment player's amount hands won if player won
		player.updateHandsWon(payoutMultiplier);
		
		// Update number of won hands in game log
		gameLog.updateHandsWon(payoutMultiplier);
		
		// Handle hand log
		fillHandLog();
		hands.add(handLog); // Append hand log to list of played hands
		HandDBHandler.insertHand(handLog); // Save hand log to DB
	}
	
	public void fillHandLog() {
		handLog.setResult(resultText);
		handLog.setPlayerHand(player.getHand().toString());
		handLog.setDealerHand(dealer.getHand().toString());
		handLog.setPlayerHandSum(player.getSum());
		handLog.setDealerHandSum(dealer.getSum());
		handLog.setPlayerNatural(player.hasNatural());
		handLog.setDealerNatural(player.hasNatural());
		handLog.setBalanceAfterHand(player.getBalance());
	}
	
	public void endGame() throws SQLException {
		// Save hand list and ending balance to game log
		gameLog.setHands(hands);
		gameLog.setEndingBalance(player.getBalance());

		// Save game log to .txt
		gameLog.saveGameLog();

		// Update player information
		PlayerDBHandler.addOrUpdatePlayer(player);

		// Update game log to DB
		GameDBHandler.updateGame(gameLog);
	}
	
	// Method to format player statistics similar to OutputHandler
	private String formatPlayerStatistics() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ").append(player.getName()).append("\n");
		sb.append("Current balance: $").append(toIntIfPossible(startingBalance)).append("\n");
		sb.append("Total games played: ").append(player.getGamesPlayed()).append("\n");
		sb.append("Total hands played: ").append(player.getHandsPlayed()).append("\n");
		sb.append("Total hands won: ").append(player.getHandsWon()).append("\n");
		return sb.toString();
	}
	
	// Method to format games statistics similar to OutputHandler
	private String formatGameStatistics() {
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
			sb.append("Starting balance: $").append(toIntIfPossible(gameLog.getStartingBalance())).append("\n");
			sb.append("Number of hands played: ").append(gameLog.getNumHands()).append("\n");
			sb.append("Number of hands won: ").append(gameLog.getNumHandsWon()).append("\n");
			sb.append("Ending balance: $").append(toIntIfPossible(gameLog.getEndingBalance())).append("\n");
			sb.append("\n"); // Add a blank line between game logs
		}

		return sb.toString();
	}

	private String formatHandStatistics() {
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
			sb.append("Balance before hand: $").append(toIntIfPossible(handLog.getBalanceBeforeHand())).append("\n");
			sb.append("Result: ").append(handLog.getResult()).append("\n");
			sb.append("Player's hand: ").append(handLog.getPlayerHand()).append("\n");
			sb.append("Dealer's hand: ").append(handLog.getDealerHand()).append("\n");
			sb.append("Sum of player's hand: ").append(handLog.getPlayerHandSum()).append("\n");
			sb.append("Sum of dealer's hand: ").append(handLog.getDealerHandSum()).append("\n");
			sb.append("Player had natural: ").append(handLog.isPlayerNatural()).append("\n");
			sb.append("Dealer had natural: ").append(handLog.isDealerNatural()).append("\n");
			sb.append("Balance after hand: $").append(toIntIfPossible(handLog.getBalanceAfterHand())).append("\n");
			sb.append("\n"); // Add a blank line between hand logs
		}

		return sb.toString();
	}
	
	public Number toIntIfPossible(double value) {
		if (value == (int) value) {
			return (int) value; // Return as int if double value is equivalent to an integer
		} else {
			return value; // Otherwise, return double as it is
		}
	}
	
	public boolean isNewPlayer() {
		return isNewPlayer;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Dealer getDealer() {
		return dealer;
	}
	
	public String getPlayerStatistics() {
		return playerStatistics;
	}
	
	public String getGameStatistics() {
		return gameStatistics;
	}
	
	public String getHandStatistics() {
		return handStatistics;
	}
	
	public double getBet() {
		return bet;
	}
	
	public double getPayoutMultipler() {
		return payoutMultiplier;
	}
	
	public String getResultText() {
		return resultText;
	}
}
