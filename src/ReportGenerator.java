import java.util.*;


public class ReportGenerator {

    private List<Student> students;

    public ReportGenerator(List<Student> students) {
        this.students = students;
    }


    public void generateFullReport() {
        if (students.isEmpty()) {
            System.out.println("\n No data available for report.");
            return;
        }

        int total = students.size();
        double highest = getHighestGpa();
        double lowest = getLowestGpa();
        double average = getAverageGpa();
        Student highestStudent = getStudentWithHighestGpa();
        Student lowestStudent = getStudentWithLowestGpa();

        System.out.println("\n==========================================================");
        System.out.println("                    STUDENT REPORT                          ");
        System.out.println("=============================================================");
        System.out.println("-------------------------------------------------------------");
        System.out.printf("│    Total Students:  %-40s │\n", total);
        System.out.println("--------------------------------------------------------------");
        System.out.printf("│    Highest GPA:     %-40.2f │\n", highest);
        if (highestStudent != null) {
            System.out.printf("│      (Student: %-41s │\n", highestStudent.getName());
        }
        System.out.println("---------------------------------------------------------------");
        System.out.printf("│    Lowest GPA:      %-40.2f │\n", lowest);
        if (lowestStudent != null) {
            System.out.printf("│      (Student: %-41s │\n", lowestStudent.getName());
        }
        System.out.println("---------------------------------------------------------------");
        System.out.printf("│   Average GPA:     %-40.2f │\n", average);
        System.out.println("---------------------------------------------------------------");

        // Department-wise breakdown
        generateDepartmentBreakdown();
    }

    private double getHighestGpa() {
        return students.stream()
                .mapToDouble(Student::getGpa)
                .max()
                .orElse(0.0);
    }

    private double getLowestGpa() {
        return students.stream()
                .mapToDouble(Student::getGpa)
                .min()
                .orElse(0.0);
    }

    private double getAverageGpa() {
        return students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }

    private Student getStudentWithHighestGpa() {
        return students.stream()
                .max(Comparator.comparingDouble(Student::getGpa))
                .orElse(null);
    }

    private Student getStudentWithLowestGpa() {
        return students.stream()
                .min(Comparator.comparingDouble(Student::getGpa))
                .orElse(null);
    }

    private void generateDepartmentBreakdown() {
        Map<String, List<Student>> deptMap = new HashMap<>();

        for (Student s : students) {
            deptMap.computeIfAbsent(s.getDepartment(), k -> new ArrayList<>()).add(s);
        }

        System.out.println("\n DEPARTMENT BREAKDOWN:");
        System.out.println("-----------------------------------------------------");
        for (Map.Entry<String, List<Student>> entry : deptMap.entrySet()) {
            String dept = entry.getKey();
            List<Student> deptStudents = entry.getValue();
            double deptAvg = deptStudents.stream()
                    .mapToDouble(Student::getGpa)
                    .average()
                    .orElse(0.0);

            System.out.printf("│  %-10s │ %3d students │ Avg GPA: %.2f │\n",
                    dept, deptStudents.size(), deptAvg);
        }
        System.out.println("------------------------------------------------------");
    }
}