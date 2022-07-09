import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

interface IWithdrawalQuantity {
    public Dispenser dispenser = null;
    public double quantity = 0;
}

class WithdrawalQuantity implements IWithdrawalQuantity {
    public Dispenser dispenser;
    public double quantity;

    public WithdrawalQuantity(Dispenser dispenser, double quantity) {
        this.dispenser = dispenser;
        this.quantity = quantity;
    }

    public static WithdrawalQuantity main(Dispenser dispenser, double quantity) {
        WithdrawalQuantity withdrawalQuantity = new WithdrawalQuantity(dispenser, quantity);

        return withdrawalQuantity;
    }
}

/**
 * ATM Machine
 */
public class ATM {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    private List<Dispenser> dispensers = new ArrayList<Dispenser>();
    private static Comparator<Dispenser> sortDispenser;

    /**
     * Initialisation of static comparator for sorting the dispensers by amount (reversed)
     */
    static {
        sortDispenser = new Comparator<Dispenser>() {
            public int compare(Dispenser d1, Dispenser d2) {
                return Double.compare(d1.amount, d2.amount) * -1;
            }
        };
    }

    // Constructor
    public ATM(String[] args) {
        // Use this to handle initialisation of the ATM
        for (int i = 0; i < args.length; i += 2) {
            // Initialise each dispenser using the provided args
            double value = Double.parseDouble(args[i]);
            int initialInventory = Integer.parseInt(args[i + 1]);

            Dispenser dispenser = new Dispenser(initialInventory, value);
            this.dispensers.add(dispenser);

            System.out.println("$" + String.format("%.0f", value) + " Dispenser ready");
        }

        // Sort the dispensers for easy iteration
        Collections.sort(this.dispensers, sortDispenser);
        System.out.println(this.dispensers.size() + " dispensers are available");
    }

	public static void main(String[] args) {
        // Run the program
        System.out.println("Initialising ATM ..."); 
        ATM atm = new ATM(args);
        System.out.println("ATM is ready!");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Request a withdrawal amount
            System.out.println("");
            System.out.println("What would you like to do?");
            System.out.println("  1. Withdraw Cash");
            System.out.println("  2. View Inventory Summary");
            System.out.println("  3. Exit");
            System.out.print("Action: ");

            String action = scanner.next();
            if (action.charAt(0) == '1') {
                System.out.print("Enter an amount to withdraw: $");
                int withdrawalAmount = scanner.nextInt();
                System.out.println("");

                // If the amount is -1, exit the program
                if (withdrawalAmount == -1) {
                    break;
                }

                System.out.println("Attempting to withdraw $" + withdrawalAmount + " ...");
                ArrayList<WithdrawalQuantity> withdrawalDispensers = atm.findWithdrawalOption(withdrawalAmount);
            
                // Withdraw the amount if there are no errors
                atm.withdraw(withdrawalAmount, withdrawalDispensers);
            } else if (action.charAt(0) == '2') {
                atm.logDispenserStatuses();
            } else if (action.charAt(0) == '3') {
                System.out.println("");
                System.out.println("ATM shutting down ...");
                System.exit(1);
            } else {
                System.out.println("");
                System.out.println(ANSI_RED + "Invalid action provided, must be one of 1, 2 or 3." + ANSI_RESET);
                System.out.println("");
            }
        }

        scanner.close();
	}

    /**
     * Find a single withdrawal option that can be used
     * @param withdrawalAmount The amount to be withdrawn, Integer
     */
    private ArrayList<WithdrawalQuantity> findWithdrawalOption(int withdrawalAmount) {
        // Find the dispensers required to fulfill the withdrawal
        ArrayList<WithdrawalQuantity> withdrawalDispensers = new ArrayList<WithdrawalQuantity>();

        for (Dispenser dispenser: this.dispensers) {
            if (dispenser.isViableWithdrawalAmount(withdrawalAmount)) {
                // Determine the quantity to be withdrawn from the given Dispenser
                double quantity = dispenser.determineWithdrawalQuantity(withdrawalAmount);

                // Save the dispenser and quantity for the final withdrawal
                WithdrawalQuantity test = new WithdrawalQuantity(dispenser, quantity);
                withdrawalDispensers.add(test);
            }
        }

        return withdrawalDispensers;
    }

    /**
     * Withdraws the requested amount from the provided dispensers with the given quantities
     * @param dispensers
     */
    private void withdraw(double withdrawalAmount, ArrayList<WithdrawalQuantity> withdrawalPortions) {
        double successfullyWithdrawnAmount = 0;
        
        // Withdraw the appropriate amount from each dispenser
        for (WithdrawalQuantity withdrawalPortion: withdrawalPortions) {
            Boolean dispenserWithdrawalSucceeded = withdrawalPortion.dispenser.withdraw(withdrawalPortion.quantity);
            if (dispenserWithdrawalSucceeded) {
                successfullyWithdrawnAmount += withdrawalPortion.dispenser.amount * withdrawalPortion.quantity;
            } else {
                System.out.println(ANSI_RED + "Error! Failed to complete withdrawl." + ANSI_RESET);
                this.logDispenserStatuses();
                return;
            }
        }

        // Successfully withdrew the full amount
        System.out.println(ANSI_GREEN + "Successfully withdrew $" + String.format("%.0f", successfullyWithdrawnAmount) + "!" + ANSI_RESET);
        return;
    }

    public void logDispenserStatuses() {
        System.out.println("");
        for (Dispenser dispenser: this.dispensers) {
            String fontColour = dispenser.amount >= 10 ? ANSI_GREEN : dispenser.amount >= 5 ? ANSI_YELLOW : ANSI_RED;
            System.out.println("$" + String.format("%.0f", dispenser.amount) + " Dispenser Quantity Remaining: " + fontColour + dispenser.quantity + ANSI_RESET);
        }
        System.out.println("");
    }

    private void findWithdrawalOptions(int withdrawalAmount) {
        
        
    }
}