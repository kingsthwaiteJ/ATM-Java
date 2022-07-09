import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

interface IWithdrawalQuantity {
    public Dispenser dispenser = null;
    public double quantity = 0;
}

class WithdrawalQuantity implements IWithdrawalQuantity {
    public Dispenser dispenser;
    public double quantity;

    public static void main(Dispenser dispenser, double quantity) {
        dispenser = dispenser;
        quantity = quantity;
    }
}

/**
 * ATM Machine
 */
public class ATM {
    private Dispenser[] dispensers;
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
        for (int i = 0; i > args.length; i += 2) {
            // Initialise each dispenser using the provided args
            int initialInventory = Integer.parseInt(args[i]);
            double value = Double.parseDouble(args[i + 1]);

            Dispenser dispenser = new Dispenser(initialInventory, value);
            dispensers[dispensers.length - 1] = dispenser;
        }

        // Sort the dispensers for easy iteration
        this.dispensers = Arrays.sort(dispensers, sortDispenser);

        System.out.println(dispensers.length + " dispensers are available");
    }

	public static void main(String[] args) {
        
	}

    /**
     * Find a single withdrawal option that can be used
     * @param withdrawalAmount The amount to be withdrawn, Integer
     */
    private void findWithdrawalOption(int withdrawalAmount) {
        // Find the dispensers required to fulfill the withdrawal
        ArrayList<withdrawalQuantity> withdrawalDispensers = new ArrayList<withdrawalQuantity>();

        for (Dispenser dispenser: this.dispensers) {
            if (dispenser.isViableWithdrawalAmount(withdrawalAmount)) {
                // Determine the quantity to be withdrawn from the given Dispenser
                double quantity = dispenser.determineWithdrawalQuantity(withdrawalAmount);

                // Save the dispenser and quantity for the final withdrawal
                withdrawalQuantity test = new withdrawalQuantity(
                    dispenser, quantity
                );
                withdrawalDispensers.add(test);
            }
        }

        // Withdraw the amounts
        this.withdraw(withdrawalAmount, withdrawalDispensers);
    }

    /**
     * Withdraws the requested amount from the provided dispensers with the given quantities
     * @param dispensers
     */
    private void withdraw(double withdrawalAmount, ArrayList<withdrawalQuantity> withdrawalPortions) {
        double successfullyWithdrawnAmount = 0;
        
        // Withdraw the appropriate amount from each dispenser
        for (withdrawalQuantity withdrawalPortion: withdrawalPortions) {
            Boolean dispenserWithdrawalSucceeded = withdrawalPortion.dispenser.withdraw(withdrawalPortion.quantity);
            if (dispenserWithdrawalSucceeded) {
                successfullyWithdrawnAmount += withdrawalPortion.dispenser.amount * withdrawalPortion.quantity;
            } else {
                System.out.println("Error! Failed to withdraw full amount. Withdrew $" + successfullyWithdrawnAmount + "/$" + withdrawalAmount + ".");
                this.logDispenserStatuses();
                return;
            }
        }

        // Successfully withdrew the full amount
        System.out.println("Successfully withdrew $" + successfullyWithdrawnAmount + ".");
        this.logDispenserStatuses();
        return;
    }

    public void logDispenserStatuses() {
        for (Dispenser dispenser: this.dispensers) {
            System.out.println("$" + dispenser.amount + "Dispenser Quantity: " + dispenser.quantity);
        }
    }

    private void findWithdrawalOptions(int withdrawalAmount) {
        
        
    }
}