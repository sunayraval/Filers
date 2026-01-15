import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileManager {
    public FileManager() {
        System.out.println("File Manager Instantiated");
    }

    public void createFile(String fileName) {
        try {
            File f = new File(fileName);
            if (f.createNewFile()) {
                System.out.println("File created successfully.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        File f = new File(fileName);
        if (f.delete()) {
            System.out.println("File deleted successfully.");
        }
    }

    public void clear(String fileName) {
        writeLine(fileName, "",true);
    }

    public void writeLine(String fileName, String text) {
        try {
        String str = text;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
   
        if (reader.readLine() != null) {
            System.out.println("Printing new line");
            writer.newLine();
        }
        
        writer.append(str);
        writer.close();
        reader.close();

        } catch ( IOException e ) {
            System.out.println(e.getStackTrace());
        }

    }

    public void writeLine(String fileName, String text, boolean replace_mode) {
        try {
        String str = text;
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, !replace_mode));
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
   
        if (reader.readLine() != null) {
            System.out.println("Printing new line");
            writer.newLine();
        }
        
        writer.append(str);
        writer.close();
        reader.close();

        } catch ( IOException e ) {
            System.out.println(e.getStackTrace());
        }

    }


    public void replaceLine(String fileName, String text, int lineIndex) {
        Path path = Paths.get(fileName);
        try {
            // Read all existing lines into a list
            List<String> lines = Files.readAllLines(path);

            // Check if the index is within the existing file bounds
            if (lineIndex >= 0 && lineIndex < lines.size()) {
                lines.set(lineIndex, text);
                
                // Write the modified list back to the file
                Files.write(path, lines);
                System.out.println("Line " + lineIndex + " replaced successfully.");
            } else {
                System.err.println("Error: Index " + lineIndex + " is out of bounds for file " + fileName);
            }

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }

    public void deleteLine(String fileName, int lineIndex) {
        try {
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);
            if (lineIndex >= 0 && lineIndex < lines.size()) {
                lines.remove(lineIndex);
                Files.write(path, lines);
                System.out.println("Line " + lineIndex + " deleted.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean exists(String fileName) {
        return new File(fileName).exists();
    }

    //file returns index of a certain string (trimmed)
    public int getIndexOfFile(String fileName, String target) {
        ArrayList <String> lines = getLines(fileName);
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).trim().equals(target.trim())) {
                return i;
            }
        }
        return -1;
    }

    public void findAndReplace(String fileName, String oldText, String newText) {
        try {
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).contains(oldText)) {
                    lines.set(i, lines.get(i).replace(oldText, newText));
                }
            }
            Files.write(path, lines);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This function returns the entire file broken down into text per line in the format of a String ArrayList
    public ArrayList<String> getLines(String fileName) {
        ArrayList <String> lines = new ArrayList<>();

        try {
        
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

                while ((line = reader.readLine()) != null) {
                    lines.add(line);
                }


            reader.close();
            return lines;

        } catch ( IOException e ) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    public int length(String fileName) {
        int count = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {
                count ++;
            }
            reader.close();

        } catch ( IOException e ) {
            System.out.println(e.getStackTrace());
        }

        return count;
    }

    

    public String readLine(String fileName, int lineNumber) {
        ArrayList <String> lines = new ArrayList<>();

        try {
        
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }


        reader.close();
        return lines.get(lineNumber);

        } catch ( IOException e ) {
            System.out.println(e.getStackTrace());
        }
        return null;
    }

    public static void main(String[] args) {
        FileManager f = new FileManager();
    }
    
}
