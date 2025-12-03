
import java.time.LocalDate;

public class Offer {

    private int offerID;
    private String offerDescription;
    private double discountValue;
    private LocalDate ExpiryDate;

    public Offer(int offerID, String offerDescription, double discountValue, LocalDate ExpiryDate) {
        //call validate_date function here.

        this.offerID = offerID;
        this.offerDescription = offerDescription;
        this.discountValue = discountValue;
        this.ExpiryDate = ExpiryDate;
    }

    public int getOfferID() {
        return offerID;
    }

    public void setOfferID(int offerID) {
        this.offerID = offerID;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDate getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(LocalDate ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }

}
