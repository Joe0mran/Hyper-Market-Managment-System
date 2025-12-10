import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class inventoryGui extends JFrame {

    private salesStorage storage = new salesStorage();
    private InventoryStorage inventory;

    private JTextField idField = new JTextField(10);
    private JTextField nameField = new JTextField(10);
    private JTextField categoryField = new JTextField(10);
    private JTextField priceField = new JTextField(10);
    private JTextField quantityField = new JTextField(10);
    private JTextField expiryField = new JTextField(10);
    private JTextField actionQuantityField = new JTextField(5); // Damaged / Return

    private JTextArea outputArea = new JTextArea(15, 50);

    public inventoryGui() {
        inventory = new InventoryModule(storage.getAllProducts());

        setTitle("Inventory Module");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(7, 2,5,5));
        inputPanel.add(new JLabel("ID:")); inputPanel.add(idField);
        inputPanel.add(new JLabel("Name:")); inputPanel.add(nameField);
        inputPanel.add(new JLabel("Category:")); inputPanel.add(categoryField);
        inputPanel.add(new JLabel("Price:")); inputPanel.add(priceField);
        inputPanel.add(new JLabel("Quantity:")); inputPanel.add(quantityField);
        inputPanel.add(new JLabel("Expiry (YYYY-MM-DD):")); inputPanel.add(expiryField);

        // Action Panel
        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.add(new JLabel("Quantity for Damaged / Return:"));
        actionPanel.add(actionQuantityField);

        // Buttons Panel
        JPanel buttonPanel = new JPanel(new GridLayout(2, 5, 5,5));
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton searchBtn = new JButton("Search");
        JButton listBtn = new JButton("List All");
        JButton checkExpiryBtn = new JButton("Check Expiry");
        JButton lowStockBtn = new JButton("Low Stock");
        JButton damagedBtn = new JButton("Process Damaged");
        JButton returnBtn = new JButton("Sales Return");

        buttonPanel.add(addBtn); buttonPanel.add(updateBtn); buttonPanel.add(deleteBtn);
        buttonPanel.add(searchBtn); buttonPanel.add(listBtn); buttonPanel.add(checkExpiryBtn);
        buttonPanel.add(lowStockBtn); buttonPanel.add(damagedBtn); buttonPanel.add(returnBtn);

        // Output Area
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Organize panels
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(actionPanel);
        centerPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Button Actions
        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
        searchBtn.addActionListener(e -> searchProduct());
        listBtn.addActionListener(e -> listProducts());
        checkExpiryBtn.addActionListener(e -> checkExpiry());
        lowStockBtn.addActionListener(e -> checkLowStock());
        damagedBtn.addActionListener(e -> processDamaged());
        returnBtn.addActionListener(e -> processReturn());
    }

    // ---- Methods ----
    private void addProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String category = categoryField.getText();
            double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            LocalDate expiry = LocalDate.parse(expiryField.getText().trim());

            if (inventory.searchProduct(id) != null) {
                outputArea.setText("Product ID already exists!");
                return;
            }

            Product p = new Product(id, name, category, price, quantity, expiry, 0);
            inventory.addProduct(p);
            storage.writeAllProducts(storage.getAllProducts());
            outputArea.setText("Product added successfully!");
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage());
        }
    }

    private void updateProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            Product p = inventory.searchProduct(id);
            if (p == null) { outputArea.setText("Product not found!"); return; }

            if (!nameField.getText().isEmpty()) p.setProductName(nameField.getText());
            if (!categoryField.getText().isEmpty()) p.setCategory(categoryField.getText());
            if (!priceField.getText().isEmpty()) p.setPrice(Double.parseDouble(priceField.getText()));
            if (!quantityField.getText().isEmpty()) p.setQuantity(Integer.parseInt(quantityField.getText()));
            if (!expiryField.getText().isEmpty()) p.setExpiryDate(LocalDate.parse(expiryField.getText().trim()));

            inventory.updateProduct(id, p);
            storage.writeAllProducts(storage.getAllProducts());
            outputArea.setText("Product updated successfully!");
        } catch (Exception ex) {
            outputArea.setText("Error: " + ex.getMessage());
        }
    }

    private void deleteProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            if (inventory.deleteProduct(id)) {
                storage.writeAllProducts(storage.getAllProducts());
                outputArea.setText("Product deleted successfully!");
            } else { outputArea.setText("Product not found!"); }
        } catch (Exception ex) { outputArea.setText("Error: " + ex.getMessage()); }
    }

    private void searchProduct() {
        try {
            int id = Integer.parseInt(idField.getText());
            Product p = inventory.searchProduct(id);
            if (p != null) outputArea.setText(p.toString());
            else outputArea.setText("Product not found!");
        } catch (Exception ex) { outputArea.setText("Error: " + ex.getMessage()); }
    }

    private void listProducts() {
        StringBuilder sb = new StringBuilder();
        for (Product p : storage.getAllProducts()) sb.append(p.toString()).append("\n");
        outputArea.setText(sb.toString());
    }

    private void checkExpiry() {
        StringBuilder sb = new StringBuilder();
        for (Product p : inventory.checkExpiry()) sb.append(p.toString()).append("\n");
        outputArea.setText(sb.toString());
    }

    private void checkLowStock() {
        StringBuilder sb = new StringBuilder();
        for (Product p : inventory.checkLowStock()) sb.append(p.toString()).append("\n");
        outputArea.setText(sb.toString());
    }

    private void processDamaged() {
        try {
            int id = Integer.parseInt(idField.getText());
            int qty = Integer.parseInt(actionQuantityField.getText());
            if (inventory.processDamagedItems(id, qty)) {
                storage.writeAllProducts(storage.getAllProducts());
                outputArea.setText("Damaged items processed successfully for Product ID " + id);
            } else { outputArea.setText("Product not found!"); }
        } catch (Exception ex) { outputArea.setText("Error: " + ex.getMessage()); }
    }

    private void processReturn() {
        try {
            int id = Integer.parseInt(idField.getText());
            int qty = Integer.parseInt(actionQuantityField.getText());
            if (inventory.processSalesReturn(id, qty)) {
                storage.writeAllProducts(storage.getAllProducts());
                outputArea.setText("Sales return processed successfully for Product ID " + id);
            } else { outputArea.setText("Product not found!"); }
        } catch (Exception ex) { outputArea.setText("Error: " + ex.getMessage()); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new inventoryGui().setVisible(true));
    }
}
