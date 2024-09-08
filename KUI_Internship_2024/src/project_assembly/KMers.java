package project_assembly;

import java.util.ArrayList;
import java.util.LinkedList;

public class KMers {
    public ArrayList<LinkedList<Character>> GetKMers(String input, int k) {
        if (input.length() < k) {
            return null;
        }

        ArrayList<LinkedList<Character>> result = new ArrayList<>();

        LinkedList<Character> temp = new LinkedList<>();

        for (int i = 0; i < k; i++) {
            temp.add(input.charAt(i));
        }

        result.add((LinkedList<Character>) temp.clone());

        for (int i = k; i < input.length(); i++) {
            temp.removeFirst();
            temp.add(input.charAt(i));
            result.add((LinkedList<Character>) temp.clone());
        }

        return result;
    }

    public Pair[] GetPrefixSuffixes(ArrayList<LinkedList<Character>> kmers){
        Pair[] result = new Pair[kmers.size()];

        for (int i = 0; i < kmers.size(); i++) {
            LinkedList<Character> list = kmers.get(i);
            result[i] = GetPS(list);
        }
        return result;
    }

    private Pair GetPS(LinkedList<Character> list){
        StringBuilder prefix = new StringBuilder();
        StringBuilder suffix = new StringBuilder();

        for (int j = 0; j < list.size(); j++) {
            if (j == 0){
                prefix.append(list.get(j));
            }
            else if (j == list.size() - 1){
                suffix.append(list.get(j));
            }
            else {
                prefix.append(list.get(j));
                suffix.append(list.get(j));
            }
        }

        return new Pair(prefix, suffix);
    }
}
