package net.coderodde.util.sorting;

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;
import static net.coderodde.util.sorting.Utils.arraysIdentical;
import static net.coderodde.util.sorting.Utils.isSorted;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class BottomUpMergesortTest {
    
    @Test
    public void testIsSorted() {
        Integer[] arr = new Integer[]{ 1, 2, 3, -4, -2, 5, 2, 7, 8 };
        assertFalse(isSorted(arr, Integer::compare));
        assertFalse(isSorted(arr, 1, arr.length - 1, Integer::compare));
        assertTrue(isSorted(arr, 0, 3, Integer::compare));
        assertTrue(isSorted(arr, arr.length - 3, arr.length, Integer::compare));
    }
    
    @Test
    public void testInsertionSort() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        System.out.println("testInsertionSort: seed = " + seed);
        
        for (int op = 0; op < 1000; ++op) {
            int size = random.nextInt(30);
            Integer[] arr = new Integer[size];
            
            for (int i = 0; i < size; ++i) {
                arr[i] = random.nextInt();
            }
            
            Integer[] arr2 = arr.clone();
            
            int startIndex = random.nextInt(size + 1);
            int endIndex = random.nextInt(size + 1);
            
            if (startIndex > endIndex) {
                int tmp = startIndex;
                startIndex = endIndex;
                endIndex = tmp;
            }
            
            BottomUpMergesort.insertionSort(arr, 
                                            startIndex, 
                                            endIndex, 
                                            Integer::compare);
            Arrays.sort(arr2, startIndex, endIndex, Integer::compare);
            assertTrue(isSorted(arr, startIndex, endIndex, Integer::compare));
            assertTrue(Arrays.equals(arr, arr2));
        }
    }
    
    @Test 
    public void testSortBrute() {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        
        System.out.println("testSortBrute: seed = " + seed);
        
        for (int op = 0; op < 1000; ++op) {
            int size = random.nextInt(50);
            Integer[] array1 = new Integer[size];
            
            for (int i = 0; i < array1.length; ++i) {
                array1[i] = random.nextInt(100);
            }
            
            Integer[] array2 = array1.clone();
            
            int startIndex = random.nextInt(size + 1);
            int endIndex = random.nextInt(size + 1);
            
            if (startIndex > endIndex) {
                int tmp = endIndex;
                endIndex = startIndex;
                startIndex = tmp;
            }
            
//            System.out.println("op: " + op);
            BottomUpMergesort.sort(array1, 
                                   startIndex, 
                                   endIndex, 
                                   Integer::compare);
            Arrays.sort(array2, startIndex, endIndex, Integer::compare);
            
            assertTrue(isSorted(array1, 
                                startIndex, 
                                endIndex, 
                                Integer::compare));
            
            assertTrue(isSorted(array2,
                                startIndex, 
                                endIndex, 
                                Integer::compare));
            
            assertTrue(true);
        }
    }
    
    @Test
    public void testBug1() {
        Integer[] array1 = new Integer[]{ 8, 22, 83, 26, 90, 98, 38, 64, 4, 10, 
                                          19, 70, 35, 52, 72, 23, 53, 51, 74, 
                                          23, 41, 37, 7, 23, 54, 24, 69, 42, 33, 
                                          42, 38, 95, 22, 6, 7, 84, 99, 60, 32, 
                                          88, 81, 37, 30, 85, 22, 13, 12, 53 };
        Integer[] array2 = array1.clone();
        
        BottomUpMergesort.sort(array1, 6, 35, Integer::compare);
        Arrays.sort(array2, 6, 35, Integer::compare);
        
        assertTrue(isSorted(array1, 6, 35, Integer::compare));
        assertTrue(isSorted(array2, 6, 35, Integer::compare));
        
        assertTrue(arraysIdentical(array1, array2));
    }
    
    @Test
    public void tinyTest1() {
        Integer[] array = new Integer[]{ 3, 2, 1, 9, -4, 3, 5, 4, -8, 1,
                                         31, -4, -3, 8, 8, 8, 2 };
        BottomUpMergesort.sort(array, 3, 13, Integer::compare);
        assertTrue(isSorted(array, 3, 13, Integer::compare));
    }
}
