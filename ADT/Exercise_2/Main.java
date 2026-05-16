/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ADT.Exercise_2;

/**
 *
 * @author USER
 */
public class Main {

    public static void main(String[] args) {

        OverdraftAccount account = new OverdraftAccount(100);

        System.out.println("Initial balance: " + account.getBalance());

        account.deposit(50);

        account.withdraw(120);

        account.withdraw(400);

        account.withdraw(200);

        System.out.println("Final balance: " + account.getBalance());
    }
}
