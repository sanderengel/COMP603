package blackjack;

import javax.swing.SwingUtilities;
/**
 *
 * @author sanderengelthilo
 */
public class MainGUI {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			// Initialize model, view, and controller
			ModelGUI model = new ModelGUI("JohnDoe", 1000.0);
			ViewGUI view = new ViewGUI();
			ControllerGUI controller = new ControllerGUI(model, view);
		});
	}
}
