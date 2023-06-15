import java.io.FileWriter;
import java.io.IOException;

public class Write {
    public static void appendStringToFile(String text, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(text + " ");
            writer.write(System.lineSeparator());  // Adds a new line

            System.out.println("Text has been appended to the file: " + fileName);
        } catch (IOException e) {
            System.err.println("An error occurred while appending to the file: " + e.getMessage());
        }
    }
}
