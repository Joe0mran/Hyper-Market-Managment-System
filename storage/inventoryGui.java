import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class InventoryGUI extends JFrame {

    private InventoryModule inventory = new InventoryModule();
    private DefaultTableModel model;
    private JTable table;

    public InventoryGUI() {
        setTitle("Inventory Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // ======= TABLE =======
        String[] columns = {"ID", "Name", "Quantity", "Price", "Category", "Expiry"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // منع التعديل مباشرة على الجدول
            }
        };
        table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false); // منع نقل الأعمدة
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // ======= Row Selection كامل فقط =======
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        // ======= Highlight للمنتجات =======
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component comp = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, column);

                int qty = Integer.parseInt(t.getValueAt(row, 2).toString());
                LocalDate expiry = LocalDate.parse(t.getValueAt(row, 5).toString());

                if (LocalDate.now().isAfter(expiry)) {
                    comp.setBackground(new Color(255, 102, 102)); // أحمر فاتح للمنتهي
                } else if (qty <= 5) {
                    comp.setBackground(new Color(255, 235, 238)); // وردي خفيف للكمية المنخفضة
                } else {
                    comp.setBackground(Color.WHITE); // عادي
                }

                if (isSelected) {
                    comp.setBackground(new Color(102, 178, 255)); // اختيار الصف
                }

                return comp;
            }
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(20, 20, 940, 400);
        add(scroll);

        // ======= BUTTONS =======
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton removeBtn = new JButton("Remove");
        JButton expiredBtn = new JButton("Expired Items");
        JButton lowStockBtn = new JButton("Low Stock");

        JButton[] buttons = {addBtn, updateBtn, removeBtn, expiredBtn, lowStockBtn};
        int x = 20;
        for (JButton b : buttons) {
            b.setBounds(x, 440, 160, 40);
            b.setBackground(new Color(52, 152, 219));
            b.setForeground(Color.WHITE);
            b.setFont(new Font("Segoe UI", Font.BOLD, 14));
            b.setFocusPainted(false);
            add(b);
            x += 180;
        }

        // ======= STATS =======
        JLabel totalProductsLbl = new JLabel();
        JLabel totalQtyLbl = new JLabel();
        totalProductsLbl.setBounds(20, 500, 200, 30);
        totalQtyLbl.setBounds(240, 500, 200, 30);
        totalProductsLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalQtyLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(totalProductsLbl);
        add(totalQtyLbl);

        // ======= ACTIONS =======
        loadTable();
        updateStats(totalProductsLbl, totalQtyLbl);

        addBtn.addActionListener(e -> addProduct(totalProductsLbl, totalQtyLbl));
        removeBtn.addActionListener(e -> removeProduct(totalProductsLbl, totalQtyLbl));
        updateBtn.addActionListener(e -> updateProduct(totalProductsLbl, totalQtyLbl));
        expiredBtn.addActionListener(e -> showExpiredItems());
        lowStockBtn.addActionListener(e -> showLowStock());

        setVisible(true);
    }

    private void loadTable() {
        model.setRowCount(0);
        List<Product> products = inventory.getAllProducts();
        for (Product p : products) {
            model.addRow(new Object[]{
                    p.getId(), p.getName(), p.getQuantity(),
                    p.getPrice(), p.getCategory(), p.getExpiryDate()
            });
        }
    }

    private void updateStats(JLabel totalProductsLbl, JLabel totalQtyLbl) {
        int totalQty = 0;
        for (int i = 0; i < model.getRowCount(); i++)
            totalQty += Integer.parseInt(model.getValueAt(i, 2).toString());
        totalProductsLbl.setText("Total Products: " + model.getRowCount());
        totalQtyLbl.setText("Total Quantity: " + totalQty);
    }

    private void addProduct(JLabel totalProductsLbl, JLabel totalQtyLbl) {
        try {
            int id = inventory.getAllProducts().size() + 1;
            String name = JOptionPane.showInputDialog(this, "Product Name:");
            int qty = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantity:"));
            double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Price:"));
            String category = JOptionPane.showInputDialog(this, "Category:");
            LocalDate expiry = LocalDate.parse(JOptionPane.showInputDialog(this, "Expiry (yyyy-mm-dd):"));
            inventory.addProduct(new Product(id, name, qty, price, category, expiry));
            loadTable();
            updateStats(totalProductsLbl, totalQtyLbl);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }

    private void removeProduct(JLabel totalProductsLbl, JLabel totalQtyLbl) {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select product first!"); return; }
        int id = Integer.parseInt(model.getValueAt(row, 0).toString());
        inventory.deleteProduct(id);
        loadTable();
        updateStats(totalProductsLbl, totalQtyLbl);
    }

    private void updateProduct(JLabel totalProductsLbl, JLabel totalQtyLbl) {
        int row = table.getSelectedRow();
        if (row == -1) { JOptionPane.showMessageDialog(this, "Select product first!"); return; }
        Product p = inventory.findById(Integer.parseInt(model.getValueAt(row, 0).toString()));
        if (p == null) return;

        try {
            String name = JOptionPane.showInputDialog(this, "Name:", p.getName());
            int qty = Integer.parseInt(JOptionPane.showInputDialog(this, "Quantity:", p.getQuantity()));
            double price = Double.parseDouble(JOptionPane.showInputDialog(this, "Price:", p.getPrice()));
            String cat = JOptionPane.showInputDialog(this, "Category:", p.getCategory());
            LocalDate exp = LocalDate.parse(JOptionPane.showInputDialog(this, "Expiry (yyyy-mm-dd):", p.getExpiryDate()));

            p.setName(name);
            p.setQuantity(qty);
            p.setPrice(price);
            p.setCategory(cat);
            p.setExpiryDate(exp);

            loadTable();
            updateStats(totalProductsLbl, totalQtyLbl);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input!");
        }
    }

    private void showExpiredItems() {
        List<Product> expired = inventory.checkExpiry();
        StringBuilder sb = new StringBuilder("Expired Products:\n");
        for (Product p : expired) sb.append(p.getName()).append(" - Qty: ").append(p.getQuantity()).append("\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    private void showLowStock() {
        List<Product> low = inventory.checkLowStock();
        StringBuilder sb = new StringBuilder("Low Stock Products:\n");
        for (Product p : low) sb.append(p.getName()).append(" - Qty: ").append(p.getQuantity()).append("\n");
        JOptionPane.showMessageDialog(this, sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InventoryGUI::new);
    }
}
