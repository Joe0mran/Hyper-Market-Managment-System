import java.util.*;
import java.time.LocalDate;

public class Product {
    private int productId;
    private String productName;
    private String category;
    private double price;
    private int quantity;
    private LocalDate expiryDate;
    private int damagedQuantity;

    public Product() {
        this.productId = 0;
        this.productName = "Unknown";
        this.category = "General";
        this.price = 0.0;
        this.quantity = 0;
        this.expiryDate = LocalDate.now();
        this.damagedQuantity = 0;
    }

    public Product(int productId, String productName, String category, double price, int quantity, LocalDate expiryDate,
            int damagedQuantity) {
        setProductId(productId);
        setProductName(productName);
        setCategory(category);
        setPrice(price);
        setQuantity(quantity);
        setExpiryDate(expiryDate);
        setDamagedQuantity(damagedQuantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getDamagedQuantity() {
        return damagedQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setPrice(double price) {
        if (price >= 0) {
            this.price = price;
        }
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDamagedQuantity(int damagedQuantity) {
        if (damagedQuantity >= 0 && damagedQuantity <= this.quantity) {
            this.damagedQuantity = damagedQuantity;
        }
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

}
