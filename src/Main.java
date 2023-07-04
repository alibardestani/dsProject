import java.util.Arrays;
import java.util.List;

public class Main extends Trie {
    public static void main(String[] args) {
        List<String> words = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> wordsReverse = Read.readReverceWordFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");

        Trie trie = new Trie();
        Trie trieReverse = new Trie();

        for (String word : words) {
            trie.insert(word);
        }
        for (String wordd : wordsReverse){
            trieReverse.insert(wordd);
        }
        System.out.println("Trie contains 'apple': " + trieReverse.search("apple"));
        System.out.println("Trie contains 'elppla': " + trieReverse.search("elppa"));

    }
}
