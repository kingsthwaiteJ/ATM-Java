# ATM-Java
An ATM simulation program in Java. My attempt at an interview coding project.

## About the Project
This project is my attempt at an interview coding test where the requirement is to create an ATM that meets the following requirements:
- The ATM must know how many of each type of note that it has available, and the user must be able to request a summary of the inventory
- During initialisation, the user must be able to specify the inventory for each dispenser. After initialisation, the user must not be allowed to adjust the inventory without making a withdrawal
- It must support at least $20 and $50 denominations
- It must dispense legal combinations of notes
- If a withdrawal cannot be made due to insufficient funds or a valid combination of notes cannot be combined to match the requested withdrawl amount
- It is not required to present withdrawal options
- Dispensing notes from the ATM should result in the inventory being updated
- Failure to make a withdrawal for any reason should not update the inventory

## Design & Implementation
My initial thinking here is that the ATM should be the entry-point for the program, which can instantiate multiple dispensers from which transactions can be made.

Each dispenser should know the value of the note that it holds and the quantity that it currently holds.

The requirements for the project did not require any implementation for credit cards, bank accounts or authorization and as such I have not included support for those features in this iteration of the proejct.



## Usage
To compile the code, run `javac ./src/ATM.java ./src/Dispenser.java`.

Run the Java program using `java ATM <dispenser-1-amount> <dispenser-1-quantity> <dispenser-2-amount> <dispenser-2-quantity> ...`

### Actions

#### Withdraw Cash
The user provides an amount that they wish to withdraw from the ATM. If the ATM can withdraw the requested amount using one or more dispensers, then the cash will be withdrawn and the inventory will be updated. This simulates when the user makes a withdrawal from an ATM

#### View Inventory Summary
The ATM presents an inventory summary of each dispenser that it holds to the user. This feature would typically be used by ATM maintenance rather than its users, but for the purpose of this project, any user can view the inventory.

#### Exit
Exits the program. This simulates when the user is finished with the ATM.

## License

Distributed under the MIT License. See `LICENSE.txt` for more information.