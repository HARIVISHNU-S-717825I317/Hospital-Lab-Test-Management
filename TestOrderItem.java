public class TestOrderItem {
    public static final String ORDERED = "ORDERED";
    public static final String COLLECTED = "COLLECTED";
    public static final String REJECTED = "SAMPLE REJECTED";
    public static final String RESULTED = "RESULT READY";
    private final String id;
    private final TestOrder order;
    private final LabTest test;
    private String status = ORDERED;
    private Sample sample;
    private TestResult result;
    public TestOrderItem(String id, TestOrder order, LabTest test) {
        this.id = id;
        this.order = order;
        this.test = test;
    }
    public String getId() { return id; }
    public TestOrder getOrder() { return order; }
    public LabTest getTest() { return test; }
    public String getStatus() { return status; }
    public Sample getSample() { return sample; }
    public TestResult getResult() { return result; }
    public void attachSample(Sample s) {
        this.sample = s;
        this.status = s.isValid() ? COLLECTED : REJECTED;
    }
    public void attachResult(TestResult r) {
        this.result = r;
        this.status = RESULTED;
    }
    public boolean canCollect() { return sample == null || !sample.isValid(); }
    public boolean canRecordResult() { return sample != null && sample.isValid() && result == null; }
}