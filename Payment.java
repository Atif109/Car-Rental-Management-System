import java.io.*;

public class Payment {
    private double amount;
    private String method;
    private double additionalCharges;

    public Payment(double amount, String method, double additionalCharges) {
        this.amount = amount;
        this.method = method;
        this.additionalCharges = additionalCharges;
    }

    public void processPayment() {
        System.out.println("Processing payment...");
        System.out.println("Amount: " + amount);
        System.out.println("Method: " + method);
        if (method.equals("cash")) {
            System.out.println("Additional Charges: " + additionalCharges);
        }
        System.out.println("Payment successful!");
        recordPayment();
    }

    private void recordPayment() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("ledger.txt", true))) {
            writer.write("Payment of " + amount + " made via " + method);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to ledger: " + e.getMessage());
        }
    }
}