import java.io.*;
import java.util.*;

public class Manager extends User {
    public Manager(String id, String password) {
        super(id, password);
    }

    @Override
    public boolean login(String id, String password) {
        return this.id.equals(id) && this.password.equals(password);
    }

    public void addEmployee(String employeeId, String employeePassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("employees.txt", true))) {
            writer.write(employeeId + "," + employeePassword);
            writer.newLine();
            System.out.println("Employee added successfully!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void viewCars() {
        try (BufferedReader reader = new BufferedReader(new FileReader("cars.txt"))) {
            String line;
            System.out.println("-------------------------------------");
            System.out.println("        === Available Cars ===       ");
            System.out.println("-------------------------------------");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                System.out.println("Car ID: " + parts[0] + ", Name: " + parts[1] + ", Price per Day: " + parts[2]);
            }
        } catch (IOException e) {
            System.out.println("Error reading cars: " + e.getMessage());
        }
    }

    public void viewLedger() {
        clearScreen();
        try (BufferedReader reader = new BufferedReader(new FileReader("ledger.txt"))) {
            String line;
            System.out.println("-------------------------------------");
            System.out.println("       === Payment Records ===       ");
            System.out.println("-------------------------------------");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading ledger: " + e.getMessage());
        }
    }

    public void managerMenu(Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("-------------------------------------");
            System.out.println("         === Manager Panel ===       ");
            System.out.println("-------------------------------------");
            System.out.println("1 - Add Employee");
            System.out.println("2 - View Cars");
            System.out.println("3 - View Ledger");
            System.out.println("0 - Back to Main Menu");
            System.out.println("-------------------------------------");
            System.out.print("Enter your choice: ");


            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                System.out.println("-------------------------------------");
                System.out.println("            ADD EMPLOYE              ");
                System.out.println("-------------------------------------");
                    System.out.print("Enter Employee ID: ");
                    String employeeId = scanner.nextLine();
                    System.out.print("Enter Employee Password: ");
                    String employeePassword = scanner.nextLine();
                    addEmployee(employeeId, employeePassword);
                    break;
                case 2:
                    viewCars();
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine(); 
                    break;
                case 3:
                    viewLedger();
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine(); 
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