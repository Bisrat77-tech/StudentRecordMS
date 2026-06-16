import java.io.*;
import java.util.*;

/**
 * Handles reading/writing students using OBJECT SERIALIZATION.
 * Uses: ObjectOutputStream, ObjectInputStream
 * Simplest code - saves entire objects at once!
 *
 * Requirement: Student class must implement Serializable (it does!)
 */
public class SerializationFileManager {

    private File serFile;

    public SerializationFileManager(String filePath) {
        serFile = new File(filePath);

        File parentDir = serFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("[SERIAL] Created directory: " + parentDir.getAbsolutePath());
        }

        try {
            if (!serFile.exists()) {
                serFile.createNewFile();
                System.out.println("[SERIAL] Created file: " + serFile.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("[SERIAL] Error creating file: " + e.getMessage());
        }
    }

    /**
     * Saves entire list of students as ONE object.
     * This is the simplest approach - Java handles all the details.
     */
    public void saveStudents(List<Student> students) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(serFile))) {
            out.writeObject(students);  // Freeze-dry the entire list
            System.out.println("[SERIAL] Saved " + students.size() + " students");
        }
    }

    /**
     * Loads entire list of students as ONE object.
     * Must cast back to ArrayList<Student>.
     */
    @SuppressWarnings("unchecked")
    public List<Student> loadStudents() throws IOException, ClassNotFoundException {
        if (!serFile.exists() || serFile.length() == 0) {
            System.out.println("[SERIAL] No existing data, starting fresh");
            return new ArrayList<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(serFile))) {
            List<Student> students = (ArrayList<Student>) in.readObject();  // Thaw the list
            System.out.println("[SERIAL] Loaded " + students.size() + " students");
            return students;
        }
    }

    public void displayFileProperties() {
        System.out.println("\n📁 SERIALIZATION FILE PROPERTIES:");
        System.out.println("  ├─ Name: " + serFile.getName());
        System.out.println("  ├─ Path: " + serFile.getAbsolutePath());
        System.out.println("  ├─ Size: " + serFile.length() + " bytes");
        System.out.println("  ├─ Exists: " + serFile.exists());
        System.out.println("  ├─ Readable: " + serFile.canRead());
        System.out.println("  ├─ Writable: " + serFile.canWrite());
        System.out.println("  └─ Last Modified: " + new Date(serFile.lastModified()));
    }

    public File getFile() {
        return serFile;
    }
}