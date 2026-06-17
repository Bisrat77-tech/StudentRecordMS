import java.util.*;
import java.io.*;


public class StudentManager {

    private List<Student> students;
    private int storageType;  // 1=TEXT, 2=BINARY, 3=SERIAL
    private TextFileManager textManager;
    private BinaryFileManager binaryManager;
    private SerializationFileManager serialManager;

    public StudentManager() {
        students = new ArrayList<>();
        storageType = 1;  // Default to TEXT

        // Initialize all three managers
        textManager = new TextFileManager("data/students.txt");
        binaryManager = new BinaryFileManager("data/students.dat");
        serialManager = new SerializationFileManager("data/students.ser");

        // Load existing data
        loadData();
    }

    // ========== STORAGE TYPE MANAGEMENT ==========

    public void setStorageType(int type) {
        if (type >= 1 && type <= 3) {
            // Save current data before switching
            saveData();
            storageType = type;
            // Load data from new storage type
            loadData();
            System.out.println("\n Switched to storage type: " + getStorageTypeName());
        } else {
            System.out.println(" Invalid storage type! Use 1, 2, or 3.");
        }
    }

    public String getStorageTypeName() {
        switch (storageType) {
            case 1: return "TEXT File (CSV)";
            case 2: return "BINARY File (Data Streams)";
            case 3: return "SERIALIZATION (Object Streams)";
            default: return "Unknown";
        }
    }

    // ========== DATA PERSISTENCE ==========

    private void saveData() {
        try {
            switch (storageType) {
                case 1:
                    textManager.saveStudents(students);
                    break;
                case 2:
                    binaryManager.saveStudents(students);
                    break;
                case 3:
                    serialManager.saveStudents(students);
                    break;
            }
        } catch (IOException e) {
            System.out.println(" Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            switch (storageType) {
                case 1:
                    students = textManager.loadStudents();
                    break;
                case 2:
                    students = binaryManager.loadStudents();
                    break;
                case 3:
                    students = serialManager.loadStudents();
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Error loading data: " + e.getMessage());
            students = new ArrayList<>();
        }
    }


    public boolean addStudent(Student student) {
        // Check for duplicate ID
        if (findStudentById(student.getStudentId()) != null) {
            System.out.println(" Student with ID " + student.getStudentId() + " already exists!");
            return false;
        }

        students.add(student);
        saveData();
        System.out.println(" Student added successfully!");
        return true;
    }


    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getStudentId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }


    public void searchAndDisplay(String id) {
        Student s = findStudentById(id);
        if (s != null) {
            System.out.println("\n Student Found:");
            System.out.println("  " + s);
        } else {
            System.out.println(" Student with ID " + id + " not found.");
        }
    }

    public boolean updateStudent(String id, String newName, String newDepartment, double newGpa) {
        Student s = findStudentById(id);
        if (s == null) {
            System.out.println(" Student with ID " + id + " not found.");
            return false;
        }

        if (newName != null && !newName.trim().isEmpty()) {
            s.setName(newName);
        }
        if (newDepartment != null && !newDepartment.trim().isEmpty()) {
            s.setDepartment(newDepartment);
        }
        if (newGpa >= 0.0 && newGpa <= 4.0) {
            s.setGpa(newGpa);
        }

        saveData();
        System.out.println(" Student updated successfully!");
        return true;
    }


    public boolean deleteStudent(String id) {
        Student s = findStudentById(id);
        if (s == null) {
            System.out.println(" Student with ID " + id + " not found.");
            return false;
        }

        students.remove(s);
        saveData();
        System.out.println(" Student deleted successfully!");
        return true;
    }


    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\n No students in the system.");
            return;
        }

        System.out.println("\n ALL STUDENTS (" + students.size() + " records):");
        System.out.println("------------------------------------------------------");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + ". " + students.get(i));
        }
        System.out.println("------------------------------------------------------");
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);  // Return a copy to protect original
    }

    public int getStudentCount() {
        return students.size();
    }

    // ========== FILE PROPERTIES DISPLAY ==========

    public void displayCurrentFileProperties() {
        switch (storageType) {
            case 1:
                textManager.displayFileProperties();
                break;
            case 2:
                binaryManager.displayFileProperties();
                break;
            case 3:
                serialManager.displayFileProperties();
                break;
        }
    }
}