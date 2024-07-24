package deque;


import org.checkerframework.framework.qual.LiteralKind;
import org.eclipse.jetty.server.HttpInput;

/** Implement the Deque by using circular sentinel topology */
public class LinkedListDeque<BeepLord> {
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
    public LinkedListDeque(BeepLord x) {
        sentinel = new ListNode(x, null, null); // make the sentinel's item = the first one's item
        sentinel.next = new ListNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public int size() {
        return size;
    }

    public void addLast(BeepLord item) {
        ListNode T = new ListNode(item);
        T.next = sentinel;
        T.prev = sentinel.prev;
        sentinel.prev.next = T;
        sentinel.prev = T;
        size++;
    }

    public void addFirst(BeepLord item){
        sentinel.next = new ListNode(item, sentinel, sentinel.next);
        size++;
    }

    public BeepLord removeFirst() {
        BeepLord tmp;
        tmp = sentinel.next.item;
        if(tmp == null) {
            return null;
        }
        sentinel.next = sentinel.next.next;
        sentinel.next.next.prev = sentinel;
        size--;
        return  tmp;
    }

    public BeepLord removeLast() {
        BeepLord tmp;
        tmp = sentinel.prev.item;
        if(tmp == null) {
            return null;
        }
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


    /** the subclass won't use anything in this big class then it will be the static type */
    public class ListNode {
        BeepLord item;
        ListNode next;
        ListNode prev;

        public ListNode() {
            next = null;
            prev = null;
        }

        public ListNode(BeepLord item) {
            this.item = item;
            next = prev = null;
        }

        public ListNode(BeepLord x, ListNode prior, ListNode next) {
            item = x;
            this.prev = prior;
            this.next  = next;
        }
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> Q = new LinkedListDeque<>(9);
        Q.addFirst(0);
        Q.addLast(8);
        Q.printDeque();

    }
}
