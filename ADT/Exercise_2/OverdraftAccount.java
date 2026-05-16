/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT.Exercise_2;

/**
 *
 * @author USER
 */
public class OverdraftAccount extends BankAccount {

    private final double OVERDRAFT_LIMIT = -500;

    public OverdraftAccount(double balance) {
        super(balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
            System.out.println("New balance: " + balance);
        }
    }

    @Override
    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        // Allow overdraft up to -500
        if ((balance - amount) >= OVERDRAFT_LIMIT) {
            balance -= amount;

            System.out.println("Withdrawn: " + amount);
            System.out.println("New balance: " + balance);

        } else {
            System.out.println("Overdraft limit exceeded.");
        }
    }
}
