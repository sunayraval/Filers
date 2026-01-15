import java.util.ArrayList;
// A lightweight data writer and interpreter for saving game data with simple .txt files

//language format
// key split value
public class SunayFiler extends FileManager {
//custom language variables
// "~s~ jacksonian_value $ value"
    final String split_indicator = "$";
    final String type_bool = "~b~";
    final String type_int = "~i~";
    final String type_string = "~s~";


    public void SunayFiler() {
        System.out.println("SunayFiler instantiated");
    }
    //adders
    public void add(String filename, String key, String value) {
        writeLine(filename, parseKeyValuePair(key, value));
    }

    public void add(String filename, String key, boolean value) {
        writeLine(filename, parseKeyValuePair(key, value));
    }

    public void add(String filename, String key, int value) {
        writeLine(filename, parseKeyValuePair(key, value));
    }

    //key value pair parsers
    public String parseKeyValuePair(String key, String value) {
        return (type_string + " "+key + " " + split_indicator + " " + value);
    }
    public String parseKeyValuePair(String key, boolean value) {
        return (type_bool + " " + key + " " + split_indicator + " " + value);
    }
    public String parseKeyValuePair(String key, int value) {
        return (type_int + " "+key + " " + split_indicator + " " + value);
    }
    //key value pair extractors
    public ArrayList<Object> extractKeyValuePair(String input) {
        String currentType = "";
        if (input.startsWith(type_bool)) currentType = type_bool;
        else if (input.startsWith(type_int)) currentType = type_int;
        else if (input.startsWith(type_string)) currentType = type_string;

        String cleanInput = input.substring(currentType.length()).trim();
        String[] parts = cleanInput.split("\\s*\\" + split_indicator + "\\s*");

        String key = parts[0];   // The "key"
        String rawValue = parts[1]; // The "value" string

        ArrayList<Object> result = new ArrayList<>();
        // 3. Add key to list and then convert/add value based on the prefix found
        result.add(key);

        switch (currentType) {
            case type_bool:
                result.add(Boolean.parseBoolean(rawValue));
                break;
            case type_int:
                result.add(Integer.parseInt(rawValue));
                break;
            case type_string:
            default:
                result.add(rawValue);
                break;            
        }
            return (result);
        }
    
    public int getIndexByKey(String filename, String key) {
        ArrayList<String> lines = getLines(filename);
        for (int i = 0; i < lines.size(); i++) {
            ArrayList<Object> extracted = extractKeyValuePair(lines.get(i));
            if (extracted.get(0).equals(key)) {
                return i;
            }
        }
        return -1; // Key not found
    }

    public Object get(String filename, String key) {
        int index = getIndexByKey(filename, key);
        if (index != -1) {
            ArrayList<String> lines = getLines(filename);
            return extractKeyValuePair(lines.get(index)).get(1);
        }
        return null;
    }

    public void update(String filename, String key, Object value) {
        int index = getIndexByKey(filename, key);
        String newValue = "";

        // Determine the type and parse
        if (value instanceof Integer) newValue = parseKeyValuePair(key, (int)value);
        else if (value instanceof Boolean) newValue = parseKeyValuePair(key, (boolean)value);
        else newValue = parseKeyValuePair(key, value.toString());

        if (index != -1) {
            // Key exists, replace it
            replaceLine(filename, newValue, index);
        } else {
            // Key is new, append it
            writeLine(filename, newValue);
        }
    }
    public static void main(String[] args) {
        SunayFiler f = new SunayFiler();
        String val = f.parseKeyValuePair("jacksonian_value", 3);
        f.writeLine("sunay_filer_test.txt",val);
        System.out.println(f.extractKeyValuePair(val).get(0));
        f.clear("sunay_filer_test.txt");

    }

    
}
