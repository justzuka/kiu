package project_assembly;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class KMersTest {
    public KMers kmers;
    @Before
    public void setup(){
        kmers = new KMers();
    }
    @Test
    public void testGetKMers(){
        String testString = "GATCACAGG";
        int k = 4;

        ArrayList<LinkedList<Character>> result = kmers.GetKMers(testString, k);

        ArrayList<LinkedList<Character>> correct = new ArrayList<>();

        correct.add(new LinkedList<>(List.of('G', 'A', 'T', 'C')));
        correct.add(new LinkedList<>(List.of('A', 'T', 'C', 'A')));
        correct.add(new LinkedList<>(List.of('T', 'C', 'A', 'C')));
        correct.add(new LinkedList<>(List.of('C', 'A', 'C', 'A')));
        correct.add(new LinkedList<>(List.of('A', 'C', 'A', 'G')));
        correct.add(new LinkedList<>(List.of('C', 'A', 'G', 'G')));

        assertArrayEquals(correct.toArray(), result.toArray());

    }

    @Test
    public void testGetPrefixSuffixes(){
        String testString = "GATCACAGG";
        int k = 4;

        ArrayList<LinkedList<Character>> resultKMers = kmers.GetKMers(testString, k);

        Pair[] ps = kmers.GetPrefixSuffixes(resultKMers);

        Pair[] correctPS = new Pair[ps.length];

        correctPS[0] = new Pair<>("GAT", "ATC");
        correctPS[1] = new Pair<>("ATC", "TCA");
        correctPS[2] = new Pair<>("TCA", "CAC");
        correctPS[3] = new Pair<>("CAC", "ACA");
        correctPS[4] = new Pair<>("ACA", "CAG");
        correctPS[5] = new Pair<>("CAG", "AGG");


        assertArrayEquals( Arrays.stream(correctPS)
                .map(e -> e.toString())
                .toArray(String[]::new), Arrays.stream(ps)
                .map(e -> e.toString())
                .toArray(String[]::new));

    }

}
