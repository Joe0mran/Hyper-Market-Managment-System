package hypermarket.storage;

import hypermarket.models.User;
import java.util.ArrayList;

public interface EmployeeStorage {

    ArrayList<User> load();

    void save(ArrayList<User> list);
}
