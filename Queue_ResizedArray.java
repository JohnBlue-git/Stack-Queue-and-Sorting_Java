/*
Auther: John Blue
Time: 2022/3
Platform: ATOM with atom-ide-ui, ide-java, and script
SDK: java SE 8 SDK
Object: Queue implemented by Resized Array
Reference: ...
*/



import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue_ResizedArray<Item> implements Iterable<Item> {
    // initial capacity of underlying resizing array
    private static final int INIT_CAPACITY = 8;

    // variable
    private int n;            // number of elements on stack
    private Item[] a;         // array of items
    private int head, end;

    // constructor
    public Queue_ResizedArray() {
        this.n = 0;
        this.a = (Item[]) new Object[INIT_CAPACITY];
        this.head = 0;
        this.end = 0;
    }
    //
    public Queue_ResizedArray(final Queue_ResizedArray<Item> copy) {
      this.n = copy.n;
      // idon;t know why the folllowing is dangourrous too @@
      this.a = (Item[]) new Object[INIT_CAPACITY];
      for (int i = 0; i < n; i++) {
        this.a[i] = copy.a[i];
      }
      this.head = copy.head;
      this.end = copy.end;
    }

    // function
    public boolean isEmpty() {
        return n == 0;
    }
    //
    public int size() {
        return n;
    }
    // resize the underlying array holding the elements
    // resized array is dangerous ... not so recommend
    private void resize(int capacity) {
        // smaller then don't resize
        if (capacity <= n) {
          return;
        }
        // textbook implementation
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = a[i];
        }
        //a = null;
        a = copy;
        head = 0;// necessary ? but i don't know why
        end = n;// necessary ? but i don't know why
    }
    //
    public void enqueue(Item item) {
        if (n == a.length) resize(2 * a.length);    // double size of array if necessary
        a[end++] = item;                        // add item
        if (end == a.length) end = 0;          // wrap-around
        n++;
    }
    //
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = a[head];
        a[head] = null;                            // to avoid loitering
        head++;
        n--;
        if (head == a.length) head = 0;           // wrap-around
        // shrink size of array if necessary
        if (n > 0 && n == a.length / 4) resize(a.length / 2);
        return item;
    }
    //
    public Item front() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[head];
    }
    //
    public Item back() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return a[end];
    }

    // iterator
    public Iterator<Item> iterator() {
        return new ReverseArrayIterator();
    }
    //
    private class ReverseArrayIterator implements Iterator<Item> {
        private int i;

        public ReverseArrayIterator() {
            i = n - 1;
        }

        public boolean hasNext() {
            return i >= 0;
        }

        // an iterator, doesn't implement remove() since it's optional
        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return a[i--];
        }
    }



    // main
    public static void main(String[] args) {
      //
      Queue_ResizedArray<Integer> array = new Queue_ResizedArray<Integer>();
      //
      System.out.println("enqueue >>");
      for (int i = 0; i < 6; i++) {
        array.enqueue(i);
      }
      //
      System.out.println("reverse iteration >>");
      Iterator<Integer> it = array.iterator();
      while (it.hasNext()) {
        System.out.print(it.next() + " ");
      }
      System.out.print("\n");
      //
      System.out.println("dequeue >>");
      while (!array.isEmpty()) {
        System.out.print(array.dequeue() + " ");
      }
      System.out.print("\n");
    }
}
