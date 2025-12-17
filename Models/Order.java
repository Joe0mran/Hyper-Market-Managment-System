
import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private ArrayList<OrderItem> items;
    private double totalPrice;
    private LocalDate orderDate;
    private String status;

    public Order(int orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.orderDate = LocalDate.now();
        this.status = "Completed";
    }

    public void addItem(OrderItem item) {
        items.add(item);
        totalPrice += item.getTotalPrice();
    }

    public int getOrderId() { return orderId; }
    public ArrayList<OrderItem> getItems() { return items; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDate getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

