/**
 * ATM Machine Dispenser
 */
public class Dispenser {
    // Types
    public enum DispenserType {
        NOTES, COINS
    };

    // Variables
    public int quantity = 0;
    public double amount = 0;
    public DispenserType type;

    // Constructor
    public Dispenser(int initialQuantity, double value) {
        quantity = initialQuantity;
        amount = value;
        type = value >= 5 ? DispenserType.NOTES : DispenserType.COINS;
    }

    // Main Method
    public void main(int initialQuantity, double value) {   
        quantity = initialQuantity;
        amount = value;
    }

    // Retrieve the quantity of notes/coins available
    public int checkQuantity() {
        return quantity;
    }

    // Check if the requested quantity can be withdrawn
    public boolean canWithdrawQuantity(double quantity) {
        return quantity >= quantity;
    }

    // Check if the requested quantity can be withdrawn without using any other dispensers
    public boolean cleanWithdrawal(int withdrawalAmount) {
        return ((withdrawalAmount / amount) % 1) == 0;
    }

    // Check if the requested withdrawal amount is greater or equal to the amount this dispenser holds
    public boolean isViableWithdrawalAmount(int withdrawalAmount) {
        return Math.floor(withdrawalAmount / amount) > 0;
    }

    // Determine what quantity can be withdrawn
    public double determineWithdrawalQuantity(int withdrawalAmount) {
        return Math.floor(amount / withdrawalAmount);
    }

    // Withdraw the specified quantity
    public boolean withdraw(double withdrawalQuantity) {
        // Confirm that the requested quantity can be withdrawn
        if (!this.canWithdrawQuantity(withdrawalQuantity) || withdrawalQuantity == 0) {
            return false;
        } else {
            System.out.println("Dispensed " + String.format("%.0f", withdrawalQuantity) + " x $" + String.format("%.0f", this.amount) + " notes");
            quantity -= withdrawalQuantity;
            return true;
        }
    }
}