package blackjack;

import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        // Initialize and launch the Blackjack GUI
        SwingUtilities.invokeLater(() -> {
            BlackjackGUI gui = new BlackjackGUI();
            gui.createAndShowGUI(); // Call the method to set up the GUI
        });
    }
}
