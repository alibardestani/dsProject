import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Trie {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> words = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> userInputs = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\inputs.txt");

        Trie Keyboard = new Trie();
        Trie Analyze = new Trie();

        for (String key : words) {
            Keyboard.insert(key);
        }
        for (String key : words) {
            Analyze.insert(key);
        }

        String[] ArrayWord;
        String[] CompleteArrayWord1;
        String[] CompleteArrayWord2;
        String userInput = input.nextLine();
        ArrayWord = userInput.split(" ");
        for(int i=0; i<ArrayWord.length; i++){
            if(!Analyze.search(ArrayWord[i]) && !Keyboard.search(ArrayWord[i])) {
                CompleteArrayWord2 = Analyze.autoComplete(ArrayWord[i]).toArray(new String[0]);
                if (CompleteArrayWord2.length < 5) {
                    CompleteArrayWord1 = Keyboard.autoComplete(ArrayWord[i]).toArray(new String[0]);
                    System.out.println("Please Enter Index For " + ArrayWord[i] + " :\n" + Arrays.toString(CompleteArrayWord2) + Arrays.toString(CompleteArrayWord1));
                    int x = input.nextInt();
                    if (x >= CompleteArrayWord2.length) {
                        ArrayWord[i] = CompleteArrayWord1[x];
                    } else {
                        ArrayWord[i] = CompleteArrayWord2[x];
                    }
                } else {
                    System.out.println("Please Enter Index For " + ArrayWord[i] + " :\n" + Arrays.toString(CompleteArrayWord2));
                    int x = input.nextInt();
                    ArrayWord[i] = CompleteArrayWord2[x];
                }
                for (String it : ArrayWord)
                    System.out.print(it + " ");
                System.out.println();
            }
        }
        Write.appendArrayListToFile(List.of(ArrayWord), "D:\\learn\\Shiraz\\Ds\\DsProject\\src\\inputs.txt");

    }
}
