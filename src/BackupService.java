import java.io.*;
import java.util.*;


public class BackupService {

    private static final String BACKUP_DIR = "backup";


    public static void createBackup(File sourceFile) throws IOException {

        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
            System.out.println("[BACKUP] Created backup directory: " + backupDir.getAbsolutePath());
        }


        String timestamp = new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String backupName = sourceFile.getName().replace(".", "_backup_" + timestamp + ".");
        File backupFile = new File(backupDir, backupName);

        System.out.println("\n CREATING BACKUP...");
        System.out.println("  Source: " + sourceFile.getAbsolutePath());
        System.out.println("  Dest:   " + backupFile.getAbsolutePath());


        long startTime = System.nanoTime();
        copyWithBufferedStreams(sourceFile, backupFile);
        long endTime = System.nanoTime();
        double timeMs = (endTime - startTime) / 1_000_000.0;

        System.out.println("   Backup completed in " + String.format("%.2f", timeMs) + " ms");
        System.out.println("   Backup saved as: " + backupFile.getName());
    }


    private static void copyWithBufferedStreams(File source, File dest) throws IOException {

        String name = source.getName().toLowerCase();

        if (name.endsWith(".txt") || name.endsWith(".ser")) {

            try (BufferedReader reader = new BufferedReader(new FileReader(source));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(dest))) {

                String line;
                int lineCount = 0;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                    lineCount++;
                }
                System.out.println("   Copied " + lineCount + " lines");
            }
        } else {

            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest))) {

                byte[] buffer = new byte[8192];
                int bytesRead;
                int totalBytes = 0;

                while ((bytesRead = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                    totalBytes += bytesRead;
                }

                System.out.println("   Copied " + totalBytes + " bytes (" +
                        String.format("%.2f", totalBytes / 1024.0) + " KB)");
            }
        }
    }


    public static void listBackups() {
        File backupDir = new File(BACKUP_DIR);
        if (!backupDir.exists() || backupDir.listFiles() == null) {
            System.out.println("\n No backups found.");
            return;
        }

        File[] backups = backupDir.listFiles((dir, name) -> name.contains("backup"));
        if (backups == null || backups.length == 0) {
            System.out.println("\n No backups found.");
            return;
        }

        System.out.println("\n AVAILABLE BACKUPS:");
        System.out.println("--------------------------------------------------------");
        for (File backup : backups) {
            System.out.printf("│     %-50s │\n", backup.getName());
            System.out.printf("│     Size: %-6d bytes, Modified: %-19s │\n",
                    backup.length(), new Date(backup.lastModified()));
        }
        System.out.println("---------------------------------------------------------");
    }
}