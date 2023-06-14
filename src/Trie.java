import java.util.ArrayList;
import java.util.List;

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
    public static List<String> autoComplete(String word){
        List<String> wordsComplete = new ArrayList<>();

        int level;
        int length = word.length();
        int index;
        TrieNode pCrawl = root;
        StringBuilder aa = new StringBuilder();
        for (level = 0; level < length; level++) {
            char ch = word.charAt(level);
            if (Character.isLowerCase(ch)) {
                index = ch - 'a';
            } else if (Character.isUpperCase(ch)) {
                index = ch - 'A' + 26;
            } else {
                continue;
            }
            aa.append(ch);
            pCrawl = pCrawl.children[index];
        }
        TrieNode dpCrawl = root;
        StringBuilder aa_copy;
        for(int i=0; i<52; i++){
            if(pCrawl.children[i] != null){
                aa_copy = new StringBuilder(aa);
                aa_copy.append(asciiToChar(i));
                if(pCrawl.isEndOfWord){
                    wordsComplete.add(String.valueOf(aa_copy));
                    break;
                }else {
                    boolean end = false;
                    int count = 0;
                    dpCrawl = pCrawl.children[i];
                    while (!end || count<52){
                        count++;
                        for(int it=0; it<52; it++){
                            if(dpCrawl.children[it] != null){
                                aa_copy.append(asciiToChar(it));
                                dpCrawl = dpCrawl.children[it];
                                if(dpCrawl.isEndOfWord){
                                    wordsComplete.add(String.valueOf(aa_copy));
                                    end = true;
                                    break;
                                }
                                it = 0;
                            }
                        }
                    }
                }
            }
        }
        return wordsComplete;
    }
    public static char asciiToChar(int i){
        if (i < 26){
            i += 97;
        }else {
            i +=39;
        }
        return (char)i;
    }
}
