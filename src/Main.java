import java.util.*;

public class Main extends Trie {
    public static void main(String[] args) {
        List<String> words = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> wordsReverse = Read.readReverceWordFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> History = Read.readReverceWordFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\history.txt");
        Trie trie = new Trie();
        Trie trieReverse = new Trie();

        Scanner input = new Scanner(System.in);

        for (String word : words) {
            trie.insert(word);
        }
        for (String word : wordsReverse){
            trieReverse.insert(word);
        }

        boolean flag = false;
        System.out.println("Please Enter String");
        String[] str = input.nextLine().split(" ");
        while (!flag){
            for(int i = 0; i<str.length; i++){
                for(String word : str){
                    System.out.print(word+" ");
                }
                System.out.println("\n===> "+str[i]+"\n1-AutoComplete\n2-Suggestions");
                int choice = input.nextInt();
                switch (choice) {
                    case 1 -> {
                        List<String> NewSug = sortList(trie.autocomplete(str[i]),History);
                        for (int it = 0; it < NewSug.size(); it++) {
                            System.out.println(it + "\t" + NewSug.get(it));
                        }
                        int SeChoice = input.nextInt();
                        str[i] = trie.autocomplete(str[i]).get(SeChoice);
                        System.out.println();
                        History.add(str[i]);
                    }
                    case 2 -> {
                        ArrayList<HashMap<String, Integer>> suggestions = ConnectTwoArray(trie.Suggestions(str[i]), trieReverse.Suggestions(new StringBuilder(str[i]).reverse().toString()));

                        for (int index = 0; index < suggestions.size(); index++) {
                            HashMap<String, Integer> suggestion = suggestions.get(index);
                            System.out.println(index + "\t" + suggestion.get("word") + "\t" + suggestion.get("distance"));
                        }

                        int userChoice = input.nextInt();

                        if (userChoice >= 0 && userChoice < suggestions.size()) {
                            String chosenString = String.valueOf(suggestions.get(userChoice).get("word"));
                            System.out.println("User chose: " + chosenString);
                            str[i] = chosenString;
                            History.add(chosenString);
                        } else {
                            System.out.println("Invalid choice.");
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
//        System.out.println("Trie contains 'apple': " + trie.search("tes"));
//        System.out.println(trie.autocomplete("ti"));
//        System.out.println("Trie contains 'apple': " + trie.distance("llll","ap"));
        //for(Object word : ConnectTwoArray(trie.Suggestions("ple"),trieReverse.Suggestions(String.valueOf(new StringBuilder("ple").reverse())))){
            //System.out.println(word);
        //}
        Write.WriteToFile(History,"history.txt");
        Write.WriteToFile(List.of(str),"inputs.txt");


    }

    public static ArrayList<HashMap<String, Integer>> ConnectTwoArray(ArrayList<HashMap<String, Integer>> pre, ArrayList<HashMap<String, Integer>> suf) {
        ArrayList<HashMap<String, Integer>> combined = new ArrayList<>(pre);
        combined.addAll(suf);

        reverseStrings(suf);
        // Sort the combined ArrayList based on the Integer values in ascending order
        combined.sort(Comparator.comparingInt(row -> row.get("distance")));

        // Create a new ArrayList to hold the top five Strings with the minimum Integer values
        ArrayList<HashMap<String, Integer>> result = new ArrayList<>();

        // Retrieve the top five elements from the combined ArrayList
        for (int i = 0; i < Math.min(5, combined.size()); i++) {
            result.add(combined.get(i));
        }

        return result;
    }
    public static void reverseStrings(ArrayList<HashMap<String, Integer>> list) {
        for (HashMap<String, Integer> row : list) {
            if (row.containsKey("word")) {
                String originalString = String.valueOf(row.get("word"));
                String reversedString = new StringBuilder(originalString).reverse().toString();
                row.put("word", Integer.valueOf(reversedString));
            }
        }
    }
    public static List<String> sortList(List<String> list1, List<String> list2) {
        // Sort list1
        Collections.sort(list1);
        System.out.println("In sortList\n"+list1+"\n"+list2);

        // Create a map to store the count of each word in list2
        Map<String, Integer> countMap = new HashMap<>();
        for (String word : list2) {
            countMap.put(word, countMap.getOrDefault(word, 0) + 1);
        }

        // Sort list1 based on the count of each word in list2
        list1.sort((word1, word2) -> countMap.getOrDefault(word2, 0) - countMap.getOrDefault(word1, 0));

        return list1;
    }
}
