package deque;


import org.checkerframework.framework.qual.LiteralKind;
import org.eclipse.jetty.server.HttpInput;

/** Implement the Deque by using circular sentinel topology */
public class LinkedListDeque<Item> implements Deque<Item> {
    private ListNode sentinel;
    private int size;

    /** Empty case */
    public LinkedListDeque() {
        sentinel = new ListNode();
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /** here in order to have a garbage value for sentinel's item what should do?
     *  ANSWER IS: make a Empty constructor func for the ListNode
     */
    public LinkedListDeque(Item x) {
        sentinel = new ListNode(x, null, null); // make the sentinel's item = the first one's item
        sentinel.next = new ListNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
    }

    public Item get(int index) {
        int cnt = 0;
        ListNode T = sentinel.next;
        while(cnt != index) {
            T = T.next;
            cnt++;
        }
        return T.item;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void addLast(Item item) {
        ListNode T = new ListNode(item);
        T.next = sentinel;
        T.prev = sentinel.prev;
        sentinel.prev.next = T;
        sentinel.prev = T;
        size++;
    }

    public void addFirst(Item item){
        sentinel.next = new ListNode(item, sentinel, sentinel.next);
        size++;
    }

    public Item removeFirst() {
        Item tmp;
        tmp = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.next.prev = sentinel;
        size--;
        return tmp;
    }

    public Item removeLast() {
        Item tmp;
        tmp = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return tmp;
    }

    public void printDeque() {
        ListNode p = sentinel.next;
        // if p.next != sentinel in to loop
        while(p.next != sentinel) {
            System.out.print(p.item);
            System.out.print(" ");
            p = p.next;
        }
        System.out.print(p.item);
    }


    /** the subclass won't use anything instances in this big class then it will be the static type */
    public class ListNode {
        Item item;
        ListNode next;
        ListNode prev;

        public ListNode() {
            next = null;
            prev = null;
        }

        public ListNode(Item item) {
            this.item = item;
            next = prev = null;
        }

        public ListNode(Item x, ListNode prior, ListNode next) {
            item = x;
            this.prev = prior;
            this.next  = next;
        }
    }
}
