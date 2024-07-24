public class SSList {
    /** Doesn't use the SSList class's things so it can be static */
    /** nested class */
    private static class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int x, IntNode n) {
            next = n;
            item = x;
        }
    }

    private IntNode sentinel;
    private int size;


    public SSList() {
        sentinel = new IntNode(-1, null);
        size = 0;
    }

    public SSList(int x) {
        sentinel = new IntNode(-1, null);
        sentinel.next = new IntNode(x, null);
    }


    /** Return the first elements of the list */
    public int getFirst() {
        return sentinel.next.item;
    }

    public void addFirst(int x) {
        sentinel.next.next = new IntNode(x, null);
        size++;
    }

    /** The private method help the real method in the recursive data Structure */
    private static int size(IntNode p) {
        if(p.next == null) {
            return 1;
        }
        return size(p.next) + 1;
    }

    public int size() {
        /** private static method for helper */
        return size(sentinel.next);
    }

    /** sentinel node */
    public void addLast(int x) {
        IntNode p = sentinel;
        while(p.next != null) {
            p = p.next;
        }
        p.next = new IntNode(x, null);
    }

    public int getLast() {
        IntNode p = sentinel;
        while(p.next != null) {
            p = p.next;
        }
        return p.item;
    }

    public static void main(String[] args) {
        SSList list = new SSList(3);
        list.addFirst(2);
        list.addLast(9);
        System.out.println(list.getLast());
        System.out.println(list.size());

    }

}



