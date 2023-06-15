import java.util.ArrayList;
import java.util.List;

public class Trie {
    private static class TrieNode {
        TrieNode[] children;
        boolean isEndOfWord;

        TrieNode() {
            children = new TrieNode[52];
            isEndOfWord = false;
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

        return wordsComplete;
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

    public boolean spellCheck(String word) {
        return search(word);
    }

}
