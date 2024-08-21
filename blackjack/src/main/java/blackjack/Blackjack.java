public class BlackJackGame {
    private Deck deck;
    private Dealer dealer;
    private Player player;
    private GameState gameState;
    private InputHandler inputHandler;
    private OutputHandler outputHandler;

    public BlackJackGame() {
        deck = new Deck();
        dealer = new Dealer("Dealer");
        player = new Player("Player");
        gameState = new GameState();
        inputHandler = new InputHandler();
        outputHandler = new OutputHandler();

        startGame();
    }

    private void startGame() {
        deck.shuffle();

        // Initial dealing
        dealer.addCard(deck.dealCard());
        dealer.addHiddenCard(deck.dealCard());

        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());

        outputHandler.displayInitialHands(player, dealer);

        while (!gameState.isGameOver()) {
            String action = inputHandler.getPlayerAction();
            if ("H".equalsIgnoreCase(action)) {
                player.addCard(deck.dealCard());
                outputHandler.displayPlayerHand(player);
                if (player.isBust()) {
                    gameState.setGameOver("Player busts! Dealer wins.");
                    break;
                }
            } else if ("S".equalsIgnoreCase(action)) {
                dealer.play(deck, outputHandler);
                gameState.determineWinner(player, dealer);
                break;
            }
        }

        outputHandler.displayGameResult(gameState);
    }

    public static void main(String[] args) {
        new BlackJackGame();
    }
}
