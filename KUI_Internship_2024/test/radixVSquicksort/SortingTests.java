package radixVSquicksort;

import static org.junit.Assert.*;

import org.junit.Test;
public class SortingTests {

    @Test
    public void testEmpty(){
        int[] arr = new int[] {};
        int[] result = new int[] {};
        assertArrayEquals(result, RadixSort.sort(arr));
    }

    @Test
    public void testFindMax() {
        int[] arr = new int[] { 5,6,1,6,8,19};

        assertEquals(19, RadixSort.findMax(arr));
    }

    @Test
    public void testCombine(){
        int[] arr = new int[] {5, 1, 3};
        int pivot = 999;
        int[] arr1 = new int[] {10, 123};
        int[] result = new int[] {5, 1, 3, 999, 10, 123};

        assertArrayEquals(result, QuickSort.combineArrays(arr, pivot, arr1));
    }

    @Test
    public void testTrim(){
        int[] arr = new int[] {5, 1, 3, 4, 5, 6, 7};
        int[] result = new int[] {5, 1, 3};

        assertArrayEquals(result, QuickSort.trimArray(arr, 3));
    }

    @Test
    public void testFull(){
        int[] arr = new int[] {123,10,2,5,1,3};
        int[] result = new int[] {1,2,3,5,10,123};
        assertArrayEquals(result, RadixSort.sort(arr));
        assertArrayEquals(result, QuickSort.sort(arr));


        arr = new int[] {5,10,2,5,1,3};
        result = new int[] {1,2,3,5,5,10};
        assertArrayEquals(result, RadixSort.sort(arr));
        assertArrayEquals(result, QuickSort.sort(arr));
    }

    @Test
    public void testArrayNotChangeAfterSort(){
        int[] arr = new int[] {123,10,2,5,1,3};
        int[] arrCopy = arr.clone();
        int[] result = new int[] {1,2,3,5,10,123};

        assertArrayEquals(result, RadixSort.sort(arr));

        assertArrayEquals(result, QuickSort.sort(arr));

        assertArrayEquals(arr, arrCopy);

    }
}
