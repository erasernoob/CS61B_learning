package deque;

import java.util.Comparator;
import org.junit.Test;
import static org.junit.Assert.*;

public class MaxArrayDequeTest {

//    @Test
//    public void MaxArrayDequeTest() {
//        Comparator<String> c = getStringComparator();
//        MaxArrayDeque<String> arr = new MaxArrayDeque<>(c);
//        arr.addFirst("a");
//        arr.addFirst("cc");
//        arr.addLast("ii");
//        assertEquals(arr.max(), "ii");
//    }



    public static class StringComparator implements Comparator<String> {
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }
    public Comparator<String> getStringComparator() {
        return new StringComparator();
    }
}
