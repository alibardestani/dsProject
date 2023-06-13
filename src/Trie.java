public class Trie {
    static class TrieNode{
        TrieNode[] children;
        boolean isEndOfWord;

        TrieNode() {
            children = new TrieNode[52];  // Update array size to accommodate lowercase and uppercase letters
            isEndOfWord = false;
            for (int i = 0; i < 52; i++) {
                children[i] = null;
            }
        }
    }
    static TrieNode root;
    static void insert(String key) {
        int level;
        int length = key.length();
        int index;

        TrieNode pCrawl = root;

        for (level = 0; level < length; level++) {
            char ch = key.charAt(level);
            if (Character.isLowerCase(ch)) {
                index = ch - 'a';  // Calculate index for lowercase letters
            } else if (Character.isUpperCase(ch)) {
                index = ch - 'A' + 26;  // Calculate index for uppercase letters
            } else {
                // Skip non-alphabetic characters
                continue;
            }

            if (pCrawl.children[index] == null) {
                pCrawl.children[index] = new TrieNode();
            }

            pCrawl = pCrawl.children[index];
        }

        pCrawl.isEndOfWord = true;
    }
    static boolean search(String key) {
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
}
