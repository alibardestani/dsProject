import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main extends Trie {
    public static void main(String[] args) {
        List<String> words = Read.readWordsFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        List<String> wordsReverse = Read.readReverceWordFromFile("D:\\learn\\Shiraz\\Ds\\DsProject\\src\\words.txt");
        ArrayList<String> History = new ArrayList<>();
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
                        for (int it = 0; it < trie.autocomplete(str[i]).size(); it++) {
                            System.out.println(it + "\t" + trie.autocomplete(str[i]).get(it));
                        }
                        int SeChoice = input.nextInt();
                        str[i] = trie.autocomplete(str[i]).get(SeChoice);
                        System.out.println();
                        History.add(str[i]);
                    }
                    case 2 -> {
                        System.out.println(21);
                        ArrayList<ArrayList<Object>> suggestions = ConnectTwoArray(trie.Suggestions(str[i]), trieReverse.Suggestions(String.valueOf(new StringBuilder(str[i]).reverse())));

                        // Print the suggestions with their corresponding indices
                        for (int index = 0; index < suggestions.size(); index++) {
                            ArrayList<Object> suggestion = suggestions.get(index);
                            System.out.println(index + "\t" + suggestion.get(0) + "\t" + suggestion.get(1));
                        }

                        // Get user's choice
                        int userChoice = input.nextInt();

                        // Check if the user's choice is within the valid range
                        if (userChoice >= 0 && userChoice < suggestions.size()) {
                            String chosenString = (String) suggestions.get(userChoice).get(0);
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
        Write.Write(History,"history.txt");


    }

    public static ArrayList<ArrayList<Object>> ConnectTwoArray(ArrayList<ArrayList<Object>> pre, ArrayList<ArrayList<Object>> suf){
        ArrayList<ArrayList<Object>> combined = new ArrayList<>(pre);
        combined.addAll(suf);

//        for(Object i : pre){
//            System.out.println(i);
//        }
        reverseStrings(suf);
//        System.out.println("*");
//        for (Object y : suf){
//            System.out.println(y);
//        }
//        System.out.println("******\t\t******");
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
