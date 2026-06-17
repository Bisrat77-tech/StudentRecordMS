# Student Record Management System

## 📚 Overview

A comprehensive Java-based student management system that demonstrates **three different file I/O approaches**:

- **Text Files** (Scanner/PrintWriter) - Human-readable CSV format
- **Binary Files** (DataInputStream/DataOutputStream) - Compact binary format
- **Object Serialization** (ObjectInputStream/ObjectOutputStream) - Full object persistence

This project was developed as a Home Test for OOP (Java File I/O) and showcases mastery of Java's file handling capabilities.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| ✅ **Add Student** | Add new students with ID, Name, Department, and GPA |
| ✅ **Search by ID** | Find students using their unique ID |
| ✅ **Update Information** | Modify student details |
| ✅ **Delete Student** | Remove students from the system |
| ✅ **Display All** | View all student records |
| ✅ **Generate Reports** | Statistics: Total, Highest GPA, Lowest GPA, Average GPA |
| ✅ **Backup System** | Create backups using buffered streams |
| ✅ **Multiple Storage** | Switch between 3 storage types at runtime |
| ✅ **File Properties** | View file metadata (name, path, size, last modified) |

---

## 🏗️ Project Structure
```
StudentRecordManagementSystem/
│
├── src/
│ ├── Student.java # Model class (implements Serializable)
│ ├── TextFileManager.java # Text I/O using Scanner/PrintWriter
│ ├── BinaryFileManager.java # Binary I/O using Data streams
│ ├── SerializationFileManager.java # Object I/O using Object streams
│ ├── StudentManager.java # CRUD operations + storage abstraction
│ ├── ReportGenerator.java # Statistics generation
│ ├── BackupService.java # Buffered stream backup
│ └── Main.java # Menu-driven interface
│
├── data/
│ ├── students.txt # Text storage (CSV format)
│ ├── students.dat # Binary storage
│ └── students.ser # Serialization storage
│
├── backup/ # Backup files directory
├── .gitignore # Git ignore file
└── README.md # This file
```
---

---

## 📖 Storage Methods Explained

### 1. Text Files (students.txt)
- **Classes used:** `FileWriter`, `PrintWriter`, `Scanner`
- **Format:** CSV (Comma Separated Values)
- **Example:** `S001,John Doe,CS,3.8`
- **Pros:** Human-readable, can edit in any text editor
- **Cons:** Slower, manual parsing needed

### 2. Binary Files (students.dat)
- **Classes used:** `DataOutputStream`, `DataInputStream`
- **Format:** Raw bytes with type information
- **Order matters:** Write and read must be in same sequence
- **Pros:** Compact, faster, preserves data types
- **Cons:** Not human-readable

### 3. Serialization (students.ser)
- **Classes used:** `ObjectOutputStream`, `ObjectInputStream`
- **Requirement:** Class must implement `Serializable`
- **Pros:** Simplest code, saves entire objects
- **Cons:** Version sensitivity, larger files

---

## 🔧 How to Run

### Prerequisites
- Java 17 or higher installed
- Git (optional, for version control)

### Step 1: Compile All Files

#### Windows (Command Prompt)
```cmd
javac *.java
```
#### Linux/Mac (Terminal)
```
javac *.java
```
### Step 2: Run the Program
```
java Main
```
### Step 3: Follow the Menu
The program will display a menu with 9 options:
```
╔══════════════════════════════════════════════════════════╗
║                         MENU                             ║
╠══════════════════════════════════════════════════════════╣
║  1. Add Student                                          ║
║  2. Search Student by ID                                 ║
║  3. Update Student Information                           ║
║  4. Delete Student                                       ║
║  5. Display All Students                                 ║
║  6. Generate Report (Total, Highest, Lowest, Average)    ║
║  7. Create Backup (Buffered Streams)                     ║
║  8. Change Storage Type                                  ║
║  9. Display File Properties                              ║
║  0. Exit                                                 ║
╚══════════════════════════════════════════════════════════╝
```
## 📝 Sample Data
When you add students, the system stores them in the selected format:
### Text File Format (students.txt)
```
S001,Alice Johnson,CS,3.8
S002,Bob Smith,IT,3.5
S003,Carol Davis,CS,3.9
S004,David Wilson,SE,3.2
S005,Emma Brown,IT,3.7

```
### Binary File Format (students.dat)
```
[Binary - Not human readable]
```
### Serialization Format (students.ser)
```
[Binary - Not human readable]
```

## 📊 Report Example

```
╔══════════════════════════════════════════════════════════╗
║                    STUDENT REPORT                        ║
╚══════════════════════════════════════════════════════════╝
┌────────────────────────────────────────────────────────┐
│  📊 Total Students:  5                                 │
├────────────────────────────────────────────────────────┤
│  ⭐ Highest GPA:     3.90                             │
│      (Student: Carol Davis                            │
├────────────────────────────────────────────────────────┤
│  📉 Lowest GPA:      3.20                             │
│      (Student: David Wilson                           │
├────────────────────────────────────────────────────────┤
│  📈 Average GPA:     3.62                             │
└────────────────────────────────────────────────────────┘

📊 DEPARTMENT BREAKDOWN:
┌────────────────────────────────────────────────────────┐
│  CS         │   2 students │ Avg GPA: 3.85            │
│  IT         │   2 students │ Avg GPA: 3.60            │
│  SE         │   1 students │ Avg GPA: 3.20            │
└────────────────────────────────────────────────────────┘
```
### 🛡️ Exception Handling

```
Exception	Where it's handled	What happens
FileNotFoundException	All Manager classes	File is created automatically
IOException	All save/load methods	Error message displayed, program continues
ClassNotFoundException	SerializationFileManager	Error message displayed, starts fresh
NumberFormatException	Main.java (getDoubleInput)	User is prompted to re-enter
EOFException	BinaryFileManager	Warning displayed, partial data loaded

```
### 🔄 Switching Storage Types

```
The system can switch between storage types at runtime:

1. Select option 8 from the menu

2. Choose:

    1 - TEXT File (CSV)

    2 - BINARY File (Data Streams)

    3 - SERIALIZATION (Object Streams)

3. The system will:

  - Save current data

  - Switch storage type

  - Load data from new storage type
```
### 💾 Backup System

The backup system uses buffered streams for performance:

#### Why Buffered Streams?

**Without buffer:** Reads 1 byte at a time (like carrying one brick)

**With buffer:** Reads 8KB chunks at once (like using a wheelbarrow)

**Performance:** 10-100x faster for large files

#### How to Backup
```
1. Select option 7 from the menu

2. The system creates backups with timestamps:

     - students_backup_20240616_143022.txt

     - students_backup_20240616_143022.dat

     - students_backup_20240616_143022.ser

3. Backups are stored in the backup/ directory
```
## 📁 File Properties Display
**Option 9 shows file metadata:**

```
📁 TEXT FILE PROPERTIES:
  ├─ Name: students.txt
  ├─ Path: C:\Users\...\data\students.txt
  ├─ Size: 245 bytes
  ├─ Exists: true
  ├─ Readable: true
  ├─ Writable: true
  └─ Last Modified: Sun Jun 16 14:30:22 EAT 2024
```

## 📚 Key Java Concepts Used
```
Concept        	                 Where it's used
implements Serializable	               Student.java
try-with-resources	                All Manager classes
DataOutputStream.writeUTF()	        BinaryFileManager.java
ObjectOutputStream.writeObject()	SerializationFileManager.java
Buffered streams	                BackupService.java
File.mkdirs()	                        All Manager constructors
File.length(), lastModified()	        File properties display
Collections (List, ArrayList)	        Throughout
Exception handling	                All classes     

```
## 📋 Requirements Checklist
```
Requirement	                       Status	                                            Implementation
Student class with ID, Name, Dept, GPA	✅	                                                  Student.java
Add/Search/Update/Delete/Display	✅	                                                  StudentManager.java
Text files (Scanner/PrintWriter)	✅	                                                  TextFileManager.java
Binary files (Data streams)		✅                                                         BinaryFileManager.java
Object Serialization		      ✅                                                            SerializationFileManager.java
Report (Total, Highest, Lowest, Average)✅		                                          ReportGenerator.java
File class properties		       ✅                                                           displayFileProperties()
Buffered backup		               ✅                                                           BackupService.java
Exception handling		       ✅                                                      Try-catch throughout
README with system design	     ✅                                                        This file
```
## 📅 Submission Info
**Assignment:** OOP Home Test - Java File I/O

**Deadline:** June 18, 2026

**Presentation:** June 19, 2026

**Author:** Bisrat Zenebe



