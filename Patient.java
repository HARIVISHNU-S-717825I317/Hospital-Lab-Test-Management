public class Patient {
    private final String id;
    private final String name;
    private final int age;
    private final String phone;
    public Patient(String id, String name, int age, String phone) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getPhone() { return phone; }
    public String toString() { return id + " | " + name + " | Age " + age + " | " + phone; }
}