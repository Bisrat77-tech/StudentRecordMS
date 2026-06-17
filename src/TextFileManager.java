import java.io.*;
import java.util.*;


public class TextFileManager {

    private File textFile;


    public TextFileManager(String filePath) {
        textFile = new File(filePath);

        // Create parent directories (e.g., "data/" folder)
        File parentDir = textFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("[TEXT] Created directory: " + parentDir.getAbsolutePath());
        }

        // Create the file if it doesn't exist
        try {
            if (!textFile.exists()) {
                textFile.createNewFile();
                System.out.println("[TEXT] Created file: " + textFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("[TEXT] Error creating file: " + e.getMessage());
        }
    }


    public void saveStudents(List<Student> students) throws IOException {
        // try-with-resources - automatically closes the writer
        try (PrintWriter writer = new PrintWriter(new FileWriter(textFile))) {
            for (Student s : students) {
                writer.println(s.toFileString());
            }
            System.out.println("[TEXT] Saved " + students.size() + " students");
        }
    }


    public List<Student> loadStudents() throws IOException {
        List<Student> students = new ArrayList<>();

        // Check if file exists and has content
        if (!textFile.exists() || textFile.length() == 0) {
            System.out.println("[TEXT] No existing data, starting fresh");
            return students;
        }

        try (Scanner scanner = new Scanner(textFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        students.add(Student.fromFileString(line));
                    } catch (Exception e) {
                        System.out.println("[TEXT] Skipping bad line: " + line);
                    }
                }
            }
            System.out.println("[TEXT] Loaded " + students.size() + " students");
        }

        return students;
    }


    public void displayFileProperties() {
        System.out.println("\n TEXT FILE PROPERTIES:");
        System.out.println("  ├─ Name: " + textFile.getName());
        System.out.println("  ├─ Path: " + textFile.getAbsolutePath());
        System.out.println("  ├─ Size: " + textFile.length() + " bytes");
        System.out.println("  ├─ Exists: " + textFile.exists());
        System.out.println("  ├─ Readable: " + textFile.canRead());
        System.out.println("  ├─ Writable: " + textFile.canWrite());
        System.out.println("  └─ Last Modified: " + new Date(textFile.lastModified()));
    }

    public File getFile() {
        return textFile;
    }
}