package hypermarket;
import java.util.ArrayList;
public class AdminModule {
    private ArrayList<User> employees;
    public AdminModule() {
        employees = new ArrayList<>();
    }
    public boolean addEmployee(User u) {
        if (u == null || u.getId() <= 0) return false;
        if (searchEmployee(u.getId()) != null) return false;
        employees.add(u);
        return true;
    }
    public boolean deleteEmployee(int id) {
        User u = searchEmployee(id);
        if (u == null) return false;
        employees.remove(u);
        return true;
    }
    public boolean updateEmployee(User updated) {
        User old = searchEmployee(updated.getId());
        if (old == null) return false;
        old.setUsername(updated.getUsername());
        old.setPassword(updated.getPassword());
        old.setRole(updated.getRole());
        return true;
    }
    public User searchEmployee(int id) {
        for (User u : employees) {
            if (u.getId() == id)
                return u;
        }
        return null;
    }
    public ArrayList<User> listEmployees() {
        return employees;
    }
}