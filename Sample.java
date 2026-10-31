import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Sample {
    private final String id;
    private final TestOrderItem item;
    private final String type;
    private final boolean valid;
    private final String collectedAt;
    public Sample(String id, TestOrderItem item, String type, boolean valid) {
        this.id = id;
        this.item = item;
        this.type = type;
        this.valid = valid;
        this.collectedAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
    }
    public String getId() { return id; }
    public TestOrderItem getItem() { return item; }
    public String getType() { return type; }
    public boolean isValid() { return valid; }
    public String getCollectedAt() { return collectedAt; }
}