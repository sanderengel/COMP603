package blackjack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackGUI extends JFrame {
    private Player player;
    private Dealer dealer;
    private InputHandler inputHandler;
    private Blackjack blackjack;
    private JTextArea textArea;
    private JTextField inputField;
    private JButton submitButton;
    
    public BlackjackGUI(Player player, Dealer dealer, InputHandler inputHandler) {
        this.player = player;
        this.dealer = dealer;
        this.inputHandler = inputHandler;

        // Set up the frame
        setTitle("Blackjack Game");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        inputField = new JTextField();
        submitButton = new JButton("Submit");
        
        // Add components to the frame
        add(scrollPane, BorderLayout.CENTER);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(submitButton, BorderLayout.EAST);
        add(panel, BorderLayout.SOUTH);

        // Action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput(inputField.getText());
                inputField.setText("");
            }
        });

        setVisible(true);
    }

    private void processInput(String input) {
        // Handle input commands
        if (input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("QUIT")) {
            textArea.append("Thank you for playing!\n");
            System.exit(0);
        } else {
            // Process the input (e.g., betting, playing a hand)
            // You will need to implement the specific game logic here
            // For example:
            if (input.startsWith("BET ")) {
                try {
                    double bet = Double.parseDouble(input.split(" ")[1]);
                    // Validate and place bet
                } catch (NumberFormatException ex) {
                    textArea.append("Invalid bet amount. Please enter a number.\n");
                }
            }
            // Add additional commands for other game actions (hit, stand, etc.)
        }
    }

    public static void main(String[] args) {
        // Connect to DB and initialize player
        DBHandler.initializeDB();
        InputHandler inputHandler = new InputHandler();
        String playerName = inputHandler.getName();
        Player player = PlayerDBHandler.getPlayer(playerName);
        if (player == null) {
            player = new Player(playerName, 1000.0, 1, 0, 0);
        }

        // Create dealer and Blackjack instance
        Dealer dealer = new Dealer();
        Blackjack blackjack = new Blackjack(player, dealer, inputHandler);
        
        // Launch GUI
        new BlackjackGUI(player, dealer, inputHandler);
    }
}
