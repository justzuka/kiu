package hashmapVSquicksort;

import org.junit.Test;
import radixVSquicksort.QuickSort;
import radixVSquicksort.RadixSort;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;

public class HashmapSolutionVSsorting {

    @Test
    public void testWords1000Length5(){
        System.out.println("Words 1000, Length 5 -------------------");
        processStrings(1000, 5);
        System.out.println();
    }

    @Test
    public void testWords1000Length100(){
        System.out.println("Words 1000, Length 100");
        processStrings(1000, 100);
        System.out.println();
    }

    @Test
    public void testWords1000Length1000(){
        System.out.println("Words 1000, Length 1000");
        processStrings(1000, 1000);
        System.out.println();
    }

    @Test
    public void testWords1000Length5000(){
        System.out.println("Words 1000, Length 5000");
        processStrings(1000, 5000);
        System.out.println();
    }


    public static void processStrings(int length, int wordLength) {
        // Generate random strings
        String[] randomStrings = generateRandomStrings(length, wordLength);

        // QuickSort the random strings and measure time taken
        Comparator<String> comparator = String::compareTo;
        long startTime = System.nanoTime();

        String[] sortedStrings = GenericQuickSort.sort(randomStrings, comparator);

        long endTime = System.nanoTime();
        long quickSortTime = endTime - startTime;

        System.out.println("QuickSort took " + quickSortTime + " nanoseconds.");

        // Find duplicates using HashMap solution and measure time taken
        startTime = System.nanoTime();

        HashMap<String, Integer> freq = HashmapSolution.findAllDuplicates(sortedStrings);

        endTime = System.nanoTime();
        long hashmapSolutionTime = endTime - startTime;

        System.out.println("HashMap solution took " + hashmapSolutionTime + " nanoseconds.");

    }
    public static String[] generateRandomStrings(int length, int wordLength) {
        String[] randomStrings = new String[length];
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        Random random = new Random();

        for (int i = 0; i < length; i++) {
            StringBuilder sb = new StringBuilder(wordLength);
            for (int j = 0; j < wordLength; j++) {
                sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
            }
            randomStrings[i] = sb.toString();
        }

        return randomStrings;
    }
}
