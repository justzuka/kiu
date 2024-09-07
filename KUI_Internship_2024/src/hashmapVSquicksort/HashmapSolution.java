package hashmapVSquicksort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class HashmapSolution {
    public static HashMap<String, Integer> findAllDuplicates(String[] words) {
        HashMap<String, Integer> freqs = new HashMap<>();

        for (int i = 0; i < words.length; i++) {
            int wordFreq = freqs.getOrDefault(words[i], 0);
            freqs.put(words[i], wordFreq + 1);
        }

        return freqs;
    }
}
