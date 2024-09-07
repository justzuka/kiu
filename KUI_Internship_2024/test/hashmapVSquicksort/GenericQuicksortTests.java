package hashmapVSquicksort;

import org.junit.Test;

import java.util.Comparator;
import static org.junit.Assert.*;
public class GenericQuicksortTests {

    @Test
    public void testEmpty(){
        Integer[] arr = {};
        Comparator<Integer> comparator = Integer::compareTo;

        Integer[] sortedArr = GenericQuickSort.sort(arr, comparator);

        assertArrayEquals(arr, sortedArr);
    }
    @Test
    public void testSort() {
        Integer[] arr = {3, 6, 8, 10, 1, 2, 1};
        Comparator<Integer> comparator = Integer::compareTo;

        Integer[] sortedArr = GenericQuickSort.sort(arr, comparator);

        Integer[] result = {1, 1, 2, 3, 6, 8, 10};

        assertArrayEquals(result, sortedArr);

    }
}
