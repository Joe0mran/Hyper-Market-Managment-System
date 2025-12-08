import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class productStorage {

    private String filename = "products.txt";


    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String category = parts[2];
                double price = Double.parseDouble(parts[3]);
                int quantity = Integer.parseInt(parts[4]);
                LocalDate expiry = LocalDate.parse(parts[5]);
                int damaged = Integer.parseInt(parts[6]);
                products.add(new Product(id, name, category, price, quantity, expiry, damaged));
            }
        } catch (IOException e) {

        }
        return products;
    }



    public Product searchProduct(int id) {
        for (Product p : getAllProducts()) {
            if (p.getProductId() == id)
                return p;
        }
        return null;
    }




}

