package blackjack;

import java.util.Scanner;

/**
 *
 * @author sanderengelthilo
 * @author Jassel Doong
 */
public class InputHandler {
	private Scanner scanner;
	
	public InputHandler() {
		scanner = new Scanner(System.in);
	}
	
	public String getAction() {
		System.out.println("Do you wish to HIT (H) or STAY (S)? ");
		String action = scanner.nextLine();
		return action.toUpperCase();
	}
}
