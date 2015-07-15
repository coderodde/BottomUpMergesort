package net.coderodde.util.sorting;

import java.util.Comparator;
import java.util.Random;

/**
 * This class contains some static utility methods for working with arrays.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6
 */
public class Utils {


    /**
     * Tests whether the range {@code array[fromIndex], array[fromIndex + 1],
     * ..., array[toIndex - 2], array[toIndex - 1]} is sorted into ascending 
     * order as specified by {@code cmp}.
     * 
     * @param <T>       the array component type.
     * @param array     the array holding the target range.
     * @param fromIndex the starting (inclusive) index.
     * @param toIndex   the ending (exclusive) index.
     * @param cmp       the element comparator.
     * @return          {@code true} only if the requested range is sorted.
     */
    public static <T> boolean isSorted(T[] array,
                                       int fromIndex,
                                       int toIndex,
                                       Comparator<? super T> cmp) {
        for (int i = fromIndex; i < toIndex - 1; ++i) {
            if (cmp.compare(array[i], array[i + 1]) > 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * Tests whether the array {@code array} is sorted into ascending order as
     * specified by {@code cmp}.
     * 
     * @param <T>   the array component type.
     * @param array the array holding the target range.
     * @param cmp   the element comparator.
     * @return      {@code true} only if the entire array is sorted.
     */
    public static <T> boolean isSorted(T[] array, Comparator<? super T> cmp) {
        return isSorted(array, 0, array.length, cmp);
    }

    /**
     * Returns {@code true} if the two input arrays are of the same length, and
     * both have identical array components.
     * 
     * @param <T>  the array component type.
     * @param arr1 the first array.
     * @param arr2 the second array.
     * @return {@code true} if the two arrays have identical contents.
     */
    public static <T> boolean arraysIdentical(T[] arr1, T[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; ++i) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * This method creates a random array of integers.
     * 
     * @param size the length of the result array.
     * @param random the instance of {@link java.util.Random}.
     * @return a random array.
     */
    public static Integer[] createRandomIntegerArray(int size, Random random) {
        Integer[] ret = new Integer[size];

        for (int i = 0; i < size; ++i) {
            ret[i] = random.nextInt(size);
        }

        return ret;
    }
}
