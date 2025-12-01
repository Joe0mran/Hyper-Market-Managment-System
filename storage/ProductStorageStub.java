public class ProductStorageStub {
    private ArrayList<Product> products = new ArrayList<>();

    public void addProduct(Product p) { products.add(p); }

    public void removeProduct(Product p) {
        products.remove(p);
    }
// update()

    public Product searchProduct(int id) {
        for (Product p : products)
            if (p.getProductId() == id) return p;
        return null;
    }

    public ArrayList<Product> getAllProducts() {
        return products;
    }
}
