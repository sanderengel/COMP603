package blackjack;

import java.util.Scanner;
import java.util.Locale;

public class InputHandler {
	private final Scanner scanner;
	
	public InputHandler() {
		scanner = new Scanner(System.in).useLocale(Locale.US);
	}
	
	// Method to check if user wants to quit program
	private void checkQuit(String input) {
		if (input.equalsIgnoreCase("Q") || input.equalsIgnoreCase("QUIT")) {
			System.out.println("Quitting game. Goodbye!");
			System.exit(0); // Terminate
		}
	}
	
	public String getName() {
		System.out.println("What is your name?");
		String name = scanner.nextLine();
		checkQuit(name); // Check if user wants to quit
		System.out.println("");
		System.out.println("Nice to have you, " + name + ".");
		return name;
	}
	
	public boolean getConfirmation() {
		String startConfirm = scanner.nextLine();
		checkQuit(startConfirm); // Check if user wants to quit
		switch (startConfirm.toUpperCase()) {
			case "Y":
			case "YES":
				System.out.println("");
				return true;
			case "N":
			case "NO":
				System.out.println("");
				return false;
			default:
				System.out.println("Please input a valid answer (Y or N)");
				return getConfirmation(); // Recursively call function again if input is invalid
		}
	}
	
	public double getBet(double balance) {
		double bet = 0.0;
		boolean valid = false;
		
		while (!valid) {
			try {
				System.out.println("How much would like to bet?");
				String input = scanner.nextLine(); // Read entire line as input
				checkQuit(input); // Check if user wants to quit
				bet = Double.parseDouble(input); // Attempt to parse input as double
				
				if (bet <= 0) { // Check if bet is positive
					System.out.println("Please enter a positive number.");
				} else if (bet > balance) { // Check that bet does not exceed balance
					System.out.println("You do not have enough funds to make this bet.");
				} else {
					valid = true;
				}
				
			} catch (NumberFormatException e) {
				System.out.println("Please input a numeric bet value.");
//				scanner.next(); // Clear invalid input
			}
		}
		
		System.out.println("");
		return bet;
	}
	
	public String getAction() {
		System.out.println("Do you wish to HIT (H) or STAND (S)? ");
		String action = scanner.nextLine();
		checkQuit(action); // Check if user wants to quit
		return action.toUpperCase();
	}

}
