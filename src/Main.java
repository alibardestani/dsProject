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

//        System.out.print("Enter a sentence: ");
//        String userInput = scanner.nextLine();

//        String[] words = userInput.split(" ");

        for (String word : wordsTxt) {
            trie.insert(word);
        }

//        StringBuilder correctedSentence = new StringBuilder();
//        for (String word : words) {
//            List<String> suggestions = trie.autoComplete(word);
//
//            if (suggestions.isEmpty()) {
//                correctedSentence.append(word).append(" ");
//            } else if (!trie.search(word)) {
//                System.out.println("Autocomplete suggestions for \"" + word + "\":");
//                System.out.println(0+". "+word);
//                for (int i = 0; i < suggestions.size(); i++) {
//                    System.out.println((i + 1) + ". " + suggestions.get(i));
//                }
//
//                System.out.print("Choose a suggestion (1-" + suggestions.size() + "), or 0 to keep the word: ");
//                int choice = scanner.nextInt();
//
//                if (choice >= 1 && choice <= suggestions.size()) {
//                    String chosenSuggestion = suggestions.get(choice - 1);
//                    correctedSentence.append(chosenSuggestion).append(" ");
//                }else if (choice == 0){
//                    correctedSentence.append(word).append(" ");
//                }else {
//                    correctedSentence.append(word).append(" ");
//                }
//            } else {
//                correctedSentence.append(word).append(" ");
//            }
//        }
//
//        String correctedInput = correctedSentence.toString().trim();
//        System.out.println("Corrected sentence: " + correctedInput);




        // SUJJEST WORD ****************

        System.out.print("Enter a misspelled word: ");
        String misspelledWord = scanner.nextLine();

        // Get correction suggestions for the misspelled word
        List<String> suggestions = trie.CorrectionSuggestions(misspelledWord);

        // Print the suggestions
        System.out.println("Correction suggestions for '" + misspelledWord + "':");
        for (int i = 0; i < suggestions.size(); i++) {
            System.out.println((i + 1) + ". " + suggestions.get(i));
        }

        if (!suggestions.isEmpty()) {
            System.out.print("Enter the number of the suggestion to replace with: ");
            int choice = scanner.nextInt();

            if (choice >= 1 && choice <= suggestions.size()) {
                String selectedSuggestion = suggestions.get(choice - 1);
                String correctedWord = selectedSuggestion.substring(0, selectedSuggestion.indexOf(" (distance:"));

                System.out.println("Selected suggestion: " + correctedWord);

                // Replace the misspelled word with the selected suggestion
                // You can now use the correctedWord variable in your code
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("No suggestions available.");
        }




//        Write.appendStringToFile(correctedInput, "D:\\learn\\Shiraz\\Ds\\DsProject\\src\\inputs.txt");
    }
}
