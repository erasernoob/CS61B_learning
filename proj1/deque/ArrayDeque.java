package deque;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.awt.*;

public class ArrayDeque<Item> implements Deque<Item> {
    public Item[] item;
    private int capacity;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** NOTE: when setting the nextFirst and nextLast index for the first time
     *  set them the initial value to adjacent to make sure there is no "fake full" phenomenon*/
    public ArrayDeque() {
        item = (Item []) new Object[8];
        capacity = item.length;
        // 这里的两个索引的值，是要求可以随机分配的吗？
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }


    // 以下都是计算更新索引的值的
    public void conFirst() {
        while(nextFirst >= capacity || item[nextFirst] != null) {
            nextFirst = ((nextFirst - 1) + capacity) % capacity;
        }
    }

    public void conLast() {
        while(item[nextLast] != null || nextFirst >= capacity) {
            nextLast = (nextLast + 1) % capacity;
        }
    }

    /** Get the elements by index */
    public Item get(int index) {
        return item[index];
    }

    public double getRatio() {
        return 1.0 * size / capacity;
    }

    /** usage too low to shrink the size to upper the usageRatio */
    // 缩减大小的问题在于，如何进行对两个左右索引的重新分配，缩减后大小的分配
    public void shrinkSize() {
        int cap = capacity / 4 + 1;
        Item[] a = (Item []) new Object[cap];

        int first = (nextFirst + 1) % capacity;
        int index = 0;
        while(first != nextLast) {
            if(item[first] != null) {
                a[index++] = item[first];
            }
            first = (first + 1) % capacity;
        }

        // 思路：将后面的值直接接在前一个队列的队尾，以当前这种特殊的情况，将两索引都指向多分配出来的那个box

        item = a;
        nextFirst = nextLast = a.length-1;
        capacity = cap;
    }

    public void removeLast(Item T) {
        int num = ((nextLast - 1) + capacity) % capacity;
        Item x = item[num];
        item[num] = null;
        nextLast = num;
        size--;
        detectForRatio();
        T = x;
    }

    public void detectForRatio() {
        double ratio = getRatio();
        if(ratio <= 0.25 && capacity >= 16) {
            shrinkSize();
        }
    }

    public void removeFirst(Item T) {
        int index = (nextFirst + 1) % capacity;
        Item x = item[index];
        item[index] = null;
        nextFirst = index;
        size--;
        detectForRatio();
        T = x;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return nextFirst == nextLast || size == capacity;
    }
    public void addLast(Item x) {
        if(isFull()) {
            resize(capacity * 2);
        }
        item[nextLast] = x;
        size++;
        conLast();
    }

    public Item[] getTheSortArray() {
        Item[] a = (Item[]) new Object[size];
        int first = (nextFirst + 1) % capacity;
        int index = 0;
        while(first != nextLast) {
            if(item[first] != null) {
                a[index++] = item[first];
            }
            first = (first + 1) % capacity;
        }
        return a;
    }

    public void printDeque() {
        int first = (nextFirst + 1) % capacity;
        while(first != nextLast) {
            if(item[first] != null) {
                System.out.print(item[first] + " ");
            }
            first = (first + 1) % capacity;
        }
        System.out.println();
    }

    public void addFirst(Item x) {
        if(isFull()) {
            resize(capacity * 2);
        }
        item[nextFirst] = x;
        size++;
        conFirst();
    }

    public int size() {
        return size;
    }

    public int getTheRealIndex(int first) {
        return (first + 1) % capacity;
    }

    /** How to do this correctly >>>>>>>>>>>>>>>>>>
     * Also need to change the index of the two nextfirst and the nextlast*/
    public void resize(int cap) {
        Item[] a = (Item []) new Object[cap];
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
