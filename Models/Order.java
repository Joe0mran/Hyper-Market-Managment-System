import java.time.LocalDate;
import java.util.ArrayList;

public class Order {
    /* Fields */
    private int orderId;
    private ArrayList<Order> productList;
    private double totalPrice;
    private LocalDate orderDate;
    private String status;
    /* Constructors */
    public Order(){
        this.productList = new ArrayList<>();
        this.orderDate = LocalDate.now();
        this.status = "Pending";
    }
    public Order(int orderId, ArrayList<Order> productList, double totalPrice, LocalDate orderDate, String status){
        this.orderId = orderId;
        this.productList = productList;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.status = status;
    }
    /* Getters */
    public int getOrderId() {
        return orderId;
    }
    public ArrayList<Order> getProductList() {
        return productList;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public LocalDate getOrderDate() {
        return orderDate;
    }
    public String getStatus() {
        return status;
    }
    /* Setters */
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public void setProductList(ArrayList<Order> productList) {
        this.productList = productList;
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    /* toString */
    @Override
    public String toString() {
        return "Order {" + "OrderId=" + orderId +
                ", ProductCount=" + (productList != null ? productList.size() : 0) +
                ", TotalPrice=" + totalPrice + ", OrderDate=" + orderDate +
                ", Status='" + status + '\'' + '}';
    }
}
