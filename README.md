# Filers
Quick and Easy Java txt Storage for Games
# FileManager Class

A robust, lightweight Java utility class designed to simplify file I/O operations. It provides a high-level API for creating, deleting, reading, and modifying text files without worrying about complex stream management.

---

## 🚀 Features

* **Easy Appending:** Quickly add data to files with automatic new-line handling.
* **Direct Editing:** Replace or delete specific lines by their index.
* **Search & Replace:** Find specific text fragments and update them globally within a file.
* **File Analysis:** Get the total line count or find the index of a specific string.
* **Smart Overwriting:** Toggle between appending data or wiping the file for fresh writes.

---

## 📖 API Reference

### File Lifecycle
| Method | Description |
| :--- | :--- |
| `createFile(String fileName)` | Creates a new empty file on the disk if it doesn't already exist. |
| `deleteFile(String fileName)` | Permanently removes the file from the disk. |
| `clear(String fileName)` | Wipes all content from the file, resetting its size to zero. |
| `exists(String fileName)` | Returns `true` if the file exists, `false` otherwise. |

### Writing & Modifying
| Method | Description |
| :--- | :--- |
| `writeLine(String fileName, String text)` | Appends text to the end of the file. Adds a new line automatically if the file isn't empty. |
| `writeLine(String fileName, String text, boolean replaceMode)` | If `replaceMode` is true, it overwrites the file with the new text. |
| `replaceLine(String fileName, String text, int lineIndex)` | Swaps the content of a specific line (0-indexed) with new text. |
| `deleteLine(String fileName, int lineIndex)` | Removes a specific line entirely, shifting subsequent lines up. |
| `findAndReplace(String fileName, String oldText, String newText)` | Scans the entire file and replaces all occurrences of `oldText` with `newText`. |

### Reading & Searching
| Method | Description |
| :--- | :--- |
| `getLines(String fileName)` | Returns an `ArrayList<String>` containing every line in the file. |
| `readLine(String fileName, int lineNumber)` | Retrieves the text of a single specific line. |
| `length(String fileName)` | Returns the total number of lines in the file. |
| `getIndexOfFile(String fileName, String target)` | Finds the first line index that matches the `target` string (trimmed). Returns -1 if not found. |

---

## 🛠 Usage Examples

### 1. Basic Setup and Writing
```java
FileManager fm = new FileManager();
String myFile = "data.txt";

fm.createFile(myFile);
fm.writeLine(myFile, "Hello World!");
fm.writeLine(myFile, "This is line two.");

### 2. Updating a Specific Line
```java
// Change "Hello World!" (index 0) to "Welcome Home!"
fm.replaceLine("data.txt", "Welcome Home!", 0);
```

### 3. Searching for Data
```java
int index = fm.getIndexOfFile("data.txt", "This is line two.");
if (index != -1) {
    System.out.println("Found text at line: " + index);
}
```

### 4. Clearing the File
```java
fm.clear("data.txt"); // The file still exists but is now empty.
```

---

## ⚠️ Notes
* **Indices:** All line numbers are 0-based (the first line is index `0`).
* **Exceptions:** Most methods include internal `try-catch` blocks to handle `IOExceptions`.
* **Dependencies:** Requires Java 8 or higher (uses `java.nio.file.Files`).
