/*
Auther: John Blue
Time: 2022/3
Platform: ATOM with atom-ide-ui, ide-java, and script
SDK: java SE 8 SDK
Object: Queue implemented by LinekedList
Reference: ...
*/



import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue_LinkedList<Item> implements Iterable<Item> {
    // variable
    private int n;          // size of the Queue
    private Node first;     // top of Queue
    private Node end;       // end of Queue

    // inner Node
    //private class Node {// private would have some access problem
    public class Node {
        // variable
        private Item item;
        private Node next;
        // constructor
        public Node() {}
        public Node(Item it) {
          this.item = it;
          this.next = null;
        }
        public Node(final Node nd) {
          this.item = nd.item;
          this.next = null;
          if (nd.next != null) {
            this.next = new Node(nd.next);
          }
          else {// !!! very important for setting end
            end = this;
          }
        }
    }

    // constructor
    public Queue_LinkedList() {
        this.n = 0;
        this.first = null;
        this.end = null;
    }
    public Queue_LinkedList(final Queue_LinkedList<Item> copy) {// copy have to check <Item> again
      if (copy.isEmpty()) {
        this.n = 0;
        this.first = null;
        this.end = null;
      }
      else {
        this.n = copy.n;
        this.first = new Node(copy.first);
      }
    }

    // function
    public boolean isEmpty() {
        return first == null;
    }
    //
    public int size() {
        return n;
    }
    //
    public void Print() {
      if (isEmpty()) throw new NoSuchElementException("Queue underflow");
      Node current = first;
      System.out.print("item: ");
      while (current != null) {
        System.out.print(current.item + " ");
        current = current.next;
      }
      System.out.print("\n");
    }
    //
    public void enqueue(Item item) {
        Node nd = new Node(item);
        n++;
        if (isEmpty()) {
          first = nd;
          end = nd;
          return;
        }
        end.next = nd;
        end = nd;
    }
    //
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = first.item;
        first = first.next;
        n--;
        if (isEmpty()) {
          end = null;
        }
        return item;
    }
    //
    public Item front() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return first.item;
    }
    //
    public Item back() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return end.item;
    }

    // iterator
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }
    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext()  { return current != null; }

        public void remove() { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // main
    public static void main(String[] args) {
        //
        Queue_LinkedList<Integer> que = new Queue_LinkedList<Integer>();
        //
        System.out.println("enqueue >>");
        for (int i = 0; i < 6; i++) {
          que.enqueue(i);
        }
        //
        System.out.println("print >>");
        que.Print();
        //
        System.out.println("iteration >>");
        Iterator<Integer> it = que.iterator();
        while (it.hasNext()) {
          System.out.print(it.next() + " ");
        }
        System.out.print("\n");
        //
        System.out.println("copy and dequeue >>");
        Queue_LinkedList<Integer> copy = new Queue_LinkedList<Integer>(que);
        copy.enqueue(666);
        System.out.print("origin ");
        que.Print();
        System.out.print("  copy item: ");
        while (!copy.isEmpty()) {
          System.out.print(copy.dequeue() + " ");
        }
        if (copy.isEmpty()) {
          System.out.println("\ncopy is empty now");
        }
        System.out.print("\n");
    }
}
