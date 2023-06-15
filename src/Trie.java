import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class Trie {
    private static class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;
        int frequency;

        TrieNode() {
            children = new TrieNode[52];
            isEndOfWord = false;
            frequency = 0;
            for (int i = 0; i < 52; i++) {
                children[i] = null;
            }
        }
    }

    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String key) {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            char ch = key.charAt(level);
            if (Character.isLowerCase(ch)) {
                index = ch - 'a';
            } else if (Character.isUpperCase(ch)) {
                index = ch - 'A' + 26;
            } else {
                continue;
            }

            if (pCrawl.children[index] == null) {
                pCrawl.children[index] = new TrieNode();
            }

            pCrawl = pCrawl.children[index];
        }

        pCrawl.isEndOfWord = true;
        pCrawl.frequency++;
    }

    public boolean search(String key) {
        int level;
        int length = key.length();
        int index;
        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            char ch = key.charAt(level);
            if (Character.isLowerCase(ch)) {
                index = ch - 'a';
            } else if (Character.isUpperCase(ch)) {
                index = ch - 'A' + 26;
            } else {
                continue;
            }

            if (pCrawl.children[index] == null) {
                return false;
            }

            pCrawl = pCrawl.children[index];
        }

        return pCrawl.isEndOfWord;
    }

    public List<String> autoComplete(String word) {
        List<String> wordsComplete = new ArrayList<>();

        int length = word.length();
        int index;
        TrieNode pCrawl = root;

        for (int level = 0; level < length; level++) {
            char ch = word.charAt(level);
            if (Character.isLowerCase(ch)) {
                index = ch - 'a';
            } else if (Character.isUpperCase(ch)) {
                index = ch - 'A' + 26;
            } else {
                continue;
            }

            if (pCrawl.children[index] == null) {
                return wordsComplete;
            }

            pCrawl = pCrawl.children[index];
        }

        autoCompleteDFS(pCrawl, new StringBuilder(word), wordsComplete);

        PriorityQueue<String> sortedWords = new PriorityQueue<>(Comparator.comparingInt(this::getFrequency).reversed());
        sortedWords.addAll(wordsComplete);

        List<String> topSuggestions = new ArrayList<>();
        int count = 0;
        while (count < 5 && !sortedWords.isEmpty()) {
            topSuggestions.add(sortedWords.poll());
            count++;
        }

        return topSuggestions;
    }

    private void autoCompleteDFS(TrieNode node, StringBuilder prefix, List<String> wordsComplete) {
        if (node.isEndOfWord) {
            wordsComplete.add(prefix.toString());
        }

        for (int i = 0; i < 52; i++) {
            if (node.children[i] != null) {
                char ch = asciiToChar(i);
                prefix.append(ch);
                autoCompleteDFS(node.children[i], prefix, wordsComplete);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }
    }

    private char asciiToChar(int i) {
        if (i < 26) {
            i += 97;
        } else {
            i += 39;
        }
        return (char) i;
    }

    private int getFrequency(String word) {
        TrieNode pCrawl = root;
        int length = word.length();
        int index;

        for (int level = 0; level < length; level++) {
            char ch = word.charAt(level);
            if (Character.isLowerCase(ch)) {
                index = ch - 'a';
            } else if (Character.isUpperCase(ch)) {
                index = ch - 'A' + 26;
            } else {
                continue;
            }

            if (pCrawl.children[index] == null) {
                return 0;
            }

            pCrawl = pCrawl.children[index];
        }

        return pCrawl.frequency;
    }
}
