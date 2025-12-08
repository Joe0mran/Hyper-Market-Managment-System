package hypermarket.models;

public class UserFactory {

    public static User create(int id, String username, String password, String role) {
        switch (role) {
            case "Admin":
                return new AdminUser(id, username, password);
            case "Inventory":
                return new InventoryEmployee(id, username, password);
            case "Sales":
                return new SalesEmployee(id, username, password);
            case "Marketing":
                return new MarketingEmployee(id, username, password);
            default:
                return null;
        }
    }
}
