package hypermarket.storage;

import hypermarket.models.User;
import java.io.*;
import java.util.ArrayList;

public class EmployeeStorageFile implements EmployeeStorage {

    private final String filePath = "employees.txt";

    @Override
    public ArrayList<User> load() {
        ArrayList<User> list = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                User u = parseUser(line);
                if (u != null) list.add(u);
            }

        } catch (IOException e) {
            System.out.println("File not found, starting empty");
        }

        return list;
    }

    @Override
    public void save(ArrayList<User> list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (User u : list) {
                bw.write(formatUser(u));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Cannot save file");
        }
    }

    private User parseUser(String line) {
        String[] parts = line.split(",");
        return new User(
                Integer.parseInt(parts[0]),
                parts[1],
                parts[2],
                parts[3]
        );
    }

    private String formatUser(User u) {
        return u.getId() + "," + u.getUsername() + "," +
               u.getPassword() + "," + u.getRole();
    }

    public ArrayList<Object> loadEmployees() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void saveEmployees(ArrayList<Object> employees) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
