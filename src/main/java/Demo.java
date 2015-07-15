import java.util.Arrays;
import java.util.Random;
import static net.coderodde.util.sorting.Utils.isSorted;
import static net.coderodde.util.sorting.Utils.arraysIdentical;
import static net.coderodde.util.sorting.Utils.createRandomIntegerArray;
import net.coderodde.util.sorting.BottomUpMergesort;

public class Demo {

    private static final int SIZE = 5000000;
    private static final int FROM_INDEX = 3;
    private static final int TO_INDEX = SIZE - 2;
    
    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        BottomUpMergesort.setInsertionsortThreshold(8);
        Integer[] array1 = createRandomIntegerArray(SIZE, random);
        Integer[] array2 = array1.clone();
        
        System.out.println("Seed: " + seed);
        
        //// java.util.Arrays.sort
        long ta = System.currentTimeMillis();
        Arrays.sort(array1, FROM_INDEX, TO_INDEX, Integer::compare);
        long tb = System.currentTimeMillis();

        System.out.println(
                "java.util.Arrays.sort() in " + (tb - ta) + " ms. Sorted: " +
                isSorted(array1, FROM_INDEX, TO_INDEX, Integer::compare));

        //// net.coderodde.util.sorting.BottomUpMergesort.sort
        ta = System.currentTimeMillis();
        BottomUpMergesort.sort(array2, FROM_INDEX, TO_INDEX, Integer::compare);
        tb = System.currentTimeMillis();
        
        System.out.println(
                "net.coderodde.util.sorting.BottomUpMergesort.sort() " +
                (tb - ta) + " ms. Sorted: " + 
                isSorted(array2, FROM_INDEX, TO_INDEX, Integer::compare));

        System.out.println(
                "Arrays identical: " + arraysIdentical(array1, array2));
    }
}
