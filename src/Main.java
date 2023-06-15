import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Trie {
    public static void main(String[] args) {
        Trie trie = new Trie();
        Scanner scanner = new Scanner(System.in);
        List<String> wordsTxt = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> userInputs = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\inputs.txt");

        System.out.print("Enter a sentence: ");
        String userInput = scanner.nextLine();

        String[] words = userInput.split(" ");

        for (String word : wordsTxt) {
            trie.insert(word);
        }

        StringBuilder correctedSentence = new StringBuilder();
        for (String word : words) {
            List<String> suggestions = trie.autoComplete(word);

            if (suggestions.isEmpty()) {
                correctedSentence.append(word).append(" ");
            } else if (!trie.search(word)) {
                System.out.println("Autocomplete suggestions for \"" + word + "\":");
                for (int i = 0; i < suggestions.size(); i++) {
                    System.out.println((i + 1) + ". " + suggestions.get(i));
                }

                System.out.print("Choose a suggestion (1-" + suggestions.size() + "), or 0 to keep the word: ");
                int choice = scanner.nextInt();

                if (choice >= 1 && choice <= suggestions.size()) {
                    String chosenSuggestion = suggestions.get(choice - 1);
                    correctedSentence.append(chosenSuggestion).append(" ");
                } else {
                    correctedSentence.append(word).append(" ");
                }
            } else {
                correctedSentence.append(word).append(" ");
            }
        }

        String correctedInput = correctedSentence.toString().trim();
        System.out.println("Corrected sentence: " + correctedInput);

        Write.appendStringToFile(correctedInput, "D:\\learn\\Shiraz\\Ds\\DsProject\\src\\inputs.txt");
    }
}
