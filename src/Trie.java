import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class TrieNode {
    private static final int ALPHABET_SIZE = 26;
    TrieNode[] children;
    boolean isEndOfWord;

    public TrieNode() {
        children = new TrieNode[ALPHABET_SIZE];
        isEndOfWord = false;
    }
}

class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = Character.toLowerCase(word.charAt(i));
            if (!Character.isLetter(ch)) {
                continue; // Ignore non-alphabetic characters
            }
            int index = ch - 'a';
            if (current.children[index] == null) {
                current.children[index] = new TrieNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            int index = Character.toLowerCase(ch) - 'a';
            if (current.children[index] == null) {
                return false;
            }
            current = current.children[index];
        }
        return current != null && current.isEndOfWord;
    }

    public List<String> autocomplete(String prefix) {
        TrieNode current = root;
        for (int i = 0; i < prefix.length(); i++) {
            char ch = Character.toLowerCase(prefix.charAt(i));
            int index = ch - 'a';
            if (current.children[index] == null) {
                return new ArrayList<>(); // No words with the given prefix
            }
            current = current.children[index];
        }

        List<String> suggestions = new ArrayList<>();
        autocompleteHelper(current, prefix, suggestions);

        if(suggestions.size() > 5){
            suggestions = suggestions.subList(0,5);
        }

        return suggestions;
    }

    private void autocompleteHelper(TrieNode node, String prefix, List<String> suggestions) {
        if (node.isEndOfWord) {
            suggestions.add(prefix);
        }

        for (char ch = 'a'; ch <= 'z'; ch++) {
            int index = ch - 'a';
            if (node.children[index] != null) {
                autocompleteHelper(node.children[index], prefix + ch, suggestions);
            }
        }
    }

    public int distance (String word1, String word2){
        int dis = 0;
        for (int i=0; i<word1.length(); i++){
            if(i >= word2.length()){
                break;
            }
            if(word1.charAt(i) != word2.charAt(i)){
                dis++;
            }
        }
        if(word1.length()<word2.length() || word1.length()>word2.length()){
            dis = dis + word1.length() - word2.length();
            dis = Math.abs(dis);
        }
        return dis;
    }

    public ArrayList<ArrayList<Object>> Suggestions(String fix){
        ArrayList<ArrayList<Object>> sug = new ArrayList<>();
        List<String> prefix = new ArrayList<>();
        prefix = autocomplete(fix);
        for(String word : prefix){
            ArrayList<Object> row = new ArrayList<>();
            row.add(word);
            row.add(distance(fix,word));
            sug.add(row);
        }

        return sug;
    }
}