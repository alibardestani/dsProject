import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.*;

public class Main extends Application {
    private List<String> words;
    private List<String> wordsReverse;
    private List<String> history;
    private Trie trie;
    private Trie trieReverse;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        readWordsFromFile();
        trie = new Trie();
        trieReverse = new Trie();

        for (String word : words) {
            trie.insert(word);
        }
        for (String word : wordsReverse) {
            trieReverse.insert(word);
        }

        VBox root = new VBox(10);
        root.setPrefWidth(400);
        root.setPrefHeight(300);

        Label inputLabel = new Label("Please Enter String:");
        TextField inputField = new TextField();

        ListView<String> listView = new ListView<>();
        listView.setPrefHeight(150);

        Button processButton = new Button("Process");
        processButton.setOnAction(e -> processInput(inputField.getText(), listView));

        root.getChildren().addAll(inputLabel, inputField, listView, processButton);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("JavaFX Trie Application");
        primaryStage.show();
    }

    private void readWordsFromFile() {
        words = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        wordsReverse = Read.readReverceWordFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        history = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\history.txt");
    }

    private void processInput(String input, ListView<String> listView) {
        List<String> str = Arrays.asList(input.split(" "));

        for (int i = 0; i < str.size(); i++) {
            String word = str.get(i);
            listView.getItems().add(word);
            System.out.println("\n===> " + word);
            int choice = showChoiceDialog();

            switch (choice) {
                case 1 -> {
                    List<String> newSuggestions = sortList(trie.autocomplete(word), history);
                    int selectedChoice = showListDialog(newSuggestions);
                    if (selectedChoice != -1) {
                        str.set(i, newSuggestions.get(selectedChoice));
                        System.out.println();
                        history.add(newSuggestions.get(selectedChoice));
                        System.out.println("History\t" + history + "\t" + newSuggestions.get(selectedChoice));
                    }
                }
                case 2 -> {
                    if (trie.search(word) || trieReverse.search(word)) {
                        System.out.println("It is Ok!");
                    } else {
                        i--;
                    }
                }
                case 3 -> {
                    ArrayList<HashMap<String, Integer>> suggestions = ConnectTwoArray(
                            trie.Suggestions(word), reverseStrings(trieReverse.Suggestions(new StringBuilder(word).reverse().toString())));
                    ArrayList<HashMap<String, Integer>> newSuggestions = sortList(suggestions, history);
                    int userChoice = showHashMapDialog(newSuggestions);
                    if (userChoice >= 0 && userChoice < newSuggestions.size()) {
                        HashMap<String, Integer> suggestion = newSuggestions.get(userChoice);
                        String chosenString = suggestion.keySet().iterator().next();
                        System.out.println("User chose: " + chosenString);
                        str.set(i, chosenString);
                        history.add(chosenString);
                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
                default -> {
                    // Do nothing
                }
            }
        }

        listView.getItems().clear();
        listView.getItems().addAll(str);
        Write.WriteToFile(history, "D:\\learn\\Shiraz\\Ds\\DsProject\\src\\history.txt");

        int lastChoice = showContinueDialog();
        if (lastChoice != 1) {
            Platform.exit();
        }
    }

    private int showChoiceDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choice");
        alert.setHeaderText(null);
        alert.setContentText("Choose an action:");
        ButtonType autoCompleteButton = new ButtonType("AutoComplete");
        ButtonType spellCheckButton = new ButtonType("Spell Check");
        ButtonType suggestionsButton = new ButtonType("Suggestions");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(autoCompleteButton, spellCheckButton, suggestionsButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == autoCompleteButton) {
                return 1;
            } else if (result.get() == spellCheckButton) {
                return 2;
            } else if (result.get() == suggestionsButton) {
                return 3;
            }
        }
        return 0;
    }

    private int showListDialog(List<String> suggestions) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(suggestions.get(0), suggestions);
        dialog.setTitle("AutoComplete");
        dialog.setHeaderText("Select a suggestion:");
        dialog.setContentText("Suggestion:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return suggestions.indexOf(result.get());
        }
        return -1;
    }

    private int showHashMapDialog(ArrayList<HashMap<String, Integer>> suggestions) {
        ListView<String> listView = new ListView<>();
        for (HashMap<String, Integer> suggestion : suggestions) {
            String key = suggestion.keySet().iterator().next();
            Integer value = suggestion.get(key);
            listView.getItems().add(key + " => " + value);
        }

        Dialog<Integer> dialog = new Dialog<>();
        dialog.setTitle("Suggestions");
        dialog.setHeaderText("Select a suggestion:");
        dialog.getDialogPane().setContent(listView);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                int index = listView.getSelectionModel().getSelectedIndex();
                if (index >= 0 && index < suggestions.size()) {
                    return index;
                }
            }
            return -1;
        });

        Optional<Integer> result = dialog.showAndWait();
        return result.orElse(-1);
    }

    private int showContinueDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Continue?");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to continue?");
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == yesButton) {
                return 1;
            }
        }
        return 0;
    }

    public static ArrayList<HashMap<String, Integer>> ConnectTwoArray(ArrayList<HashMap<String, Integer>> pre, ArrayList<HashMap<String, Integer>> suf) {
        ArrayList<HashMap<String, Integer>> combined = new ArrayList<>(pre);
        combined.addAll(suf);

        combined.sort(Comparator.comparingInt(hashMap -> hashMap.values().iterator().next()));

        ArrayList<HashMap<String, Integer>> result = new ArrayList<>();

        for (int i = 0; i < Math.min(5, combined.size()); i++) {
            HashMap<String, Integer> hashMap = combined.get(i);
            result.add(hashMap);
        }

        return result;
    }

    private static ArrayList<HashMap<String, Integer>> reverseStrings(ArrayList<HashMap<String, Integer>> list) {
        for (HashMap<String, Integer> hashMap : list) {
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                String reversedKey = new StringBuilder(key).reverse().toString();
                hashMap.put(reversedKey, value);
                hashMap.remove(key);
            }
        }
        return list;
    }

    public static List<String> sortList(List<String> list1, List<String> list2) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : list2) {
            if (list1.contains(word)) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        list1.sort((a, b) -> wordCount.getOrDefault(b, 0) - wordCount.getOrDefault(a, 0));

        return list1;
    }

    public static ArrayList<HashMap<String, Integer>> sortList(ArrayList<HashMap<String, Integer>> list1, List<String> list2) {
        HashMap<String, Integer> wordCount = new HashMap<>();

        for (HashMap<String, Integer> word : list1) {
            String wordString = word.keySet().iterator().next();
            if (list2.contains(wordString)) {
                wordCount.put(wordString, wordCount.getOrDefault(wordString, 0) + 1);
            }
        }

        list1.sort((a, b) -> wordCount.getOrDefault(b.keySet().iterator().next(), 0) -
                wordCount.getOrDefault(a.keySet().iterator().next(), 0));

        return list1;
    }

    private void initializeTrie(Trie trie, Trie trieReverse, List<String> words, List<String> wordsReverse) {
        for (String word : words) {
            trie.insert(word);
        }
        for (String word : wordsReverse) {
            trieReverse.insert(word);
        }
    }
}
