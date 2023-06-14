import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main extends Trie {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        List<String> words = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");

        root = new TrieNode();
        for (String key : words) {
            insert(key);
        }
        System.out.println(autoComplete("h"));
//        String[] ArrayWord;
//        String[] CopmleteArrayWord;
//        String userInput = input.nextLine();
//        ArrayWord = userInput.split(" ");
//        for(int i=0; i<ArrayWord.length; i++){
//            if (!search(ArrayWord[i])){
//                CopmleteArrayWord = autoComplete(ArrayWord[i]).toArray(new String[0]);
//                System.out.println("Please Enter Index For "+ArrayWord[i]+" :\n"+ Arrays.toString(CopmleteArrayWord));
//                int x = input.nextInt();
//                ArrayWord[i] = CopmleteArrayWord[x];
//                for (String it : ArrayWord)
//                    System.out.print(it+" ");
//            }
//        }




    }
}
