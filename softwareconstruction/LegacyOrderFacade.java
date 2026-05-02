/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareconstruction;

public class LegacyOrderFacade {

    private final LegacyOrderProcessor legacyOrderProcessor;

    public LegacyOrderFacade() {
        this.legacyOrderProcessor = new LegacyOrderProcessor();
    }

    public void placeOrder(String customerEmail,
                           String itemCode,
                           double amount,
                           String deliveryAddress) {

        legacyOrderProcessor.processOrder(
                customerEmail,
                itemCode,
                amount,
                deliveryAddress
        );
    }
}