
import java.util.Map;

public class SalesController {

    private SalesStorage storage;

    public SalesController(SalesStorage storage) {
        this.storage = storage;
    }

    public int createOrder(Map<Integer, Integer> items) {
        return storage.createOrder(items);
    }

    public void cancelOrder(int orderId) {
        storage.cancelOrder(orderId);
    }

    public SalesStorage getStorage() {
        return storage;
    }
}
