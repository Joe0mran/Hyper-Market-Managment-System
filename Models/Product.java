import  java.time.LocalDate;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;
    private String category;
    private int damagedQuantity;
    private LocalDate expiryDate;

    public Product(int id, String name, int quantity, double price,
                   String category, LocalDate expiryDate) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.expiryDate = expiryDate;
        this.damagedQuantity = 0;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public int getDamagedQuantity() { return damagedQuantity; }
    public LocalDate getExpiryDate() { return expiryDate; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setCategory(String category) { this.category = category; }
    public void setDamagedQuantity(int damagedQuantity) { this.damagedQuantity = damagedQuantity; }
    public void setExpiryDate(LocalDate expiryDate) {this.expiryDate = expiryDate;}

    @Override
    public String toString() {
        return id + ";" + name + ";" + quantity + ";" + price + ";" + category + ";" + expiryDate;
    }
}
