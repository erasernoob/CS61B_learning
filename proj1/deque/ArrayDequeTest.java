package deque;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void addTest() {
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            if (i % 2 == 0) {
                arr.addLast(i);
            } else {
                arr.addFirst(i);
            }
        }
    }

    @Test
    public void removeFirstTest() {
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            if (i % 2 == 0) {
                arr.addLast(i);
            } else {
                arr.addFirst(i);
            }
        }
        int tmp;
        for (int i = 0; i < 16; i++) {
            arr.removeFirst();
        }
    }

    @Test
    public void theAddMethodTestSimple() {
        int tmp = 1;
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        arr.addLast(0);
        arr.addFirst(0);


        ArrayDeque<Integer> arr1 = new ArrayDeque<>();
        arr1.addFirst(0);
        arr1.addLast(0);
        assertTrue(arr.equals(arr1));


//        assertArrayEquals(new Integer[]{1, 2, 3, -1}, arr1.getTheSortArray());

    }

    @Test
    public void removeTest() {
        int tmp = 1;
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            if (i % 2 == 0) {
                arr.addLast(i);
            } else {
                arr.addFirst(i);
            }
        }
        for (int i = 0; i < 16; i++) {
            arr.removeLast();
        }
    }

    @Test
    public void shrinkTheSizeTest() {
        int tmp = 1;
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for (int i = 16; i >= 0; --i) {
            arr.addFirst(i);
        }
        for (int j = 16; j >= 0; --j) {
            arr.removeLast();
        }
    }

}
