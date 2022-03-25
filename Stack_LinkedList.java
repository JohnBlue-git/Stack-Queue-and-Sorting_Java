/*
Auther: John Blue
Time: 2022/3
Platform: ATOM with atom-ide-ui, ide-java, and script
SDK: java SE 8 SDK
Object: Stack implemented by LinekedList
Reference: ...
*/



import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack_LinkedList<Item> implements Iterable<Item> {
    // variable
    private int n;          // size of the stack
    private Node first;     // top of stack

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
        }
    }

    // constructor
    public Stack_LinkedList() {
        this.n = 0;
        this.first = null;
    }
    public Stack_LinkedList(final Stack_LinkedList<Item> copy) {// copy have to check <Item> again
      if (copy.isEmpty()) {
        this.n = 0;
        this.first = null;
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
      if (isEmpty()) throw new NoSuchElementException("Stack underflow");
      Node current = first;
      System.out.print("item: ");
      while (current != null) {
        System.out.print(current.item + " ");
        current = current.next;
      }
      System.out.print("\n");
    }
    //
    public void push(Item item) {
        Node nd = new Node(item);
        n++;
        if (isEmpty()) {
          first = nd;
          return;
        }
        nd.next = first;
        first = nd;
    }
    //
    public Item pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        Item item = first.item;
        first = first.next;
        n--;
        return item;
    }
    //
    public Item front() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        return first.item;
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
        Stack_LinkedList<Integer> stack = new Stack_LinkedList<Integer>();
        //
        System.out.println("push >>");
        for (int i = 0; i < 6; i++) {
          stack.push(i);
        }
        //
        System.out.println("print >>");
        stack.Print();
        //
        System.out.println("iteration >>");
        Iterator<Integer> it = stack.iterator();
        while (it.hasNext()) {
          System.out.print(it.next() + " ");
        }
        System.out.print("\n");
        //
        System.out.println("copy and pop >>");
        Stack_LinkedList<Integer> copy = new Stack_LinkedList<Integer>(stack);
        System.out.print("item: ");
        for (int i = 0; i < 6; i++) {
          System.out.print(copy.pop() + " ");
        }
        if (copy.isEmpty()) {
          System.out.println("\ncopy is empty now");
        }
        System.out.print("\n");
    }
}
