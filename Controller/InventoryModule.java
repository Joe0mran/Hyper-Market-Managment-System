import java.util.*;
import java.time.LocalDate;

public class InventoryModule {
    private List<Product> products; // This list comes from the Storage Stub

    public InventoryModule(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    public boolean updateProduct(int productId, Product newProductData) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == productId) {
                products.set(i, newProductData);
                return true;
            }
        }
        return false;
    }

    public boolean deleteProduct(int productId) {
        Iterator<Product> it = products.iterator();
        while (it.hasNext()) {
            if (it.next().getProductId() == productId) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public Product searchProduct(int productId) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProductId() == productId) {
                return products.get(i);
            }
        }
        return null;
    }

    public StringBuilder listProducts(){
        StringBuilder s = new StringBuilder();
        for(int i = 0 ; i < products.size(); i++){
            s.append(products.get(i));
            s.append("\n");
        }
        return s;
    }

    public List<Product> checkExpiry(){
        List<Product> p = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if(LocalDate.now().isAfter(products.get(i).getExpiryDate())){
                p.add(products.get(i));
            }
        }
        return p;
    }

    public List<Product> checkLowStock(){
        List<Product> p = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if(products.get(i).getQuantity() <= 5){
                p.add(products.get(i));
            }
        }
        return p;
    }

    public boolean processDamagedItems(int productId, int damageAmount){
        Product p = searchProduct(productId);
        if(p == null){
            return false;
        }
        p.setDamagedQuantity(p.getDamagedQuantity() + damageAmount);
        p.setQuantity(Math.max(0, p.getQuantity() - damageAmount));
        return true;
    }

    public boolean processSalesReturn(int productId, int returnAmount){
        Product p = searchProduct(productId);
        if(p == null){
            return false;
        }
        p.setQuantity(p.getQuantity() + returnAmount);
        return true;
    }
}
