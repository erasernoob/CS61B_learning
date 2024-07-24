package deque;

import java.awt.*;

public class ArrayDeque<Gdle> {
    public Gdle[] item;
    private int capacity;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** NOTE: when setting the nextFirst and nextLast index for the first time
     *  set them the initial value to adjacent to make sure there is no "fake full" phenomenon*/
    public ArrayDeque() {
        item = (Gdle []) new Object[8];
        capacity = item.length;
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    public void conFirst() {
        while(nextFirst >= capacity) {
            nextFirst = ((nextFirst - 1) + capacity) % capacity;
        }
    }
    public void conLast() {
        while(item[nextLast] != null || nextFirst >= capacity) {
            nextLast = (nextLast + 1) % capacity;
        }
    }

    /** Get the elements by index */
    public Gdle get(int index) {
        return item[index];
    }

    public double getRatio() {
        return 1.0 * size / capacity;
    }

    /** usage too low to shrink the size to upper the usageRatio */
    public void shrinkSize() {
        int cap = capacity / 4;
        Gdle[] a = (Gdle []) new Object[cap];
        int first = nextFirst;
        int cnt = 0;
        for(int i = 0; i < capacity; i++) {
            first = getTheRealIndex(first);
            if(cnt == size) {
                break;
            }
            if(item[first] == null) {
                continue;
            }

            a[i] = item[first];
            cnt++;
        }
        item = a;
        capacity = cap;
        conFirst();
        conLast();
    }


    public Gdle removeLast() {
        int num = ((nextLast - 1) + capacity) % capacity;
        Gdle x = item[num];
        item[num] = null;
        nextLast = num;
        size--;
        detectForRatio();
        return x;
    }

    public void detectForRatio() {
        double ratio = getRatio();
        if(ratio <= 0.25 && capacity >= 16) {
            shrinkSize();
        }
    }

    public Gdle removeFirst() {
        Gdle x = item[nextFirst+1];
        item[nextFirst + 1] = null;
        nextFirst = (nextFirst + 1) % capacity;
        size--;
        detectForRatio();
        return x;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return nextFirst == nextLast || size == capacity;
    }
    public void addLast(Gdle x) {
        if(isFull()) {
            resize(capacity * 2);
        }
        item[nextLast] = x;
        size++;
        conLast();
    }

    public void printDeque() {
        int first = nextFirst;
        for(int i = 0; i < capacity; i++) {
            first = getTheRealIndex(first);
            if(item == null) {
                continue;
            }
            System.out.print(item[first]);
            System.out.print(" ");
        }
        System.out.println();
    }

    public void addFirst(Gdle x) {
        if(isFull()) {
            resize(capacity * 2);
        }
        item[nextFirst] = x;
        size++;
        conFirst();
    }

    public int size() {
        return capacity;
    }

    public int getTheRealIndex(int first) {
        return (first + 1) % capacity;
    }

    /** How to do this correctly >>>>>>>>>>>>>>>>>>
     * Also need to change the index of the two nextfirst and the nextlast*/
    public void resize(int cap) {
        Gdle[] a = (Gdle []) new Object[cap];
        int first = nextFirst;
        for(int i = 0; i < capacity; i++) {
            first = getTheRealIndex(first);
            a[i] = item[first];
        }
        item = a;
        capacity = cap;
        conFirst();
        conLast();
    }

}
