package hypermarket;
public class SalesEmployee extends User {
    public SalesEmployee (){}
    public SalesEmployee(int id, String username, String password) {
        super(id, username, password, "SALES");
    }
    @Override
    public String getRole() {
        return "SalesEmployee";
    }
}