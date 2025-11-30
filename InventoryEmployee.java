package hypermarket;
public class InventoryEmployee extends User {
    public InventoryEmployee(){}
    public InventoryEmployee(int id, String username, String password) {
        super(id, username, password, "INVENTORY");
    }
    @Override
    public String getRole() {
        return "InventoryEmployee";
    }  
}
