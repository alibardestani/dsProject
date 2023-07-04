import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Read {
    public static List<String> readWordsFromFile(String filename) {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineWords = line.split(" ");
                words.addAll(Arrays.asList(lineWords));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }
    public static List<String> readReverceWordFromFile(String filename){
        List<String> words = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                for (String word : lineWords) {
                    StringBuilder reversedWord = new StringBuilder(word).reverse();
                    words.add(String.valueOf(reversedWord));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }
}
