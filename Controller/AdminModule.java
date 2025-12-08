package hypermarket.controllers;

import hypermarket.models.User;
import hypermarket.storage.EmployeeStorage;
import java.util.ArrayList;

public class AdminModule {
    
    private EmployeeStorage storage;

    public AdminModule(EmployeeStorage storage) {
        this.storage = storage;
    }

    public boolean addEmployee(User u) {
        if (u == null || u.getId() <= 0) return false;
        if (searchEmployee(u.getId()) != null) return false;

        ArrayList<User> list = storage.load();  // load
        list.add(u);
        storage.save(list);                     // save
        return true;
    }

    public boolean deleteEmployee(int id) {
        ArrayList<User> list = storage.load();

        User u = searchEmployee(id);
        if (u == null) return false;

        list.remove(u);
        storage.save(list);
        return true;
    }

    public boolean updateEmployee(User updated) {
        ArrayList<User> list = storage.load();

        User old = searchEmployee(updated.getId());
        if (old == null) return false;

        old.setUsername(updated.getUsername());
        old.setPassword(updated.getPassword());
        old.setRole(updated.getRole());

        storage.save(list);
        return true;
    }

    public User searchEmployee(int id) {
        ArrayList<User> list = storage.load();
        for (User u : list) {
            if (u.getId() == id) return u;
        }
        return null;
    }

    public ArrayList<User> listEmployees() {
        return storage.load();
    }
}
