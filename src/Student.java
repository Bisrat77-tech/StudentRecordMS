import java.io.Serializable;
import java.io.Serial;


public class Student implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Private fields - data hiding principle
    private String studentId;
    private String name;
    private String department;
    private double gpa;


    public Student(String studentId, String name, String department, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.gpa = gpa;
    }


    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getGpa() { return gpa; }


    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setGpa(double gpa) { this.gpa = gpa; }


    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Dept: %s | GPA: %.2f",
                studentId, name, department, gpa);
    }


    public String toFileString() {
        return studentId + "," + name + "," + department + "," + gpa;
    }


    public static Student fromFileString(String line) {
        String[] parts = line.split(",");
        return new Student(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]));
    }
}