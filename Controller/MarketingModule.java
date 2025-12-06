package hypermarket;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class MarketingModule {

    private List<Offer> activeOffers;

    public MarketingModule() {
        this.activeOffers = new ArrayList<>();
    }

    public void createOffer(int offerId, String description, double discountValue, String validityDateString) {
                
        LocalDate validUntil = LocalDate.parse(validityDateString);
                
        LocalDate startDate = LocalDate.now();
      
        Offer newOffer = new Offer(offerId, description, discountValue, validUntil);
        
        activeOffers.add(newOffer);
    }

    public void sendOfferToInventory(int offerID) {
        Offer offerToSend = null;
        
        // Find the offer
        for (Offer offer : activeOffers) {
            // Use the getter from Offer.java
            if (offer.getOfferID() == offerID) {
                offerToSend = offer;
                break;
            }
        }

        if (offerToSend != null) {
            System.out.println(">>> System Message: Sending Offer ID " + offerID + " to Inventory Module...");
            // Logic to call InventoryModule would go here
        } else {
            System.out.println("Error: Offer ID " + offerID + " not found.");
        }
    }

    public void createReport() {
        System.out.println("\n--- Marketing Report ---");
        System.out.println("Total Active Offers: " + activeOffers.size());
        System.out.println("Offer Details:");
        
        if (activeOffers.isEmpty()) {
            System.out.println("No active offers available.");
        } else {
            for (Offer offer : activeOffers) {
                System.out.println("ID: " + offer.getOfferID() + 
                                   " | Desc: " + offer.getOfferDescription() + 
                                   " | Discount: " + offer.getDiscountValue() + 
                                   " | Expires: " + offer.getExpiryDate());
            }
        }
        System.out.println("------------------------\n");
    }

}
