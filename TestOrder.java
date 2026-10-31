import java.util.ArrayList;
import java.util.List;
public class TestOrder {
    private final String id;
    private final Patient patient;
    private final List<TestOrderItem> items = new ArrayList<>();
    private Invoice invoice;
    public TestOrder(String id, Patient patient) {
        this.id = id;
        this.patient = patient;
    }
    public String getId() { return id; }
    public Patient getPatient() { return patient; }
    public List<TestOrderItem> getItems() { return items; }
    public Invoice getInvoice() { return invoice; }
    public void setInvoice(Invoice invoice) { this.invoice = invoice; }
    public void addItem(TestOrderItem item) { items.add(item); }
    public double getSubtotal() {
        double s = 0;
        for (TestOrderItem i : items) s += i.getTest().getPrice();
        return s;
    }
    public TestOrderItem findItem(String itemId) {
        for (TestOrderItem i : items) if (i.getId().equalsIgnoreCase(itemId)) return i;
        return null;
    }
    public void printSummary() {
        System.out.println("Order " + id + " | Patient: " + patient.getName() + " (" + patient.getId() + ")");
        for (TestOrderItem i : items) {
            System.out.println("  " + i.getId() + " | " + i.getTest().getName() + " | " + i.getStatus() + " | Rs." + String.format("%.2f", i.getTest().getPrice()));
        }
        System.out.println("  Charges (before tax): Rs." + String.format("%.2f", getSubtotal()));
        System.out.println("  Invoice: " + (invoice == null ? "Not generated" : invoice.getId() + (invoice.isPaid() ? " (PAID)" : " (UNPAID)")));
    }
    public void printResults() {
        System.out.println("Results Report | Patient: " + patient.getName() + " (" + patient.getId() + ") | Order: " + id);
        boolean any = false;
        for (TestOrderItem i : items) {
            TestResult r = i.getResult();
            if (r != null) {
                any = true;
                System.out.println("  " + r.getId() + " | Item " + i.getId() + " | " + i.getTest().getName() + " | Sample " + i.getSample().getId() + " | " + r.getValue() + " " + r.getUnit() + " | " + r.getRemarks());
            }
        }
        if (!any) System.out.println("  No results recorded yet.");
    }
}