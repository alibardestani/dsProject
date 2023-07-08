import java.util.*;

public class Main extends Trie {
    public static void main(String[] args) {
        List<String> words = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> wordsReverse = Read.readReverceWordFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> History = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\history.txt");
        Trie trie = new Trie();
        Trie trieReverse = new Trie();

        Scanner input = new Scanner(System.in);

        for (String word : words) {
            trie.insert(word);
        }
        for (String word : wordsReverse){
            trieReverse.insert(word);
        }
        boolean eof = false;
        while (!eof){
            boolean flag = false;
            System.out.println("Please Enter String");
            String[] str = input.nextLine().split(" ");
            while (!flag){
                for(int i = 0; i<str.length; i++){
                    for(String word : str){
                        System.out.print(word+" ");
                    }
                    System.out.println("\n===> "+str[i]+"\n1-AutoComplete\n2-Spell Check\n3-Suggestions");
                    int choice = input.nextInt();
                    switch (choice) {
                        case 1 -> {
                            List<String> NewSug = sortList(trie.autocomplete(str[i]),History);
                            System.out.println(-1 + "\t" + str[i]);
                            for (int it = 0; it < NewSug.size(); it++) {
                                System.out.println(it + "\t" + NewSug.get(it));
                            }
                            int SeChoice = input.nextInt();
                            if(SeChoice != -1){
                                str[i] = NewSug.get(SeChoice);
                            }
                            System.out.println();
                            History.add(str[i]);
                            System.out.println("History\t"+History+"\t"+str[i]);
                        }
                        case 2->{
                            if(trie.search(str[i])){
                                System.out.println("It is Ok!");
                                History.add(str[i]);
                            }
                            else {
                                System.out.println("It isn't find in our dictionary");
                                i--;
                            }
                        }
                        case 3 -> {
                            ArrayList<HashMap<String, Integer>> suggestions = ConnectTwoArray(trie.Suggestions(str[i]), reverseStrings(trieReverse.Suggestions(new StringBuilder(str[i]).reverse().toString())));
                            ArrayList<HashMap<String, Integer>> NewSug = sortList(suggestions,History);
                            NewSug = removeDuplicateStrings(NewSug);
                            System.out.println(-1 + "\t" + str[i]);
                            for (int index = 0; index < NewSug.size(); index++) {
                                HashMap<String, Integer> suggestion = NewSug.get(index);
                                for (Map.Entry<String, Integer> entry : suggestion.entrySet()) {
                                    String key = entry.getKey();
                                    Integer value = entry.getValue();
                                    System.out.println(index + "\t" + key + "\t=> " + value);
                                }
                            }

                            int userChoice = input.nextInt();

                            if(userChoice != -1){
                                if (userChoice >= 0 && userChoice < NewSug.size()) {
                                    HashMap<String, Integer> suggestion = NewSug.get(userChoice);
                                    String chosenString = suggestion.keySet().iterator().next();
                                    System.out.println("User chose: " + chosenString);
                                    str[i] = chosenString;
                                    History.add(chosenString);
                                }else {
                                    System.out.println("Invalid choice.");
                                }
                            }else {
                                System.out.println("User chose: " + str[i]);
                                History.add(str[i]);
                            }

                        }
                        default -> flag = true;
                    }
                }
                flag = true;
            }
            for(String word : str){
                System.out.print(word+" ");
            }
            Write.WriteToFile(History,"D:\\learn\\Shiraz\\Ds\\DsProject\\src\\history.txt");
            System.out.println("Do You Want To Continue?\n1-Yes\n2-No");
            int LastChoice = input.nextInt();
            if(LastChoice != 1){
                eof = true;
            }
            input.nextInt();
        }
        System.out.println("Finish Program");
    }

    public static ArrayList<HashMap<String, Integer>> ConnectTwoArray(ArrayList<HashMap<String, Integer>> pre, ArrayList<HashMap<String, Integer>> suf) {
        ArrayList<HashMap<String, Integer>> combined = new ArrayList<>(pre);
        combined.addAll(suf);

        combined.sort(Comparator.comparingInt(hashMap -> hashMap.values().iterator().next()));

        ArrayList<HashMap<String, Integer>> result = new ArrayList<>();

        for (int i = 0; i < Math.min(5, combined.size()); i++) {
            HashMap<String, Integer> hashMap = combined.get(i);
            result.add(hashMap);
        }

        return result;
    }

    private static ArrayList<HashMap<String, Integer>> reverseStrings(ArrayList<HashMap<String, Integer>> list) {
        for (HashMap<String, Integer> hashMap : list) {
            for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();

                String reversedKey = new StringBuilder(key).reverse().toString();
                hashMap.put(reversedKey, value);
                hashMap.remove(key);
            }
        }
        return list;
    }

    public static List<String> sortList(List<String> list1, List<String> list2) {
        Map<String, Integer> wordCount = new HashMap<>();
        for (String word : list2) {
            if (list1.contains(word)) {
                wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            }
        }

        list1.sort((a, b) -> wordCount.getOrDefault(b, 0) - wordCount.getOrDefault(a, 0));

        return list1;
    }
    public static ArrayList<HashMap<String, Integer>> sortList(ArrayList<HashMap<String, Integer>> list1, List<String> list2) {
        HashMap<String, Integer> wordCount = new HashMap<>();

        for (HashMap<String, Integer> word : list1) {
            String wordString = word.keySet().iterator().next();
            if (list2.contains(wordString)) {
                wordCount.put(wordString, wordCount.getOrDefault(wordString, 0) + 1);
            }
        }

        list1.sort((a, b) -> wordCount.getOrDefault(b.keySet().iterator().next(), 0) -
                wordCount.getOrDefault(a.keySet().iterator().next(), 0));

        return list1;
    }

    private static ArrayList<HashMap<String, Integer>> removeDuplicateStrings(ArrayList<HashMap<String, Integer>> list) {
        HashSet<String> uniqueStrings = new HashSet<>();
        ArrayList<HashMap<String, Integer>> result = new ArrayList<>();

        for (HashMap<String, Integer> map : list) {
            for (String key : map.keySet()) {
                if (!uniqueStrings.contains(key)) {
                    uniqueStrings.add(key);
                    result.add(map);
                    break;
                }
            }
        }

        return result;
    }

}
