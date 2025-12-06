
import java.util.ArrayList;
import java.util.List;

public class InventoryStorageStub {

    private List<Product> products;

    public InventoryStorageStub() {
        products = new ArrayList<>();
        products.add(new Product(1, "Milk", "Dairy", 20.0, 10, null, 0));
        products.add(new Product(2, "Rice", "Grains", 45.0, 25, null, 0));
        products.add(new Product(3, "Chocolate", "Snacks", 12.0, 5, null, 0));
    }

    public List<Product> getProducts() {
        return products;
    }

    public void saveProducts(List<Product> updatedList) {
        this.products = updatedList;
    }
}
