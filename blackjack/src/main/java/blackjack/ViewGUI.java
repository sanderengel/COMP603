package blackjack;

import javax.swing.*;
/**
 *
 * @author sanderengelthilo
 */
public class ViewGUI {
	private JFrame frame;
	private JButton betButton;
	private JTextField betInput;
	private JLabel balanceLabel;
	private JLabel gameResultLabel;
	
	public ViewGUI() {
		frame = new JFrame("Blackjack Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		
		betButton = new JButton("Place Bet");
		betInput = new JTextField(10);
		balanceLabel = new JLabel("Balance: ");
		gameResultLabel = new JLabel("Game Result: ");
		
		JPanel panel = new JPanel();
		panel.add(balanceLabel);
		panel.add(betInput);
		panel.add(betButton);
		panel.add(gameResultLabel);
		
		frame.add(panel);
		frame.setVisible(true);
	}
	
	// Getters for components so controller can access them
    public JButton getBetButton() {
        return betButton;
    }

    public JTextField getBetInput() {
        return betInput;
    }

    public void updateBalance(double balance) {
        balanceLabel.setText("Balance: " + balance);
    }

    public void updateGameResult(String result) {
        gameResultLabel.setText("Game Result: " + result);
    }
}
