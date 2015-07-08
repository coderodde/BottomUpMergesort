package net.coderodde.util.sorting;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This class provides static methods for sorting object arrays using 
 * bottom-up merge sort. The algorithm used is a bottom-up merge sort.
 * 
 * @author Rodion "rodde" Efremov
 * @version 1.6
 */
public class BottomUpMergesort {

    /**
     * Specifies the maximum length of a chunk which is sorted using insertion
     * sort.
     */
    private static final int INSERTIONSORT_THRESHOLD = 13;

    /**
     * Sorts the range {@code array[fromIndex], array[fromIndex + 1], ...,
     * array[toIndex - 2], array[toIndex - 1]}.
     * 
     * @param <T>       the actual array component type.
     * @param array     the array holding the target range.
     * @param fromIndex the starting (inclusive) index of the range to sort.
     * @param toIndex   the ending (exclusive) index of the range to sort. 
     * @param cmp       the object comparator.
     */
    public static <T> void sort(T[] array,
                                int fromIndex,
                                int toIndex,
                                Comparator<? super T> cmp) {
        if (toIndex - fromIndex < 2) {
            // Trivially sorted or indices ass-basckwards.
            return;
        }

        // Create the auxiliary buffer.
        int rangeLength = toIndex - fromIndex;
        T[] buffer = Arrays.copyOfRange(array, fromIndex, toIndex);

        // Find out how many merge passes we need to do over the input range.
        int runs = rangeLength / INSERTIONSORT_THRESHOLD +
                  (rangeLength % INSERTIONSORT_THRESHOLD != 0 ? 1 : 0);
        int mergePasses = getMergePassAmount(runs);

        // Set up the state.
        T[] source;
        T[] target;
        int sourceOffset;
        int targetOffset;

        if (mergePasses % 2 == 0) {
            // If here, we will do an even amount of merge passes.
            source = array;
            target = buffer;
            sourceOffset = fromIndex;
            targetOffset = 0;
        } else {
            // If here, we will do an odd amount of merge passes.
            source = buffer;
            target = array;
            sourceOffset = 0;
            targetOffset = fromIndex;
        }

        // Create the initial runs.
        for (int i = 0; i < runs - 1; ++i) {
            int tmpIndex = sourceOffset + i * INSERTIONSORT_THRESHOLD;
            insertionSort(source, 
                          tmpIndex, 
                          tmpIndex + INSERTIONSORT_THRESHOLD,
                          cmp);
        }

        // Do not forget the last (the righmost) run. Note, that the length of
        // the last run may vary between 1 and INSERTIONS_SORT_THRESHOLD, 
        // inclusively.
        int lastRunStartIndex = sourceOffset + (runs - 1) * 
                                INSERTIONSORT_THRESHOLD;
        insertionSort(source, 
                      lastRunStartIndex,
                      Math.min(lastRunStartIndex + INSERTIONSORT_THRESHOLD, 
                               sourceOffset + rangeLength),
                      cmp);

        // Initial runs are ready to be merged. 'runWidth <<= 1' multiplies
        // 'runWidth' by 2.
        for (int runWidth = INSERTIONSORT_THRESHOLD; 
                 runWidth < rangeLength;
                 runWidth <<= 1) {

            int runIndex = 0;

            for (; runIndex < runs - 1; runIndex += 2) {
                // Set up the indices.
                int leftIndex = sourceOffset + runIndex * runWidth;
                int leftBound = leftIndex + runWidth;
                int rightBound = Math.min(leftBound + runWidth, 
                                          rangeLength + sourceOffset);
                int targetIndex = targetOffset + runIndex * runWidth;

                // Perform the actual merging.
                merge(source,
                      target,
                      leftIndex,
                      leftBound,
                      rightBound,
                      targetIndex,
                      cmp);
            }

            if (runIndex < runs) {
                // 'runIndex' is the index of the "orphan" run at the end of the
                // range being sorted. Since it may appear in the opposite 
                // array as two non-merged runs, we have to simply copy this 
                // orphan run to the opposite array.
                System.arraycopy(source,
                                 sourceOffset + runIndex * runWidth,
                                 target,
                                 targetOffset + runIndex * runWidth,
                                 rangeLength - runIndex * runWidth);
            }

            runs = (runs >>> 1) + (runs % 2 == 0 ? 0 : 1);
            // Change the roles of the arrays.
            T[] tmparr = source;
            source = target;
            target = tmparr;
            int tmp = sourceOffset;
            sourceOffset = targetOffset;
            targetOffset = tmp;
        }
    }

    /**
     * Sorts the entire array.
     * 
     * @param <T>   the array component type.
     * @param array the array to sort.
     * @param cmp   the comparator.
     */
    public static <T> void sort(T[] array, Comparator<? super T> cmp) {
        sort(array, 0, array.length, cmp);
    }

    /**
     * Sorts the range {@code array[fromIndex,], array[fromIndex + 1], ...,
     * array[toIndex - 2], array[toIndex - 1]} using insertion sort. This 
     * implementation is <b>stable</b>.
     * 
     * @param <T>       the array component type.
     * @param array     the array holding the requested range.
     * @param fromIndex the starting (inclusive) index.
     * @param toIndex   the ending (exclusive) index.
     * @param cmp       the array component comparator.
     */
    public static <T> void insertionSort(T[] array,
                                         int fromIndex,
                                         int toIndex,
                                         Comparator<? super T> cmp) {
        for (int i = fromIndex + 1; i < toIndex; ++i) {
            T element = array[i];
            int j = i;

            for (; j > fromIndex && cmp.compare(array[j - 1], element) > 0; --j) {
                array[j] = array[j - 1];
            }

            array[j] = element;
        }
    }

    /**
     * Returns the amount of merge passes needed to sort a range containing 
     * {@code runs} runs. (A run is any contiguous, strictly descending or
     * ascending subsequence.
     * 
     * @param  runs the amount of runs in the target range.
     * @return the amount of needed merge passes.
     */
    private static int getMergePassAmount(int runs) {
        return 32 - Integer.numberOfLeadingZeros(runs - 1);
    }

    /**
     * Merges the sorted ranges {@code source[leftIndex, leftBound)} and
     * {@code source[rightIndex, rightBound)} putting the result to 
     * {@code target} starting from component with index {@code targetIndex}.
     * 
     * @param <T>         the array component type.
     * @param source      the source array.
     * @param target      the target array.
     * @param leftIndex   the (inclusive) starting index of the left run.
     * @param leftBound   the (exclusive) ending index of the left run.
     * @param rightIndex  the (inclusive) starting index of the right run.
     * @param rightBound  the (exclusive) ending index of the right run.
     * @param targetIndex the starting index of the result run in the target
     *                     array.
     * @param cmp         the element comparator.
     */
    private static <T> void merge(T[] source,
                                  T[] target,
                                  int leftIndex,
                                  int leftBound,
                                  int rightBound,
                                  int targetIndex,
                                  Comparator<? super T> cmp) {
        int rightIndex = leftBound;

        while (leftIndex < leftBound && rightIndex < rightBound) {
            target[targetIndex++] = 
                    cmp.compare(source[rightIndex], source[leftIndex]) < 0 ?
                    source[rightIndex++] :
                    source[leftIndex++];
        }

        System.arraycopy(source, 
                         leftIndex, 
                         target, 
                         targetIndex, 
                         leftBound - leftIndex);

        System.arraycopy(source, 
                         rightIndex, 
                         target, 
                         targetIndex, 
                         rightBound - rightIndex);
    }
}
