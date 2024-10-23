package blackjack;

import javax.swing.SwingUtilities;

public class MainGUI {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			// Initialize model, view, and controller
			ModelGUI model = new ModelGUI();
			ViewGUI view = new ViewGUI();
			ControllerGUI controller = new ControllerGUI(model, view);
		});
	}
}
