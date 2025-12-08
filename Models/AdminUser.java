package hypermarket.models;
public class AdminUser extends User {
    public AdminUser(){}
    public AdminUser(int id, String username, String password) {
        super(id, username, password, "ADMIN");
    }
    @Override
    public String getRole() {
        return "ADMIN";
    }
}

