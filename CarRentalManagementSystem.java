import java.io.*;
import java.util.*;


// Employee Class
class Employee extends User {
    Scanner scanner = new Scanner(System.in);
    public Employee(String id, String password) {
        super(id, password);
    }

    @Override
    public boolean login(String id, String password) {
        return this.id.equals(id) && this.password.equals(password);
    }

    public void addCar(String carId, String carName, double pricePerDay) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("cars.txt", true))) {
            writer.write(carId + "," + carName + "," + pricePerDay);
            writer.newLine();
            System.out.println("Car added successfully!");
            System.out.print("Press ENTER to continue");
            String temp = scanner.nextLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void removeCar(String carId) {
        File inputFile = new File("cars.txt");
        File tempFile = new File("temp_cars.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean carRemoved = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[0].equals(carId)) {
                    writer.write(line);
                    writer.newLine();
                } else {
                    carRemoved = true;
                    System.out.println("Car with ID " + carId + " removed successfully!");
                }
            }

            if (!carRemoved) {
                System.out.println("Car with ID " + carId + " not found.");
            }

        } catch (IOException e) {
            System.out.println("Error processing file: " + e.getMessage());
        }

        // Delete original file and rename temp file to original file
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    public void employeeMenu(Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("=== Employee Panel ===");
            System.out.println("1 - Add Car to Inventory");
            System.out.println("2 - Remove Car from Inventory");
            System.out.println("3 - View Cars");
            System.out.println("0 - Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    clearScreen();
                    System.out.print("Enter Car ID: ");
                    String carId = scanner.nextLine();
                    System.out.print("Enter Car Name: ");
                    String carName = scanner.nextLine();
                    System.out.print("Enter Price Per Day: ");
                    double pricePerDay = scanner.nextDouble();
                    scanner.nextLine();
                    addCar(carId, carName, pricePerDay);
                    break;
                case 2:
                    clearScreen();
                    System.out.print("Enter Car ID to Remove: ");
                    String removeCarId = scanner.nextLine();
                    removeCar(removeCarId);
                    System.out.print("Press ENTER to continue");
                    scanner.nextLine();
                    break;
                case 3:
                    clearScreen();
                    viewCars();
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
}

// Customer Class
class Customer {
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

    public double calculatePayment(String carId, int days) {
        double pricePerDay = 0.0;
        try (BufferedReader reader = new BufferedReader(new FileReader("cars.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(carId)) {
                    pricePerDay = Double.parseDouble(parts[2]);
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading cars: " + e.getMessage());
        }
        return pricePerDay * days;
    }

    public boolean rentCar(String carId, int days) {
        // Check availability logic can be added here
        System.out.println("Car rented for " + days + " days.");
        return true; // Replace with actual availability check
    }

    public void makePayment(double amount, String method) {
        double additionalCharges = method.equals("cash") ? 2000.0 : 0.0;
        Payment payment = new Payment(amount, method, additionalCharges);
        payment.processPayment();
    }

    public void customerMenu(Scanner scanner) {
        while (true) {
            clearScreen();
            System.out.println("-------------------------------------");
            System.out.println("         === Customer Panel ===      ");
            System.out.println("-------------------------------------");
            System.out.println("1 - View Cars");
            System.out.println("2 - Rent a Car");
            System.out.println("0 - Back to Main Menu");
            System.out.println("-------------------------------------");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    clearScreen();
                    viewCars();
                    System.out.println("Press Enter to continue...");
                    scanner.nextLine(); 
                    break;
                case 2:
                    clearScreen();
                    System.out.println("-------------------------------------");
                    System.out.println("           === RENT CAR ===          ");
                    System.out.println("-------------------------------------");
                    System.out.print("Enter Car ID: ");
                    String carId = scanner.nextLine();
                    System.out.print("Enter Number of Days: ");
                    int days = scanner.nextInt();
                    scanner.nextLine();
                    if (rentCar(carId, days)) {
                        double amount = calculatePayment(carId, days);
                        System.out.println("Total Amount: " + amount);
                        System.out.print("Enter Payment Method (cash/card): ");
                        String method = scanner.nextLine();
                        makePayment(amount, method);
                        System.out.println("Payment successful! Thank you for your rental.");
                        System.out.println("Press Enter to exit.");
                        scanner.nextLine(); 
                    }
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



// Main Class
public class CarRentalManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Admin admin = new Admin("admin", "adminpass");

        while (true) {
            clearScreen();
            System.out.println("-------------------------------------");
            System.out.println("=== Car Rental Management System ===");
            System.out.println("-------------------------------------");
            System.out.println("1 - Customer");
            System.out.println("2 - Staff");
            System.out.println("0 - Exit");
            System.out.println("-------------------------------------");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine();

            switch (input) {
                case "admin123":
                    admin.adminMenu(scanner);
                    break;
                case "1":
                    Customer customer = new Customer();
                    customer.customerMenu(scanner);
                    break;
                case "2":
                    staffMenu(scanner);
                    break;
                case "0":
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
                    System.out.print("Press ENTER to continue");
                    String temp = scanner.nextLine();
            }
        }
    }

    private static void staffMenu(Scanner scanner) {
        clearScreen();
        System.out.println("----------");
        System.out.println("Staff Menu");
        System.out.println("----------");
        System.out.print("1: Manager\n2: Employee\nChoose option: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        if (option == 1) {
            clearScreen();
            System.out.println("-------------------------------------");
            System.out.println("               LOGIN                 ");
            System.out.println("-------------------------------------");
            System.out.print("Enter Manager ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Password: ");
            String pass = scanner.nextLine();
            if (validateManager(id, pass)) {
                Manager manager = new Manager(id, pass);
                manager.managerMenu(scanner);
            } else {
                System.out.println("Invalid credentials. Try again.");
                System.out.print("Press ENTER to continue");
                String temp = scanner.nextLine();
            }
        } else if (option == 2) {
            clearScreen();
            System.out.println("-------------------------------------");
            System.out.println("               LOGIN                 ");
            System.out.println("-------------------------------------");
            System.out.print("Enter Employee ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Password: ");
            String pass = scanner.nextLine();
            if (validateEmployee(id, pass)) {
                Employee employee = new Employee(id, pass);
                employee.employeeMenu(scanner);
            } else {
                System.out.println("Invalid credentials. Try again.");
                System.out.print("Press ENTER to continue");
                String temp = scanner.nextLine();
            }
        } else {
            System.out.println("Invalid choice! Try again.");
        }
    }

    private static boolean validateManager(String id, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("managers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading managers file: " + e.getMessage());
        }
        return false;
    }

    private static boolean validateEmployee(String id, String password) {
        try (BufferedReader reader = new BufferedReader(new FileReader("employees.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(id) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading employees file: " + e.getMessage());
        }
        return false;
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
