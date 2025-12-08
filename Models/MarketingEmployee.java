package hypermarket.models;
public class MarketingEmployee extends User {
    public MarketingEmployee(){}
    public MarketingEmployee(int id, String username, String password) {
        super(id, username, password, "MARKETING");
    }
    @Override
    public String getRole() {
        return "MarketingEmployee";
    }
}
