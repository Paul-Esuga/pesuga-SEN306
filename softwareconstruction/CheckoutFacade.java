/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareconstruction;

import java.time.LocalDateTime;

/**
 *
 * @author USER
 */

class Inventory {
    boolean checkStock(String productId) {
        return true;
    }

    void reserve(String productId) {
        System.out.println("Reserved " + productId);
    }

    void release(String productId) {
        System.out.println("Released " + productId);
    }
}

class Payment {
    boolean charge(String userId, double amount) {
        return true;
    }

    void refund(String userId, double amount) {
        System.out.println("Refunded " + amount);
    }
}

class Shipping {
    String createLabel(String address) {
        return "TRK" + System.currentTimeMillis();
    }

    void schedulePickup(String label) {
        System.out.println("Pickup scheduled for " + label);
    }

    boolean isAvailable() {
        return true; // for rollback demo
    }
}

class Email {
    void send(String to, String subject, String body) {
        System.out.println("Email to " + to);
    }
}

class TaxCalculator{
    public double calculateTax(String state, double price) {
        if (state.equalsIgnoreCase("CA")) {
            return price * 0.08;
        }

        return 0;
    }
}

class Logger{
    public void log(String userId, boolean success) {
        System.out.println(
            "[" + LocalDateTime.now() + "] " +
            "User: " + userId + " | " +
            (success ? "SUCCESS" : "FAIL")
        );
    }
}

public class CheckoutFacade {

    private final Inventory inventory;
    private final Payment payment;
    private final Shipping shipping;
    private final Email email;
    private final TaxCalculator taxCalculator;
    private final Logger logger;

    public CheckoutFacade() {
        this.inventory = new Inventory();
        this.payment = new Payment();
        this.shipping = new Shipping();
        this.email = new Email();
        this.taxCalculator = new TaxCalculator();
        this.logger = new Logger();
    }

    public OrderResult checkout(String userId, String productId,
                                double price, String address) {
        
        String state = "CA";
        
        double tax = taxCalculator.calculateTax(state, price);
        double totalPrice = price + tax;

        if (!inventory.checkStock(productId)) {
            logger.log(userId, false);
            return new OrderResult(false, null, "Product is out of stock.");
        }

        inventory.reserve(productId);

        if (!payment.charge(userId, price)) {
            inventory.release(productId);
            logger.log(userId, false);
            return new OrderResult(false, null, "Payment failed. Product reservation released.");
        }

        if (!shipping.isAvailable()) {
            payment.refund(userId, price);
            inventory.release(productId);
            logger.log(userId, false);
            return new OrderResult(false, null, "Shipping unavailable. Payment refunded and reservation released.");
        }

        String trackingNumber = shipping.createLabel(address);
        shipping.schedulePickup(trackingNumber);

        email.send(
                userId,
                "Order Successful",
                """
                Your order was successful.
                Product price: """ + price + "\n" +
                "Tax: " + tax + "\n" +
                "Total price: " + totalPrice + "\n" +
                "Tracking number: " + trackingNumber
        );

        logger.log(userId, true);
        
        return new OrderResult(true, trackingNumber, "Checkout completed successfully.");
    }
}

