public class Sales{
   private ProductStorageStub product ;


   public Sales(ProductStorageStub product){
       this.product = product;
   }
public void listProduct(){
    System.out.println("list of product is\n");
    for (Product p : product.getAllProducts()) {
        System.out.println(p);
    }
}

    public Product searchProduct(int id) {
        Product p = product.searchProduct(id);

        if (p == null) {
            System.out.println("Product not found");
        } else {
            System.out.println("Found: " + p);
        }

        return p;
    }
  public void createOrder(int id, int quantity) {
        Product p = productStorage.searchProduct(id);
        if (p == null) {
            System.out.println("Product not found");
            return;
        }
        if (p.getQuantity() < quantity) {
            System.out.println("Not enough stock");
            return;
        }
        p.setQuantity(p.getQuantity() - quantity);
        System.out.println("Order created successfully!");
        System.out.println("Remaining stock: " + p.getQuantity());
    }

    public void cancelOrder(int id, int quantity) {
        Product p = productStorage.searchProduct(id);
        if (p == null) {
            System.out.println("Product not found");
            return;
        }
        p.setQuantity(p.getQuantity() + quantity);
        System.out.println("Order cancelled and stock restored!");
    }
}
