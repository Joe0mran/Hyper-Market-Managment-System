import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryModule {

    private List<Product> products;
    private final String fileName = "products.txt"; // حطي المسار حسب مكان الملف عندك

    public InventoryModule() {
        products = new ArrayList<>();
        loadProductsFromFile();
    }

    // ======= Load Products from File =======
    private void loadProductsFromFile() {
        products.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // تجاهل الأسطر الفارغة
                String[] parts = line.split(";");
                if (parts.length != 6) continue; // كل سطر لازم 6 أعمدة
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                int qty = Integer.parseInt(parts[2]);
                double price = Double.parseDouble(parts[3]);
                String cat = parts[4];
                LocalDate expiry = LocalDate.parse(parts[5]);
                products.add(new Product(id, name, qty, price, cat, expiry));
            }
        } catch (IOException e) {
            System.out.println("File not found or error reading file.");
        }
    }

    // ======= Save Products to File =======
    private void saveProductsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            for (Product p : products) {
                pw.println(p.getId() + ";" + p.getName() + ";" + p.getQuantity() + ";" +
                        p.getPrice() + ";" + p.getCategory() + ";" + p.getExpiryDate());
            }
        } catch (IOException e) {
            System.out.println("Error writing file.");
        }
    }

    // ======= CRUD Operations =======
    public void addProduct(Product p) {
        products.add(p);
        saveProductsToFile();
    }

    public boolean deleteProduct(int productId) {
        for (Product p : products) {
            if (p.getId() == productId) {
                products.remove(p);
                saveProductsToFile();
                return true;
            }
        }
        return false;
    }

    public Product findById(int productId) {
        for (Product p : products) {
            if (p.getId() == productId) return p;
        }
        return null;
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public List<Product> checkExpiry() {
        List<Product> expired = new ArrayList<>();
        for (Product p : products) {
            if (LocalDate.now().isAfter(p.getExpiryDate())) expired.add(p);
        }
        return expired;
    }

    public List<Product> checkLowStock() {
        List<Product> low = new ArrayList<>();
        for (Product p : products) {
            if (p.getQuantity() <= 5) low.add(p);
        }
        return low;
    }
}

