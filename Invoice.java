public class Invoice {
    public static final double TAX_RATE = 0.12;
    private final String id;
    private final TestOrder order;
    private final double subtotal;
    private final double tax;
    private final double total;
    private boolean paid;
    private String paymentMode = "-";
    public Invoice(String id, TestOrder order) {
        this.id = id;
        this.order = order;
        this.subtotal = order.getSubtotal();
        this.tax = subtotal * TAX_RATE;
        this.total = subtotal + tax;
    }
    public String getId() { return id; }
    public TestOrder getOrder() { return order; }
    public double getTotal() { return total; }
    public boolean isPaid() { return paid; }
    public void pay(String mode) {
        this.paid = true;
        this.paymentMode = mode;
    }
    public void print() {
        System.out.println("-------- INVOICE " + id + " --------");
        System.out.println("Order    : " + order.getId());
        System.out.println("Patient  : " + order.getPatient().getName() + " (" + order.getPatient().getId() + ")");
        for (TestOrderItem i : order.getItems()) {
            System.out.println("  " + i.getId() + " " + i.getTest().getName() + " : Rs." + String.format("%.2f", i.getTest().getPrice()));
        }
        System.out.println("Subtotal : Rs." + String.format("%.2f", subtotal));
        System.out.println("Tax (" + (int) (TAX_RATE * 100) + "%) : Rs." + String.format("%.2f", tax));
        System.out.println("Total    : Rs." + String.format("%.2f", total));
        System.out.println("Status   : " + (paid ? "PAID (CLOSED) via " + paymentMode : "UNPAID (OPEN)"));
        System.out.println("-----------------------------");
    }
}