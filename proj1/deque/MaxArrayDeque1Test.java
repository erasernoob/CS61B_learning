package deque;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Comparator;

public class MaxArrayDeque1Test {

    private static class StringComparator implements Comparator<String> {

        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

    public Comparator<String> getTheStringComparator() {
        return new StringComparator();
    }

    @Test
    public void MaxArrayDequeTest1() {

    }
}
