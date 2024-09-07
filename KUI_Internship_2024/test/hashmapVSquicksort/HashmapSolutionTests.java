package hashmapVSquicksort;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;
public class HashmapSolutionTests {

    @Test
    public void test() {
        String[] words = new String[] {"aba", "cba", "aab", "aba", "cba"};
        HashMap<String, Integer> freq = HashmapSolution.findAllDuplicates(words);

        HashMap<String, Integer> expected = new HashMap<>();

        expected.put("aba", 2);
        expected.put("cba", 2);
        expected.put("aab", 1);

        assertEquals(expected, freq);
    }
}
