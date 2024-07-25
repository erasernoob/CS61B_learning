package deque;
import java.util.Comparator;

public class MaxArrayDeque<Item> extends ArrayDeque<Item> {

    private Comparator<Item> c;

    /** 1.Creates the MaxArrayDeque by using the given Comparator
     *  2.Will explicitly call the constructor of parentClass (have no parameter) */
    public MaxArrayDeque(Comparator<Item> c) {
        super();
        this.c = c;
    }

    /** return the maximum elements of the whole Deque
     *  Using the given Comparator */
    public Item max() {
        if(isEmpty()) {
            return null;
        }
        Item[] a = (Item[]) new Object[this.size()];
        a = getTheSortArray();
        Item max = a[0];
        for(int i = 0; i < this.size(); i++) {
            if(c.compare(max, a[i]) < 0) {
                max = a[i];
            }
        }
        return max;
    }

    public Item max(Comparator<Item> c) {
        if(isEmpty()) {
            return null;
        }
        Item[] a = (Item[]) new Object[this.size()];
        a = getTheSortArray();
        Item max = a[0];
        for(int i = 0; i < this.size(); i++) {
            if(c.compare(max, a[i]) < 0) {
                max = a[i];
            }
        }
        return max;
    }


}
