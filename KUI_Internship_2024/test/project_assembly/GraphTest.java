package project_assembly;

import lesson20240903.lists.Graph;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GraphTest {
    private Graph<String> graph;
    public KMers kmers;
    @Before
    public void setup(){
        kmers = new KMers();
        graph = new Graph();
    }
    @Test
    public void test(){
        String testString = "GATCACAGGTCTATCACCCTATTAACCACTCACGGGAGCTCTCCATGCATTTGGTATTTTCGTCTGGGGGGTGTGCACGCGATAGCATTGCGAGACGCTGGAGCCGGAGCACCCTATGTCGCAGTATCTGTCTTTGATTCCTGCCTCATTCTATTATTTATCGCACCTACGTTCAATATTACAGGCGAACATACCTACTAAAGTGTGTTAATTAATTAATGCTTGTAGGACATAATAATAACAATTGAAT";
        int k = 4;

        Pair[] ps = kmers.GetPrefixSuffixes(kmers.GetKMers(testString, k));


        for (int i = 0; i < ps.length; i++) {
            graph.addDirectedEdge(ps[i].getFirst().toString(), ps[i].getSecond().toString());
        }


        String result = EulerianPathApproach.reconstructStringFromPath(EulerianPathApproach.findEulerianPath(graph));

        assertEquals(testString, result);
    }

}
