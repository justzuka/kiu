package radixVSquicksort;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class QuickSort {
    public static int[] sort(int[] arr) {
        if (arr.length <= 1) {
            return arr.clone();
        }

        Random random = new Random();
        int pivotIndex = random.nextInt(arr.length);
        int pivot = arr[pivotIndex];


        int[] left = new int[arr.length];
        int[] right = new int[arr.length];
        int leftSize = 0, rightSize = 0;

        // partition the array around the pivot
        for (int i = 0; i < arr.length; i++) {
            if (i == pivotIndex) continue;

            if (arr[i] < pivot) {
                left[leftSize++] = arr[i];
            } else {
                right[rightSize++] = arr[i];
            }
        }

        // recursion
        left = sort(trimArray(left, leftSize));
        right = sort(trimArray(right, rightSize));

        // combine
        return combineArrays(left, pivot, right);
    }

    public static int[] trimArray(int[] arr, int size) {
        int[] trimmed = new int[size];
        System.arraycopy(arr, 0, trimmed, 0, size);
        return trimmed;
    }

    public static int[] combineArrays(int[] left, int pivot, int[] right) {
        int[] result = new int[left.length + 1 + right.length];
        System.arraycopy(left, 0, result, 0, left.length);
        result[left.length] = pivot;
        System.arraycopy(right, 0, result, left.length + 1, right.length);
        return result;
    }
}
