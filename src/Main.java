import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private Trie trie;
    private TextArea userInputTextArea;
    private TextArea correctedSentenceTextArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        trie = new Trie();

        // Replace the file paths with your own
        List<String> wordsTxt = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> userInputs = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\inputs.txt");

        for (String word : wordsTxt) {
            trie.insert(word);
        }

        primaryStage.setTitle("Spell Checker");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        Label userInputLabel = new Label("Enter a sentence:");
        GridPane.setConstraints(userInputLabel, 0, 0);
        grid.getChildren().add(userInputLabel);

        userInputTextArea = new TextArea();
        GridPane.setConstraints(userInputTextArea, 0, 1);
        grid.getChildren().add(userInputTextArea);

        Button checkButton = new Button("Check Spelling");
        GridPane.setConstraints(checkButton, 0, 2);
        grid.getChildren().add(checkButton);

        checkButton.setOnAction(event -> {
            String userInput = userInputTextArea.getText();
            String[] words = userInput.split(" ");
            StringBuilder correctedSentence = new StringBuilder();
            for (String word : words) {
                List<String> suggestions = trie.autoComplete(word);
                List<String> misspelled = trie.CorrectionSuggestions(word);
                List<String> mergedList = new ArrayList<>();
                mergedList.addAll(suggestions);
                mergedList.addAll(misspelled);

                if (mergedList.isEmpty()) {
                    correctedSentence.append(word).append(" ");
                } else if (!trie.search(word)) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Spelling Suggestions");
                    alert.setHeaderText("Autocomplete suggestions for \"" + word + "\":");
                    alert.setContentText(String.join("\n", mergedList));

                    ButtonType keepWordButton = new ButtonType("Keep Word");
                    alert.getButtonTypes().add(keepWordButton);

                    for (int i = 0; i < mergedList.size(); i++) {
                        ButtonType suggestionButton = new ButtonType(mergedList.get(i));
                        alert.getButtonTypes().add(suggestionButton);
                    }

                    alert.showAndWait().ifPresent(buttonType -> {
                        if (buttonType.equals(keepWordButton)) {
                            correctedSentence.append(word).append(" ");
                        } else {
                            String buttonText = buttonType.getText();
                            correctedSentence.append(buttonText).append(" ");
                        }
                    });
                } else {
                    correctedSentence.append(word).append(" ");
                }
            }

            String correctedInput = correctedSentence.toString().trim();
            correctedSentenceTextArea.setText(correctedInput);
        });

        Label correctedSentenceLabel = new Label("Corrected sentence:");
        GridPane.setConstraints(correctedSentenceLabel, 0, 3);
        grid.getChildren().add(correctedSentenceLabel);

        correctedSentenceTextArea = new TextArea();
        GridPane.setConstraints(correctedSentenceTextArea, 0, 4);
        grid.getChildren().add(correctedSentenceTextArea);

        VBox vbox = new VBox(20);
        vbox.getChildren().addAll(grid);

        Scene scene = new Scene(vbox, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
