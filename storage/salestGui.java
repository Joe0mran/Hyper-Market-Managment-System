import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class salesGui extends JFrame {

    private salesStorage storage = new salesStorage();

    public salesGui() {
        setTitle("Product Management");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel();
        JLabel idLabel = new JLabel("Product ID:");
        JTextField idField = new JTextField(10);
        JButton searchBtn = new JButton("Search");
        topPanel.add(idLabel);
        topPanel.add(idField);
        topPanel.add(searchBtn);
        add(topPanel, BorderLayout.NORTH);

        JTextArea displayArea = new JTextArea();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();

        JButton viewAllBtn = new JButton("View All Products");
        JLabel qtyLabel = new JLabel("Quantity:");
        JTextField quantityField = new JTextField(5);
        JButton createOrderBtn = new JButton("Create Order");
        JButton cancelOrderBtn = new JButton("Cancel Order");

        bottomPanel.add(viewAllBtn);
        bottomPanel.add(qtyLabel);
        bottomPanel.add(quantityField);
        bottomPanel.add(createOrderBtn);
        bottomPanel.add(cancelOrderBtn);

        add(bottomPanel, BorderLayout.SOUTH);

        viewAllBtn.addActionListener(e -> {
            displayArea.setText("");
            for (Product p : storage.getAllProducts()) {
                displayArea.append("ID: " + p.getProductId() + " | ");
                displayArea.append("Name: " + p.getProductName() + " | ");
                displayArea.append("Category: " + p.getCategory() + " | ");
                displayArea.append("Price: " + p.getPrice() + " | ");
                displayArea.append("Quantity: " + p.getQuantity() + " | ");
                displayArea.append("Expiry: " + p.getExpiryDate().format(DateTimeFormatter.ISO_DATE) + " | ");
                displayArea.append("Damaged: " + p.getDamagedQuantity() + "\n");
            }
        });

        searchBtn.addActionListener(e -> {
            displayArea.setText("");
            try {
                int id = Integer.parseInt(idField.getText());
                Product p = storage.searchProduct(id);
                if (p != null) {
                    displayArea.append("ID: " + p.getProductId() + "\n");
                    displayArea.append("Name: " + p.getProductName() + "\n");
                    displayArea.append("Category: " + p.getCategory() + "\n");
                    displayArea.append("Price: " + p.getPrice() + "\n");
                    displayArea.append("Quantity: " + p.getQuantity() + "\n");
                    displayArea.append("Expiry: " + p.getExpiryDate().format(DateTimeFormatter.ISO_DATE) + "\n");
                    displayArea.append("Damaged: " + p.getDamagedQuantity() + "\n");
                } else {
                    displayArea.setText("Product not found!");
                }
            } catch (NumberFormatException ex) {
                displayArea.setText("Enter a valid ID!");
            }
        });

        createOrderBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int qty = Integer.parseInt(quantityField.getText());
                Product p = storage.searchProduct(id);
                if (p != null) {
                    if (p.getQuantity() >= qty) {
                        storage.updateProductQuantity(id, p.getQuantity() - qty);
                        displayArea.setText("Order created!\nRemaining stock: " + (p.getQuantity() - qty));
                    } else {
                        displayArea.setText("Not enough stock!");
                    }
                } else {
                    displayArea.setText("Product not found!");
                }
            } catch (NumberFormatException ex) {
                displayArea.setText("Enter valid ID and quantity!");
            }
        });

        cancelOrderBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                int qty = Integer.parseInt(quantityField.getText());
                Product p = storage.searchProduct(id);
                if (p != null) {
                    storage.updateProductQuantity(id, p.getQuantity() + qty);
                    displayArea.setText("Order cancelled!\nStock restored: " + (p.getQuantity() + qty));
                } else {
                    displayArea.setText("Product not found!");
                }
            } catch (NumberFormatException ex) {
                displayArea.setText("Enter valid ID and quantity!");
            }
        });
    }

    
}

