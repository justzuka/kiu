package radixVSquicksort;

import java.util.ArrayList;
import java.util.List;

public class RadixSort {
    public static int[] sort(int[] arr) {
        int n = arr.length;
        if (n == 0) return arr.clone();
        arr = arr.clone();
        int max = findMax(arr);
        int maxDigits = (int) Math.log10(max) + 1;

        List<Integer>[] buckets = new ArrayList[10];
        for (int i = 0; i < 10; i++) {
            buckets[i] = new ArrayList<>();
        }

        int pass = 1;

        for (int digit = 0; digit < maxDigits; digit++) {
            // distribute the elements
            for (int i = 0; i < n; i++) {
                int bucketIndex = (arr[i] / pass) % 10;
                buckets[bucketIndex].add(arr[i]);
            }

            // collect elements
            int index = 0;
            for (int i = 0; i < 10; i++) {
                for (int num : buckets[i]) {
                    arr[index++] = num;
                }

                buckets[i].clear();
            }

            pass *= 10;
        }

        return arr;
    }

    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }
}
