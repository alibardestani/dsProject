import java.util.Arrays;
import java.util.List;

public class Main extends Trie {
    public static void main(String[] args) {
        List<String> words = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");

        root = new TrieNode();
        for (String key : words) {
            insert(key);
        }
        System.out.println(autoComplete("Af"));

    }
}
