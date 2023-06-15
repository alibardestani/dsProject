import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
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

    public List<String>CorrectionSuggestions(String misspelledWord){
        List<String> suggestions = new ArrayList<>();
        String longestPrefix = getLongestPrefix(misspelledWord);
        List<String> prefixSuggestions = autoComplete(longestPrefix);
        for(String suggestion : prefixSuggestions){
            int distance = calculateDistance(misspelledWord, suggestion);
            suggestions.add(suggestion + " (distance: "+distance+" )");
        }
        suggestions.sort(Comparator.comparingInt(Trie::getDistanceFormSuggestion));

        if(suggestions.size() > 5){
            suggestions = suggestions.subList(0,5);
        }
        return suggestions;
    }
    private static int getDistanceFormSuggestion(String suggestion){
        int index = suggestion.lastIndexOf(" (distance: ");
        String distanceString = suggestion.substring(index + 12, suggestion.length() - 1).trim();
        return Integer.parseInt(distanceString);
    }
    private String getLongestPrefix(String word){
        int length = word.length();
        StringBuilder prefix = new StringBuilder();

        TrieNode pCrawl = root;

        for (int level = 0; level < length; level++){
            char ch = word.charAt(level);
            int index = getIndexFromChar(ch);

            if (pCrawl.children[index] == null){
                break;
            }

            pCrawl = pCrawl.children[index];
            prefix.append(ch);
            if (pCrawl.isEndOfWord){
                break;
            }
        }
        return prefix.toString();
    }
    private int getIndexFromChar(char ch){
        if (Character.isLowerCase(ch)){
            return ch - 'a';
        } else if(Character.isUpperCase(ch)){
            return ch - 'A' + 26;
        }else {
            return -1;
        }
    }
    private int calculateDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) != word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }

}
