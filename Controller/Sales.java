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
    }}
//    public createOrder(){
//
//     }
//    public cancelOrder(){
//
//     }
//}