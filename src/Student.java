import java.io.Serializable;
import java.io.Serial;

/**
 * The Student class represents a single student record.
 * Implements Serializable so objects can be freeze-dried (serialized) to files.
 */
public class Student implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Private fields - data hiding principle
    private String studentId;
    private String name;
    private String department;
    private double gpa;

    // Constructor - called when creating a new student
    public Student(String studentId, String name, String department, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.gpa = gpa;
    }

    // ========== GETTERS ==========
    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getGpa() { return gpa; }

    // ========== SETTERS ==========
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setGpa(double gpa) { this.gpa = gpa; }

    /**
     * Returns a human-readable string representation.
     * Automatically called when printing a Student object.
     */
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Dept: %s | GPA: %.2f",
                studentId, name, department, gpa);
    }

    /**
     * Converts student to CSV format for TEXT file storage.
     * Example: "S001,John Doe,CS,3.8"
     */
    public String toFileString() {
        return studentId + "," + name + "," + department + "," + gpa;
    }

    /**
     * Creates a Student object from a CSV line.
     * Static method - belongs to the class, not any instance.
     */
    public static Student fromFileString(String line) {
        String[] parts = line.split(",");
        return new Student(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
    }
}