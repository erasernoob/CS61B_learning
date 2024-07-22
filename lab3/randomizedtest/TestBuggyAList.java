package randomizedtest;

import afu.org.checkerframework.checker.igj.qual.I;
import edu.princeton.cs.algs4.StdRandom;
import org.apache.commons.collections.list.AbstractLinkedList;
import org.junit.Test;

import javax.sound.midi.SysexMessage;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> b = new BuggyAList<>();
        AListNoResizing<Integer> a = new AListNoResizing<>();
        for (int i = 0; i < 3; i += 1) {
            b.addLast(i);
            a.addLast(i);
        }

        for (int i = 0; i < 3; i++) {
            int tmp1 = a.removeLast();
            int tmp2 = b.removeLast();
            assertEquals(tmp1, tmp2);

        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> B = new BuggyAList<>();
        int N = 5000;
        for(int i = 0; i  < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 3);
            if(L.size() == 0 && operationNumber != 0) {
                operationNumber = 0;
            }
            if(operationNumber == 0) {
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                B.addLast(randVal);
                System.out.println("addLast(" + randVal +")");
            } else if(operationNumber == 1) {
                int size = L.size();
                System.out.println("size: " + size);
            } else if(operationNumber == 2) {
                assertEquals(L.removeLast(), B.removeLast());
            }
        }
    }
}
