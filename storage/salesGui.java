import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SalesGUI extends JFrame {

    SalesController controller;

    DefaultTableModel productsModel;
    DefaultTableModel cartModel;
    DefaultTableModel ordersModel;

    JTextField productIdField = new JTextField();
    JTextField qtyField = new JTextField();
    JTextField cancelOrderField = new JTextField();

    public SalesGUI() {

        controller = new SalesController(new SalesStorage());

        setTitle("Sales System");
        setSize(1100, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        Color beigeLight = new Color(250,243,225);
        getContentPane().setBackground(beigeLight);

        // ===== PRODUCTS TABLE =====
        productsModel = new DefaultTableModel(
                new String[]{"ID","Name","Price","Qty"},0);
        JTable productsTable = new JTable(productsModel);
        productsTable.setBackground(beigeLight);
        productsTable.setFillsViewportHeight(true);
        loadProducts();

        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(beigeLight);
        left.add(new JLabel("Available Products"), BorderLayout.NORTH);
        left.add(new JScrollPane(productsTable), BorderLayout.CENTER);

        // ===== CONTROLS =====
        JPanel center = new JPanel(new GridLayout(12,1,5,5));
        center.setBackground(beigeLight);

        center.add(new JLabel("Product ID"));
        center.add(productIdField);
        center.add(new JLabel("Quantity"));
        center.add(qtyField);

        JButton addBtn = new JButton("Add to Cart");
        JButton removeBtn = new JButton("Remove From Cart");
        JButton cancelBtn = new JButton("Cancel Order");
        JButton createBtn = new JButton("Create Order");

        JButton[] buttons = {addBtn, removeBtn, cancelBtn, createBtn};
        for (JButton btn : buttons) {
            btn.setBackground(beigeLight);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
        }

        center.add(addBtn);
        center.add(removeBtn);
        center.add(new JLabel("Cancel Order ID"));
        center.add(cancelOrderField);
        center.add(cancelBtn);
        center.add(createBtn);

        // ===== CART & ORDERS TABLES =====
        cartModel = new DefaultTableModel(
                new String[]{"ID","Name","Price","Qty"},0);
        JTable cartTable = new JTable(cartModel);
        cartTable.setBackground(beigeLight);
        cartTable.setFillsViewportHeight(true);

        ordersModel = new DefaultTableModel(
                new String[]{"Order ID","Products","Total","Date","Status"},0);
        JTable ordersTable = new JTable(ordersModel);
        ordersTable.setBackground(beigeLight);
        ordersTable.setFillsViewportHeight(true);

        JPanel right = new JPanel(new GridLayout(2,1));
        right.setBackground(beigeLight);
        right.add(new JScrollPane(cartTable));
        right.add(new JScrollPane(ordersTable));

        add(left, BorderLayout.WEST);
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);

        // ===== ACTIONS =====
        addBtn.addActionListener(e -> {
            try {
                String idText = productIdField.getText().trim();
                String qtyText = qtyField.getText().trim();

                if (idText.isEmpty() || qtyText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter Product ID and Quantity!");
                    return;
                }

                int id = Integer.parseInt(idText);
                int qty = Integer.parseInt(qtyText);

                Product p = controller.getStorage().searchProduct(id);
                if (p != null && p.getQuantity() >= qty) {
                    cartModel.addRow(new Object[]{
                            p.getProductId(), p.getProductName(), p.getPrice(), qty
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid product ID or insufficient quantity!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid numbers for Product ID and Quantity!");
            }
        });

        removeBtn.addActionListener(e -> {
            int row = cartTable.getSelectedRow();
            if (row != -1) cartModel.removeRow(row);
        });

        createBtn.addActionListener(e -> {
            if (cartModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Cart is empty!");
                return;
            }

            Map<Integer,Integer> items = new HashMap<>();
            StringBuilder productsText = new StringBuilder();

            for (int i=0;i<cartModel.getRowCount();i++) {
                int id = (int) cartModel.getValueAt(i,0);
                int qty = (int) cartModel.getValueAt(i,3);
                items.put(id, qty);
                productsText.append(id).append(":").append(qty).append(" ");
            }

            int orderId = controller.createOrder(items);

            Order o = controller.getStorage().getAllOrders()
                    .stream().filter(x -> x.getOrderId()==orderId)
                    .findFirst().get();

            ordersModel.addRow(new Object[]{
                    o.getOrderId(), productsText,
                    o.getTotalPrice(), o.getOrderDate(), o.getStatus()
            });

            cartModel.setRowCount(0);
            loadProducts();
            JOptionPane.showMessageDialog(null, "Order created successfully!");
        });

        cancelBtn.addActionListener(e -> {
            try {
                String cancelText = cancelOrderField.getText().trim();
                if (cancelText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter Order ID to cancel!");
                    return;
                }

                int orderId = Integer.parseInt(cancelText);
                controller.cancelOrder(orderId);

                for (int i = 0; i < ordersModel.getRowCount(); i++) {
                    int idInTable = Integer.parseInt(ordersModel.getValueAt(i, 0).toString());
                    if (idInTable == orderId) {
                        ordersModel.setValueAt("Cancelled", i, 4);
                    }
                }

                loadProducts();
                JOptionPane.showMessageDialog(null, "Order cancelled successfully!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid Order ID!");
            }
        });
    }

    private void loadProducts() {
        productsModel.setRowCount(0);
        for (Product p : controller.getStorage().getAllProducts()) {
            productsModel.addRow(new Object[]{
                    p.getProductId(), p.getProductName(),
                    p.getPrice(), p.getQuantity()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalesGUI().setVisible(true));
    }
}

