
import java.time.LocalDate;

public class Offer {

    private int offerID;
    private String offerDescription;
    private double discountValue;
    private LocalDate manufactureDate;
    private LocalDate ExpiryDate;

    public Offer(int offerID, String offerDescription, double discountValue, LocalDate manufactureDate, LocalDate ExpiryDate) {
        //call validate_date function here.

        this.offerID = offerID;
        this.offerDescription = offerDescription;
        this.discountValue = discountValue;
        this.manufactureDate = manufactureDate;
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

    public LocalDate getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(LocalDate manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public LocalDate getExpiryDate() {
        return ExpiryDate;
    }

    public void setExpiryDate(LocalDate ExpiryDate) {
        this.ExpiryDate = ExpiryDate;
    }

}
