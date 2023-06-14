import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class Write {
        public static void appendArrayListToFile(List<String> list, String fileName) {
            try (FileWriter writer = new FileWriter(fileName, true)) {
                for (String element : list) {
                    writer.write(element + " ");
                }
                writer.write(System.lineSeparator());  // Adds a new line

                System.out.println("ArrayList has been appended to the file: " + fileName);
            } catch (IOException e) {
                System.err.println("An error occurred while appending to the file: " + e.getMessage());
            }
        }
}

