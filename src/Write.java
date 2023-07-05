import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Write {
    public static void Write(ArrayList<String> arrayList, String fileName) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

            for (String item : arrayList) {
                writer.write(item);
                writer.newLine();
            }

            writer.close();
            System.out.println("File '" + fileName + "' created successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }
}
