import java.io.*;
import java.util.*;

/**
 * Creates backups using BUFFERED streams for better performance.
 * Buffer loads 8KB of data at once instead of 1 byte at a time.
 */
public class BackupService {

    private static final String BACKUP_DIR = "backup";

    /**
     * Creates a backup of the current data file using buffered streams.
     */
    public static void createBackup(File sourceFile) throws IOException {
        // Create backup directory if it doesn't exist
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
            System.out.println("[BACKUP] Created backup directory: " + backupDir.getAbsolutePath());
        }

        // Create backup filename with timestamp
        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupName = sourceFile.getName().replace(".", "_backup_" + timestamp + ".");
        File backupFile = new File(backupDir, backupName);

        System.out.println("\n💾 CREATING BACKUP...");
        System.out.println("  Source: " + sourceFile.getAbsolutePath());
        System.out.println("  Dest:   " + backupFile.getAbsolutePath());

        // Measure time with buffered streams
        long startTime = System.nanoTime();
        copyWithBufferedStreams(sourceFile, backupFile);
        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1_000_000.0;

        System.out.println("  ✅ Backup completed in " + String.format("%.2f", timeMs) + " ms");
        System.out.println("  📁 Backup saved as: " + backupFile.getName());
    }

    /**
     * Copies a file using BUFFERED streams.
     * Buffer size default is 8192 bytes (8KB) - much faster than byte-by-byte!
     */
    private static void copyWithBufferedStreams(File source, File dest) throws IOException {
        // Check file type by extension to choose appropriate buffered streams
        String name = source.getName().toLowerCase();

        if (name.endsWith(".txt") || name.endsWith(".ser")) {
            // TEXT file - use BufferedReader/BufferedWriter (character streams)
            try (BufferedReader reader = new BufferedReader(new FileReader(source));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) {

                String line;
                int lineCount = 0;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                    lineCount++;
                }
                System.out.println("  📝 Copied " + lineCount + " lines");
            }
        } else {
            // BINARY file - use BufferedInputStream/BufferedOutputStream (byte streams)
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest))) {

                byte[] buffer = new byte[8192];  // 8KB buffer
                int bytesRead;
                int totalBytes = 0;

                while ((bytesRead = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }

                System.out.println("  💾 Copied " + totalBytes + " bytes (" +
                        String.format("%.2f", totalBytes / 1024.0) + " KB)");
            }
        }
    }

    /**
     * Lists all available backups.
     */
    public static void listBackups() {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists() || backupDir.listFiles() == null) {
            System.out.println("\n📭 No backups found.");
            return;
        }

        File[] backups = backupDir.listFiles((dir, name) -> name.contains("backup"));
        if (backups == null || backups.length == 0) {
            System.out.println("\n📭 No backups found.");
            return;
        }

        System.out.println("\n📋 AVAILABLE BACKUPS:");
        System.out.println("┌────────────────────────────────────────────────────────┐");
        for (File backup : backups) {
            System.out.printf("│  📁 %-50s │\n", backup.getName());
            System.out.printf("│     Size: %-6d bytes, Modified: %-19s │\n",
                    backup.length(), new Date(backup.lastModified()));
        }
        System.out.println("└────────────────────────────────────────────────────────┘");
    }
}