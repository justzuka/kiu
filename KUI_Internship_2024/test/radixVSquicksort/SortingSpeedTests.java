package radixVSquicksort;

import org.junit.Test;

import java.util.Random;

public class SortingSpeedTests {

    @Test
    public void test1000Integers() {
        System.out.println("Test 1 000");
        runBothSortsOnNNumbers(1_000);
    }

    @Test
    public void test1000000Integers() {
        System.out.println("Test 1 000 000");
        runBothSortsOnNNumbers(1_000_000);
    }

    @Test
    public void test10000000Integers() {
        System.out.println("Test 10 000 000");
        runBothSortsOnNNumbers(1_0_000_000);
    }

    private void runBothSortsOnNNumbers(int n){
        int[] testArray = new int[n];
        Random random = new Random();

        int max = (int) Math.pow(2, 16); // 65536

        for (int i = 0; i < n; i++) {
            int randomNumber = random.nextInt(max) + 1;
            testArray[i] = randomNumber;
        }

        long startTime = System.nanoTime();

        QuickSort.sort(testArray);

        long endTime = System.nanoTime();
        long quickSortTime = endTime - startTime;

        System.out.println("QuickSort took " + quickSortTime + " nanoseconds.");

        startTime = System.nanoTime();
        RadixSort.sort(testArray);

        endTime = System.nanoTime();
        long radixSortTime = endTime - startTime;

        System.out.println("RadixSort took " + radixSortTime + " nanoseconds.");
    }
}
