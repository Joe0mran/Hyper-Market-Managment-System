import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class productGui extends JFrame {

    private productStorage storage = new productStorage();

    public productGui() {
        setTitle("Product Management");
        setSize(500, 400);
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


        JButton viewAllBtn = new JButton("View All Products");
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(viewAllBtn);
        add(bottomPanel, BorderLayout.SOUTH);


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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new productGui().setVisible(true));
    }
}
