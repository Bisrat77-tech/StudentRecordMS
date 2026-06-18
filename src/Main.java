import java.util.Scanner;
import java.io.File;
import java.io.IOException;


public class Main {

    private static StudentManager manager = new StudentManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("\n ===========================================================");
        System.out.println("         STUDENT RECORD MANAGEMENT SYSTEM                      ");
        System.out.println("        Java File I/O with Multiple Storage Methods         "  );
        System.out.println(" ============================================================");
        System.out.println("\n Current Storage: " + manager.getStorageTypeName());

        boolean running = true;

        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    searchStudent();
                    break;
                case 3:
                    updateStudent();
                    break;
                case 4:
                    deleteStudent();
                    break;
                case 5:
                    manager.displayAllStudents();
                    break;
                case 6:
                    generateReport();
                    break;
                case 7:
                    backupData();
                    break;
                case 8:
                    changeStorageType();
                    break;
                case 9:
                    manager.displayCurrentFileProperties();
                    break;
                case 0:
                    running = false;
                    System.out.println("\n  Thank you for using the system! Goodbye!");
                    break;
                default:
                    System.out.println(" Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n==========================================================");
        System.out.println("                         MENU                               ");
        System.out.println(" ===========================================================");
        System.out.println("   1. Add Student                                           ");
        System.out.println("   2. Search Student by ID                                  ");
        System.out.println("   3. Update Student Information                            ");
        System.out.println("   4. Delete Student                                        ");
        System.out.println("   5. Display All Students                                  ");
        System.out.println("   6. Generate Report (Total, Highest, Lowest, Average)     ");
        System.out.println("   7. Create Backup (Buffered Streams)                      ");
        System.out.println("   8. Change Storage Type                                   ");
        System.out.println("   9. Display File Properties                               ");
        System.out.println("   0. Exit                                                  ");
        System.out.println(" ===========================================================");
        System.out.println(" Current Storage: " + manager.getStorageTypeName());
    }

    private static void addStudent() {
        System.out.println("\n  ADD NEW STUDENT");
        System.out.println("----------------------");

        String id = getStringInput("Enter Student ID (e.g., S001): ");


        if (manager.findStudentById(id) != null) {
            System.out.println(" Student with this ID already exists!");
            return;
        }

        String name = getStringInput("Enter Name: ");
        String department = getStringInput("Enter Department (CS/IT/SE/EE): ");
        double gpa = getDoubleInput("Enter GPA (0.0 - 4.0): ", 0.0, 4.0);

        Student student = new Student(id, name, department, gpa);
        manager.addStudent(student);
    }

    private static void searchStudent() {
        System.out.println("\n SEARCH STUDENT");
        System.out.println("-------------------");
        String id = getStringInput("Enter Student ID: ");
        manager.searchAndDisplay(id);
    }

    private static void updateStudent() {
        System.out.println("\n️ UPDATE STUDENT");
        System.out.println("--------------------");
        String id = getStringInput("Enter Student ID to update: ");

        Student existing = manager.findStudentById(id);
        if (existing == null) {
            System.out.println(" Student not found!");
            return;
        }

        System.out.println("Current info: " + existing);
        System.out.println("\n(Leave blank to keep current value)");

        String newName = getStringInput("New Name [" + existing.getName() + "]: ");
        if (newName.trim().isEmpty()) newName = null;

        String newDept = getStringInput("New Department [" + existing.getDepartment() + "]: ");
        if (newDept.trim().isEmpty()) newDept = null;

        double newGpa = existing.getGpa();
        String gpaInput = getStringInput("New GPA [" + existing.getGpa() + "]: ");
        if (!gpaInput.trim().isEmpty()) {
            try {
                newGpa = Double.parseDouble(gpaInput);
                if (newGpa < 0 || newGpa > 4.0) {
                    System.out.println("⚠️ GPA must be between 0 and 4. Keeping original value.");
                    newGpa = existing.getGpa();
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid GPA format. Keeping original value.");
                newGpa = existing.getGpa();
            }
        }

        manager.updateStudent(id, newName, newDept, newGpa);
    }

    private static void deleteStudent() {
        System.out.println("\n DELETE STUDENT");
        System.out.println("--------------------");
        String id = getStringInput("Enter Student ID to delete: ");

        Student existing = manager.findStudentById(id);
        if (existing == null) {
            System.out.println(" Student not found!");
            return;
        }

        System.out.println("Student to delete: " + existing);
        String confirm = getStringInput("Are you sure? (y/n): ");

        if (confirm.equalsIgnoreCase("y")) {
            manager.deleteStudent(id);
        } else {
            System.out.println(" Deletion cancelled.");
        }
    }

    private static void generateReport() {
        ReportGenerator report = new ReportGenerator(manager.getAllStudents());
        report.generateFullReport();
    }

    private static void backupData() {
        System.out.println("\n BACKUP SYSTEM");
        System.out.println("-------------------");

        try {
            // Backup all three data files
            File textFile = new File("data/students.txt");
            File binaryFile = new File("data/students.dat");
            File serialFile = new File("data/students.ser");

            if (textFile.exists()) {
                BackupService.createBackup(textFile);
            }
            if (binaryFile.exists()) {
                BackupService.createBackup(binaryFile);
            }
            if (serialFile.exists()) {
                BackupService.createBackup(serialFile);
            }

            if (!textFile.exists() && !binaryFile.exists() && !serialFile.exists()) {
                System.out.println("No data files found to backup.");
            }

            BackupService.listBackups();

        } catch (IOException e) {
            System.out.println(" Backup failed: " + e.getMessage());
        }
    }

    private static void changeStorageType() {
        System.out.println("\n CHANGE STORAGE TYPE");
        System.out.println("-------------------------");
        System.out.println("  1. TEXT File (CSV - human readable)");
        System.out.println("  2. BINARY File (Data streams - compact)");
        System.out.println("  3. SERIALIZATION (Object streams - easiest)");
        System.out.println("  0. Cancel");

        int choice = getIntInput("Choose storage type: ");

        if (choice == 0) {
            System.out.println("Operation cancelled.");
            return;
        }

        manager.setStorageType(choice);
    }

    // ========== HELPER METHODS FOR INPUT ==========

    private static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print(" Please enter a valid number: ");
            }
        }
    }

    private static double getDoubleInput(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.print(" Please enter a value between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print(" Please enter a valid number: ");
            }
        }
    }
}