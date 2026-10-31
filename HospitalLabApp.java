import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
public class HospitalLabApp {
    private static final Scanner in = new Scanner(System.in);
    private static final Map<String, Patient> patients = new LinkedHashMap<>();
    private static final Map<String, LabTest> tests = new LinkedHashMap<>();
    private static final Map<String, TestOrder> orders = new LinkedHashMap<>();
    private static int patientCount = 1, orderCount = 1, sampleCount = 1, resultCount = 1, invoiceCount = 1;
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n===== HOSPITAL LAB TEST MANAGEMENT =====");
            System.out.println("1. Add Patient");
            System.out.println("2. Add Lab Test");
            System.out.println("3. Create Test Order");
            System.out.println("4. Collect Sample");
            System.out.println("5. Record Result");
            System.out.println("6. Generate Invoice");
            System.out.println("7. Record Payment");
            System.out.println("8. Display Orders");
            System.out.println("9. Exit");
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1: addPatient(); break;
                case 2: addLabTest(); break;
                case 3: createOrder(); break;
                case 4: collectSample(); break;
                case 5: recordResult(); break;
                case 6: generateInvoice(); break;
                case 7: recordPayment(); break;
                case 8: displayOrders(); break;
                case 9: running = false; System.out.println("Exiting. Goodbye."); break;
                default: System.out.println("Invalid choice.");
            }
        }
    }
    private static String readLine(String prompt) {
        System.out.print(prompt);
        return in.nextLine().trim();
    }
    private static int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readLine(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid number.");
            }
        }
    }
    private static double readDouble(String prompt) {
        while (true) {
            try {
                double d = Double.parseDouble(readLine(prompt));
                if (d < 0) throw new NumberFormatException();
                return d;
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid non-negative amount.");
            }
        }
    }
    private static void addPatient() {
        String name = readLine("Patient name: ");
        if (name.isEmpty()) { System.out.println("Name required."); return; }
        int age = readInt("Age: ");
        String phone = readLine("Phone: ");
        String id = "P" + String.format("%03d", patientCount++);
        patients.put(id, new Patient(id, name, age, phone));
        System.out.println("Patient added: " + patients.get(id));
    }
    private static void addLabTest() {
        String code = readLine("Test code: ").toUpperCase();
        if (code.isEmpty() || tests.containsKey(code)) { System.out.println("Invalid or duplicate code."); return; }
        String name = readLine("Test name: ");
        double price = readDouble("Price: ");
        tests.put(code, new LabTest(code, name, price));
        System.out.println("Lab test added: " + tests.get(code));
    }
    private static void createOrder() {
        if (patients.isEmpty() || tests.isEmpty()) { System.out.println("Add at least one patient and one lab test first."); return; }
        System.out.println("Patients:");
        patients.values().forEach(p -> System.out.println("  " + p));
        Patient p = patients.get(readLine("Patient ID: ").toUpperCase());
        if (p == null) { System.out.println("Patient not found."); return; }
        System.out.println("Available tests:");
        tests.values().forEach(t -> System.out.println("  " + t));
        String oid = "O" + String.format("%03d", orderCount++);
        TestOrder order = new TestOrder(oid, p);
        while (true) {
            String code = readLine("Test code to add (blank to finish): ").toUpperCase();
            if (code.isEmpty()) break;
            LabTest t = tests.get(code);
            if (t == null) { System.out.println("Test not found."); continue; }
            order.addItem(new TestOrderItem(oid + "-" + (order.getItems().size() + 1), order, t));
            System.out.println("Added " + t.getName());
        }
        if (order.getItems().isEmpty()) { System.out.println("Order cancelled. No tests selected."); orderCount--; return; }
        orders.put(oid, order);
        System.out.println("Order created:");
        order.printSummary();
    }
    private static TestOrder pickOrder() {
        if (orders.isEmpty()) { System.out.println("No orders available. Create an order first."); return null; }
        TestOrder o = orders.get(readLine("Order ID: ").toUpperCase());
        if (o == null) System.out.println("Order not found.");
        return o;
    }
    private static TestOrderItem pickItem(TestOrder o) {
        o.printSummary();
        TestOrderItem i = o.findItem(readLine("Order item ID: "));
        if (i == null) System.out.println("Order item not found in this order.");
        return i;
    }
    private static void collectSample() {
        TestOrder o = pickOrder();
        if (o == null) return;
        TestOrderItem i = pickItem(o);
        if (i == null) return;
        if (!i.canCollect()) { System.out.println("A valid sample is already collected for this item."); return; }
        String type = readLine("Sample type (Blood/Urine/Serum/Swab...): ");
        String v = readLine("Is the sample valid? (y/n): ");
        Sample s = new Sample("S" + String.format("%03d", sampleCount++), i, type, v.equalsIgnoreCase("y"));
        i.attachSample(s);
        System.out.println("Sample " + s.getId() + " collected at " + s.getCollectedAt() + " for item " + i.getId() + " -> " + i.getStatus());
    }
    private static void recordResult() {
        TestOrder o = pickOrder();
        if (o == null) return;
        TestOrderItem i = pickItem(o);
        if (i == null) return;
        if (i.getResult() != null) { System.out.println("Result already recorded for this item."); return; }
        if (!i.canRecordResult()) { System.out.println("Cannot record result: no valid collected sample for this item."); return; }
        String value = readLine("Result value: ");
        String unit = readLine("Unit: ");
        String remarks = readLine("Remarks: ");
        TestResult r = new TestResult("R" + String.format("%03d", resultCount++), i, value, unit, remarks);
        i.attachResult(r);
        System.out.println("Result " + r.getId() + " recorded for item " + i.getId());
    }
    private static void generateInvoice() {
        TestOrder o = pickOrder();
        if (o == null) return;
        if (o.getInvoice() != null) { System.out.println("Invoice already exists:"); o.getInvoice().print(); return; }
        Invoice invoice = new Invoice("INV" + String.format("%03d", invoiceCount++), o);
        o.setInvoice(invoice);
        invoice.print();
    }
    private static void recordPayment() {
        TestOrder o = pickOrder();
        if (o == null) return;
        Invoice invoice = o.getInvoice();
        if (invoice == null) { System.out.println("Generate an invoice first."); return; }
        if (invoice.isPaid()) { System.out.println("Invoice already paid and closed."); return; }
        invoice.print();
        double amt = readDouble("Amount paid: ");
        if (Math.abs(amt - invoice.getTotal()) > 0.009) { System.out.println("Payment must equal the invoice total of Rs." + String.format("%.2f", invoice.getTotal()) + ". Payment not recorded."); return; }
        String mode = readLine("Payment mode (Cash/Card/UPI): ");
        invoice.pay(mode.isEmpty() ? "Cash" : mode);
        System.out.println("Payment recorded. Invoice closed.");
        invoice.print();
    }
    private static void displayOrders() {
        if (orders.isEmpty()) { System.out.println("No orders to display."); return; }
        System.out.println("\n=== ORDER SUMMARY ===");
        orders.values().forEach(TestOrder::printSummary);
        System.out.println("\n=== RESULTS REPORT ===");
        orders.values().forEach(TestOrder::printResults);
        System.out.println("\n=== INVOICES ===");
        boolean any = false;
        for (TestOrder o : orders.values()) {
            if (o.getInvoice() != null) { any = true; o.getInvoice().print(); }
        }
        if (!any) System.out.println("No invoices generated yet.");
    }
}