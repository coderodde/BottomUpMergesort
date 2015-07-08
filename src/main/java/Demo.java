import java.util.Arrays;
import java.util.Random;
import net.coderodde.util.sorting.BottomUpMergesort;
import static net.coderodde.util.sorting.Utils.isSorted;
import static net.coderodde.util.sorting.Utils.arraysIdentical;
import static net.coderodde.util.sorting.Utils.createRandomIntegerArray;
public class Demo {

    private static final int SIZE = 2000000;
    
    public static void main(String[] args) {
        long seed = System.currentTimeMillis();
        Random random = new Random(seed);
        Integer[] array1 = createRandomIntegerArray(SIZE, random);
        Integer[] array2 = array1.clone();
        System.out.println("Seed: " + seed);
        
        //// java.util.Arrays.sort
        long ta = System.currentTimeMillis();
        Arrays.sort(array1, Integer::compare);
        long tb = System.currentTimeMillis();
        
        System.out.println(
                "java.util.Arrays.sort() in " + (tb - ta) + " ms. Sorted: " +
                isSorted(array1, 2, 9, Integer::compare));
        
        //// net.coderodde.util.sorting.BottomUpMergesort.sort
        ta = System.currentTimeMillis();
        BottomUpMergesort.sort(array2, Integer::compare);
        tb = System.currentTimeMillis();
        
        System.out.println(
                "net.coderodde.util.sorting.BottomUpMergesort.sort() " +
                (tb - ta) + " ms. Sorted: " + 
                isSorted(array2, 2, 9, Integer::compare));
        
        System.out.println(
                "Arrays identical: " + arraysIdentical(array1, array2));
    }
}
