public class LabTest {
    private final String code;
    private final String name;
    private final double price;
    public LabTest(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String toString() { return code + " | " + name + " | Rs." + String.format("%.2f", price); }
}