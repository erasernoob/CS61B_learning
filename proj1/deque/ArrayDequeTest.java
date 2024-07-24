package deque;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void removeTest() {
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
    public void shrinkTheSizeTest() {
        ArrayDeque<Integer> arr = new ArrayDeque<>();
        for(int i = 16; i >= 0; --i) {
            arr.addFirst(i);
        }
        for(int j = 16; j >= 0; --j) {
            arr.removeLast();
        }
    }

}
