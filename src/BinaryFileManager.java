import java.io.*;
import java.util.*;


public class BinaryFileManager {

    private File binaryFile;

    public BinaryFileManager(String filePath) {
        binaryFile = new File(filePath);

        File parentDir = binaryFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("[BINARY] Created directory: " + parentDir.getAbsolutePath());
        }

        try {
            if (!binaryFile.exists()) {
                binaryFile.createNewFile();
                System.out.println("[BINARY] Created file: " + binaryFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("[BINARY] Error creating file: " + e.getMessage());
        }
    }


    public void saveStudents(List<Student> students) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(binaryFile))) {

            out.writeInt(students.size());


            for (Student s : students) {
                out.writeUTF(s.getStudentId());
                out.writeUTF(s.getName());
                out.writeUTF(s.getDepartment());
                out.writeDouble(s.getGpa());
            }
            System.out.println("[BINARY] Saved " + students.size() + " students");
        }
    }

    public List<Student> loadStudents() throws IOException {
        List<Student> students = new ArrayList<>();

        if (!binaryFile.exists() || binaryFile.length() == 0) {
            System.out.println("[BINARY] No existing data, starting fresh");
            return students;
        }

        try (DataInputStream in = new DataInputStream(new FileInputStream(binaryFile))) {

            int count = in.readInt();


            for (int i = 0; i < count; i++) {
                String id = in.readUTF();
                String name = in.readUTF();
                String department = in.readUTF();
                double gpa = in.readDouble();

                students.add(new Student(id, name, department, gpa));
            }
            System.out.println("[BINARY] Loaded " + students.size() + " students");
        } catch (EOFException e) {
            System.out.println("[BINARY] Warning: File may be corrupted, loaded " + students.size() + " students");
        }

        return students;
    }

    public void displayFileProperties() {
        System.out.println("\n BINARY FILE PROPERTIES:");
        System.out.println("  ├─ Name: " + binaryFile.getName());
        System.out.println("  ├─ Path: " + binaryFile.getAbsolutePath());
        System.out.println("  ├─ Size: " + binaryFile.length() + " bytes");
        System.out.println("  ├─ Exists: " + binaryFile.exists());
        System.out.println("  ├─ Readable: " + binaryFile.canRead());
        System.out.println("  ├─ Writable: " + binaryFile.canWrite());
        System.out.println("  └─ Last Modified: " + new Date(binaryFile.lastModified()));
    }

    public File getFile() {
        return binaryFile;
    }
}