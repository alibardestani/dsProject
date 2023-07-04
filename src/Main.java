import java.util.ArrayList;
import java.util.Comparator;
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
//        System.out.println("Trie contains 'apple': " + trie.search("apple"));
//        System.out.println("Trie contains 'apple': " + trie.autocomplete("ap"));
//        System.out.println("Trie contains 'apple': " + trie.distance("llll","ap"));
        for(Object word : ConnectTwoArray(trie.Suggestions("ple"),trieReverse.Suggestions(String.valueOf(new StringBuilder("ple").reverse())))){
            System.out.println(word);
        }


    }

    public static ArrayList<ArrayList<Object>> ConnectTwoArray(ArrayList<ArrayList<Object>> pre, ArrayList<ArrayList<Object>> suf){
        ArrayList<ArrayList<Object>> combined = new ArrayList<>(pre);
        combined.addAll(suf);

        for(Object i : pre){
            System.out.println(i);
        }
        reverseStrings(suf);
        System.out.println("*");
        for (Object y : suf){
            System.out.println(y);
        }
        System.out.println("******\t\t******");
        // Sort the combined ArrayList based on the Integer values in ascending order
        combined.sort(Comparator.comparingInt(row -> (int) row.get(1)));

        // Create a new ArrayList to hold the top five Strings with the minimum Integer values
        ArrayList<ArrayList<Object>> result = new ArrayList<>();

        // Retrieve the top five elements from the combined ArrayList
        for (int i = 0; i < Math.min(5, combined.size()); i++) {
            result.add(combined.get(i));
        }

        return result;
    }
    public static void reverseStrings(ArrayList<ArrayList<Object>> list) {
        for (ArrayList<Object> row : list) {
            if (row.size() > 0 && row.get(0) instanceof String) {
                String originalString = (String) row.get(0);
                String reversedString = new StringBuilder(originalString).reverse().toString();
                row.set(0, reversedString);
            }
        }
    }


}
