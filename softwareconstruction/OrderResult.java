/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package softwareconstruction;

/**
 *
 * @author USER
 */

public class OrderResult {
    private final boolean success;
    private final String trackingNumber;
    private final String message; 

    public OrderResult(boolean success, String trackingNumber, String message) {
        this.trackingNumber = null;
        this.success = false;
        this.message = null;
    }
    
    public String getTrackingNumber() {
        return trackingNumber;
    }
    public boolean isSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    
    
    
    
}
