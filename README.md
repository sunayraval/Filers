# Filers
Quick and Easy Java .txt Storage for Games

## Table of Contents
* [FileManager Class](#filemanager-class)
    * [Features](#features)
    * [API Reference](#api-reference)
    * [Usage Examples](#usage-examples)
    * [Notes](#notes)
* [SunayFiler Class](#sunayfiler)
    * [Key Features](#key-features)
    * [Data Format Standard](#data-format-standard)
    * [SunayFiler API Reference](#sunayfiler-api-reference)
    * [SunayFiler Usage Examples](#sunayfiler-usage-examples)
    * [Requirements](#requirements)

---

# FileManager Class

A robust, lightweight Java utility class designed to simplify file I/O operations. It provides a high-level API for creating, deleting, reading, and modifying text files without worrying about complex stream management.

---

## Features

* **Easy Appending:** Quickly add data to files with automatic new-line handling.
* **Direct Editing:** Replace or delete specific lines by their index.
* **Search & Replace:** Find specific text fragments and update them globally within a file.
* **File Analysis:** Get the total line count or find the index of a specific string.
* **Smart Overwriting:** Toggle between appending data or wiping the file for fresh writes.

---

## API Reference

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

## Usage Examples

### 1. Basic Setup and Writing
```java
FileManager fm = new FileManager();
String myFile = "data.txt";

fm.createFile(myFile);
fm.writeLine(myFile, "Hello World!");
fm.writeLine(myFile, "This is line two.");
```

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

## Notes
* **Indices:** All line numbers are 0-based (the first line is index `0`).
* **Exceptions:** Most methods include internal `try-catch` blocks to handle `IOExceptions`.
* **Dependencies:** Requires Java 8 or higher (uses `java.nio.file.Files`).

---

# SunayFiler

`SunayFiler` is a lightweight data writer and interpreter built on top of `FileManager`. It is specifically designed for game development, allowing you to save and load typed variables (Integer, Boolean, String) using a simple, human-readable `.txt` format.

---

## Key Features

* **Type Preservation:** Automatically handles prefixes (`~i~`, `~b~`, `~s~`) so data returns as the correct object type.
* **Key-Value Logic:** Interact with your text files like a Database or HashMap.
* **Smart Updates:** The `update()` method automatically decides whether to overwrite an existing key or append a new one.
* **Custom Format:** Uses a configurable split indicator (`$`) for clean data separation.

---

## Data Format Standard
Data is stored in the following format:
`[TypePrefix] [Key] $ [Value]`

**Example File Content:**
```text
~i~ player_score $ 500
~b~ is_game_over $ false
~s~ player_name $ Jackson
```

---

## SunayFiler API Reference

### Adding & Updating Data
| Method | Description |
| :--- | :--- |
| `add(filename, key, value)` | Appends a new key-value pair to the file. Supports `String`, `int`, and `boolean`. |
| `update(filename, key, value)` | **Recommended.** Checks if a key exists. If found, it updates that line; otherwise, it appends a new one. |

### Retrieval & Parsing
| Method | Description |
| :--- | :--- |
| `get(filename, key)` | Searches the file for the key and returns the value as an `Object`. |
| `getIndexByKey(filename, key)` | Returns the line number (index) where a specific key is stored. |
| `extractKeyValuePair(input)` | Parses a raw string line into an `ArrayList` where index 0 is the Key and index 1 is the Value (Object). |

---

## SunayFiler Usage Examples

### 1. Saving Game Data
The `update` method is the most efficient way to manage save states, as it prevents duplicate keys.

```java
SunayFiler sf = new SunayFiler();
String saveFile = "save_01.txt";

// Saving different types
sf.update(saveFile, "level", 5);
sf.update(saveFile, "playerName", "Hero");
sf.update(saveFile, "hasDoubleJump", true);
```

### 2. Loading Data
Since `get()` returns an `Object`, you can cast it back to your desired type.

```java
int currentLevel = (int) sf.get(saveFile, "level");
String name = (String) sf.get(saveFile, "playerName");
boolean canJump = (boolean) sf.get(saveFile, "hasDoubleJump");

System.out.println("Welcome back " + name + "! You are on level " + currentLevel);
```

### 3. Manual Parsing
If you are iterating through all lines manually:

```java
String rawLine = "~i~ health $ 100";
ArrayList<Object> data = sf.extractKeyValuePair(rawLine);

String key = (String) data.get(0); // "health"
int value = (int) data.get(1);     // 100
```

---

## Requirements
* **Inheritance:** This class extends `FileManager`. Ensure `FileManager.java` is in the same package.
* **Formatting:** Do not manually edit the `.txt` files unless you maintain the `~type~` prefixes and the `$` split indicator, or `extractKeyValuePair` may fail.
