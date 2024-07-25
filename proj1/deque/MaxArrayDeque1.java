package deque;
import java.util.Comparator;

public class MaxArrayDeque1<T> extends ArrayDeque<T> {
    private Comparator<T> c;

    public MaxArrayDeque1(Comparator<T> c) {
        this.c = c;
    }

    /** Use the existed comparator to get the maximum elements */
    public T max() {
        if(isEmpty()) {
            return null;
        }
        T Max = item[0];
        for(int i = 0; i < size(); i++) {
            if(c.compare(Max, item[i]) < 0) {
                Max = item[i];
            }
        }
        return Max;
    }

    /** Use the given Comparator to get the Maximum elements */
    public T max(Comparator<T> c) {
        if(isEmpty()) {
            return null;
        }
        T Max = item[0];
        for(int i = 0; i < size(); i++) {
            if(c.compare(Max, item[i]) < 0) {
                Max = item[i];
            }
        }
        return Max;
    }
}
