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

        for (String word : wordsTxt) {
            trie.insert(word);
        }

        System.out.println("Welcome to our application");
        boolean flag = false;
        int choice;
        while (!flag) {
            System.out.print("Enter a sentence: ");
            String userInput = scanner.nextLine();
            String[] words = userInput.split(" ");
            StringBuilder correctedSentence = new StringBuilder();
            for (String word : words) {
                List<String> suggestions = trie.autoComplete(word);
                List<String> misspelled = trie.CorrectionSuggestions(word); // Correction Suggestions for Misspelled Words
                List<String> mergedList = new ArrayList<>();
                mergedList.addAll(suggestions);
                mergedList.addAll(misspelled);
                System.out.println("LEN "+ words.length+"\t"+ Arrays.toString(words));
                if (mergedList.isEmpty()) {
                    correctedSentence.append(word).append(" ");
                } else if (!trie.search(word)) {
                    System.out.println("Autocomplete suggestions for \"" + word + "\":");
                    for (int i = 0; i < mergedList.size(); i++) {
                        System.out.println((i + 1) + ". " + mergedList.get(i));
                    }
                    System.out.print("Choose a suggestion (1-" + mergedList.size() + "), or 0 to keep the word: ");
                    int nextInt = scanner.nextInt();

                    if (nextInt >= 1 && nextInt <= suggestions.size()) {
                        String chosenSuggestion = suggestions.get(nextInt - 1);
                        correctedSentence.append(chosenSuggestion).append(" ");
                    }else if(nextInt > suggestions.size() && nextInt <= misspelled.size()){
                        String selectedSuggestion = misspelled.get(nextInt - 1);
                        correctedSentence.append((selectedSuggestion.substring(0, selectedSuggestion.indexOf(" (distance:")))).append(" ");
                    }else {
                        correctedSentence.append(word).append(" ");
                    }
                } else {
                    correctedSentence.append(word).append(" ");
                }
            }
            String correctedInput = correctedSentence.toString().trim();
            System.out.println("Corrected sentence: " + correctedInput);
            flag = true;
        }
//        Write.appendStringToFile(correctedInput, "D:\\learn\\Shiraz\\Ds\\DsProject\\src\\inputs.txt");
    }
}
