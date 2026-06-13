// ---------- Constants ----------
public class DiscountConstants {
    public static final double GOLD_DISCOUNT = 0.1;
    public static final double SILVER_DISCOUNT = 0.2;
    public static final double NO_DISCOUNT = 0.0;
}

// ---------- Customer data class (reduces parameter count) ----------
public class Customer {
    String name;
    String address;
    double currentDiscountTotal; // formerly 'd'
    int membershipType;          // formerly 't'
    String email;
    boolean isVip;               // formerly 'g'

    public Customer(String name, String address, int membershipType,
                     String email, boolean isVip) {
        this.name = name;
        this.address = address;
        this.membershipType = membershipType;
        this.email = email;
        this.isVip = isVip;
        this.currentDiscountTotal = 0;
    }
}

// ---------- Functional: validate order data ----------
public class OrderValidator {
    public static void validateOrders(double[] orders, int count) {
        if (count < 0 || count > orders.length) {
            throw new IllegalArgumentException("Invalid order count: " + count);
        }
        for (int i = 0; i < count; i++) {
            if (orders[i] < 0) {
                throw new IllegalArgumentException("Order amount cannot be negative: " + orders[i]);
            }
        }
    }
}

// ---------- Functional: sum order totals ----------
public class OrderCalculator {
    public static double sumOrders(double[] orders, int count) {
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += orders[i];
        }
        return sum;
    }

    // Noun-style function (returns a value, accessor-like)
    public static double discountRate(int membershipType) {
        switch (membershipType) {
            case 1: return DiscountConstants.GOLD_DISCOUNT;
            case 2: return DiscountConstants.SILVER_DISCOUNT;
            default: return DiscountConstants.NO_DISCOUNT;
        }
    }

    public static double calculateDiscountedTotal(double sum, double discountRate) {
        return sum - (sum * discountRate);
    }
}

// ---------- Functional: build customer message ----------
public class MessageBuilder {
    public static String buildSummaryMessage(Customer customer, double total) {
        String msg = "Hello " + customer.name + " of " + customer.address
                      + ", your total is " + total;
        if (customer.isVip) {
            msg += " (VIP)";
        }
        return msg;
    }
}

// ---------- Functional: notification routines ----------
public class NotificationService {
    public static void printSummary(String message) {
        System.out.println(message);
    }

    public static void emailCustomer(String email, String message) {
        if (email != null && !email.isEmpty()) {
            sendEmail(email, message);
        }
    }

    private static void sendEmail(String email, String message) {
        // existing email-sending implementation
    }
}

// ---------- Orchestrating routine ----------
public class CustomerProcessor {
    public static double processCustomer(Customer customer, double[] orders, int orderCount) {
        OrderValidator.validateOrders(orders, orderCount);

        double sum = OrderCalculator.sumOrders(orders, orderCount);
        double rate = OrderCalculator.discountRate(customer.membershipType);
        double total = OrderCalculator.calculateDiscountedTotal(sum, rate);

        String message = MessageBuilder.buildSummaryMessage(customer, total);
        NotificationService.printSummary(message);
        NotificationService.emailCustomer(customer.email, message);

        customer.currentDiscountTotal = total; // explicitly returned/updated, not silently dropped

        return total;
    }
}