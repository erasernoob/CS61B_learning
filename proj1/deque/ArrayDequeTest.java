package deque;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Optional;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void addTest() {
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for(int i = 0; i < 16; i++) {
            if(i % 2 == 0) {
                arr.addLast(i);
            } else {
                arr.addFirst(i);
            }
        }
    }

    @Test
    public void removeFirstTest() {
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for(int i = 0; i < 16; i++) {
            if(i % 2 == 0) {
                arr.addLast(i);
            } else {
                arr.addFirst(i);
            }
        }
        int tmp;
        for(int i = 0; i < 16; i++) {
            arr.removeFirst(tmp);
        }
    }

    @Test
    public void theAddMethodTestSimple() {
        int tmp = 1;
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        arr.addFirst(-1);
        arr.addLast(4);
        arr.addFirst(3);
        arr.addFirst(2);
        arr.addLast(5);
        arr.addFirst(1);
        arr.addLast(7);
        arr.addFirst(0);

        arr.removeLast(tmp);
        arr.removeLast(tmp);
        arr.removeLast(tmp);
        arr.removeFirst(tmp);
        assertArrayEquals(new Integer[]{1, 2, 3, -1}, arr.getTheSortArray());
    }

    @Test
    public void removeTest() {
        int tmp = 1;
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for(int i = 0; i < 16; i++) {
            if(i % 2 == 0) {
                arr.addLast(i);
            } else {
                arr.addFirst(i);
            }
        }
        for(int i = 0; i < 16; i++) {
            arr.removeLast(tmp);
        }
    }

    @Test
    public void shrinkTheSizeTest() {
        int tmp = 1;
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for(int i = 16; i >= 0; --i) {
            arr.addFirst(i);
        }
        for(int j = 16; j >= 0; --j) {
            arr.removeLast(tmp);
        }
    }

}
