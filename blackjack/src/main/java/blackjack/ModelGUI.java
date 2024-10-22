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
	private Blackjack blackjack;
	private boolean isNewPlayer;
	private final Dealer dealer;
	private final double defaultStartingBalance;
	private double startingBalance;
	private String playerStatistics;
	private String gameStatistics;
	private String handStatistics;
	private Deck deck;
	
	public ModelGUI() {
		dealer = new Dealer();
		defaultStartingBalance = 1000.0;
		
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
	
	public void playGame() throws SQLException {
		// Increment player's number of games played
		player.incrementGamesPlayed();
		
		// Load new deck
		deck = loadDeck();
		
		// Create game log
		GameLog gameLog = new GameLog(playerName, startingBalance);
		GameDBHandler.insertGameTimestamp(gameLog.getTimestamp()); // Insert initial game timestamp to DB
		
		// Create list to store logs of played hands
		List<HandLog> hands = new ArrayList<>();

		// FINISH METHOD LATER
	}
	
	public void playHand(Gamestate gamestate) {
		// Write later
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
	
	// Method to format player statistics similar to OutputHandler
	private String formatPlayerStatistics() {
		StringBuilder sb = new StringBuilder();
		sb.append("Name: ").append(player.getName()).append("\n");
		sb.append("Current balance: $").append(startingBalance).append("\n");
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
			sb.append("Starting balance: $").append(gameLog.getStartingBalance()).append("\n");
			sb.append("Number of hands played: ").append(gameLog.getNumHands()).append("\n");
			sb.append("Number of hands won: ").append(gameLog.getNumHandsWon()).append("\n");
			sb.append("Ending balance: $").append(gameLog.getEndingBalance()).append("\n");
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
	
	public boolean isNewPlayer() {
		return isNewPlayer;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Blackjack getBlackjack() {
		return blackjack;
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
	
}
