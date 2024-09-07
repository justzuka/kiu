package hashmapVSquicksort;

import java.lang.reflect.Array;
import java.util.Comparator;
import java.util.Random;

public class GenericQuickSort {
    public static <T> T[] sort(T[] arr, Comparator<T> comparator) {
        if (arr.length <= 1) {
            return arr.clone();
        }

        Random random = new Random();
        int pivotIndex = random.nextInt(arr.length);
        T pivot = arr[pivotIndex];

        T[] left = createArray(arr, arr.length);
        T[] right = createArray(arr, arr.length);
        int leftSize = 0, rightSize = 0;

        // partition the array around the pivot
        for (int i = 0; i < arr.length; i++) {
            if (i == pivotIndex) continue;

            if (comparator.compare(arr[i], pivot) < 0) {
                left[leftSize++] = arr[i];
            } else {
                right[rightSize++] = arr[i];
            }
        }

        // recursion
        left = sort(trimArray(left, leftSize), comparator);
        right = sort(trimArray(right, rightSize), comparator);

        // combine
        return combineArrays(left, pivot, right);
    }

    @SuppressWarnings("unchecked")
    private static <T> T[] createArray(T[] arr, int length) {
        return (T[]) Array.newInstance(arr.getClass().getComponentType(), length);
    }

    private static <T> T[] trimArray(T[] arr, int size) {
        T[] trimmed = createArray(arr, size);
        System.arraycopy(arr, 0, trimmed, 0, size);
        return trimmed;
    }

    private static <T> T[] combineArrays(T[] left, T pivot, T[] right) {
        T[] result = createArray(left, left.length + 1 + right.length);
        System.arraycopy(left, 0, result, 0, left.length);
        result[left.length] = pivot;
        System.arraycopy(right, 0, result, left.length + 1, right.length);
        return result;
    }

}
