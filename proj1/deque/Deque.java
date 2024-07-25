package deque;

public interface Deque<Item> {
    public Item get(int index);
    public void removeLast(Item T);
    public void removeFirst(Item T);
    public void addFirst(Item T);
    public void addLast(Item T);
    public int size();
    default boolean isEmpty() {
        if(this.size() == 0) {
            return true;
        }
        return false;
    }
}
