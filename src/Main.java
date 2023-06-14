import java.io.FileWriter;
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

        Trie Analyse = new Trie();

        for (String key : words) {
            Keyboard.insert(key);
        }
        for(String key : userInputs){
            Analyse.insert(key);
        }


        String[] ArrayWord;
        String[] CopmleteArrayWord;
        String userInput = input.nextLine();
        ArrayWord = userInput.split(" ");
        for(int i=0; i<ArrayWord.length; i++){
            if (!Keyboard.search(ArrayWord[i])){
                CopmleteArrayWord = Keyboard.autoComplete(ArrayWord[i]).toArray(new String[0]);
                if (CopmleteArrayWord.length != 0){
                    System.out.println("Please Enter Index For "+ArrayWord[i]+" :\n"+ Arrays.toString(CopmleteArrayWord));
                    int x = input.nextInt();
                    ArrayWord[i] = CopmleteArrayWord[x];
                    for (String it : ArrayWord)
                        System.out.print(it+" ");
                    System.out.println();
                }
            }
        }
        Write.appendArrayListToFile(List.of(ArrayWord), "D:\\learn\\Shiraz\\Ds\\DsProject\\src\\inputs.txt");
    }
}
