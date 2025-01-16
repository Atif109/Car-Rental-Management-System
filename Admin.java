import java.io.*;
import java.util.*;

public class Admin extends User {
    public Admin(String id, String password) {
        super(id, password);
    }

    @Override
    public boolean login(String id, String password) {
        return this.id.equals(id) && this.password.equals(password);
    }

    public void addManager(String managerId, String managerPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("managers.txt", true))) {
            writer.write(managerId + "," + managerPassword);
            writer.newLine();
            System.out.println("Manager added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void adminMenu(Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("-------------------------------------");
            System.out.println("         === ADMIN PANEL ===         ");
            System.out.println("-------------------------------------");
            System.out.println("1 - Add Manager");
            System.out.println("0 - Back to Main Menu");
            System.out.println("-------------------------------------");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    clearScreen();
                    System.out.print("Enter Manager ID: ");
                    String managerId = scanner.nextLine();
                    System.out.print("Enter Manager Password: ");
                    String managerPassword = scanner.nextLine();
                    addManager(managerId, managerPassword);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}