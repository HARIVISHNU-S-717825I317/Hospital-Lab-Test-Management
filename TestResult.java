public class TestResult {
    private final String id;
    private final TestOrderItem item;
    private final String value;
    private final String unit;
    private final String remarks;
    public TestResult(String id, TestOrderItem item, String value, String unit, String remarks) {
        this.id = id;
        this.item = item;
        this.value = value;
        this.unit = unit;
        this.remarks = remarks;
    }
    public String getId() { return id; }
    public TestOrderItem getItem() { return item; }
    public String getValue() { return value; }
    public String getUnit() { return unit; }
    public String getRemarks() { return remarks; }
}