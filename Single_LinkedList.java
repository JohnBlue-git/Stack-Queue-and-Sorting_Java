
/*
Auther: John Blue
Time: 2022/3
Platform: ATOM with atom-ide-ui, ide-java, and script
SDK: java SE 8 SDK
Object: generic single LinkedList

Reference: ...
From:https://community.oracle.com/tech/developers/discussion/1185356/how-to-make-a-generic-type-comparable
Item extends Comparable
can let generic type become "Comparable" such that .comapreTo is available
*/

import java.util.Iterator;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Single_LinkedList<Item extends Comparable<Item>> implements Iterable<Item> {
    // variable
    private int n;          // size of the Single
    private Node first;     // top of Single
    private Node end;       // end of Single

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
    public Single_LinkedList() {
        this.n = 0;
        this.first = null;
        this.end = null;
    }
    public Single_LinkedList(final Single_LinkedList<Item> copy) {// copy have to check <Item> again
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
      if (isEmpty()) throw new NoSuchElementException("Single underflow");
      Node current = first;
      System.out.print("item: ");
      while (current != null) {
        System.out.print(current.item + " ");
        current = current.next;
      }
      System.out.print("\n");
    }
    //
    public void push_back(Item item) {
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
    public void push_front(Item item) {
        Node nd = new Node(item);
        n++;
        if (isEmpty()) {
          first = nd;
          end = nd;
          return;
        }
        nd.next = first;
        first = nd;
    }
    //
    public Item pop_front() {
        if (isEmpty()) throw new NoSuchElementException("Single underflow");
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
        if (isEmpty()) throw new NoSuchElementException("Single underflow");
        return first.item;
    }
    //
    public Item back() {
        if (isEmpty()) throw new NoSuchElementException("Single underflow");
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

    // compare implementation
    private boolean less(Comparable left, Comparable right){
        return left.compareTo(right) < 0;
    }

    // insertion sorting
    public Single_LinkedList insertion_sort() {
      // list for retrun
    	Single_LinkedList RT = new Single_LinkedList();

    	// sorting
    	Node current = first;
    	Node current_RT;
    	while (current != null) {
    		// point to RT first
    		current_RT = RT.first;
    		// the first round
    		if (current_RT == null) {
    			RT.push_front(current.item);
    		}
    		// second ... rounds
    		else {
    			// compare value
    			// condition 1: bigger than the whole RT list
    			if (!less(current.item, RT.end.item)) {
    				RT.push_back(current.item);
    			}
    			// condition 2: smaller than the whole RT list
    			else if (less(current.item, RT.end.item)) {
    				RT.push_front(current.item);
    			}
    			// condition 3: compare though loop
    			else {
    				// new node
    				Node newNode = new Node(current.item);
    				while (!less(current.item, current_RT.next.item)) {
    					current_RT = current_RT.next;
    				}
    				newNode.next = current_RT.next;
    				current_RT.next = newNode;
    				// increase size
    				RT.n++;
    			}
    		}
    		// iteration
    		current = current.next;
    	}
    	// return
    	return RT;
    }

    // merge sorting
    private Node merge(Node left, Node right) {
   		// root
      Node current;
      if (!less(left.item, right.item)) {
        current = right;
        right = right.next;
      }
      else {
        current = left;
        left = left.next;
      }
      Node root = current;
      // following
      while (left != null || right != null) {
        if (left == null) {
          current.next = right;
          right = right.next;
        }
        else if (right == null) {
          current.next = left;
          left = left.next;
        }
        else {
         if (!less(left.item, right.item)) {
           current.next = right;
           right = right.next;
         }
         else {
           current.next = left;
           left = left.next;
         }
       }
       current = current.next;
     }
     // end
     //???RT.end = current;
     // return
     return root;
   }

    private Node halve(Node p_l, int SP) {
    	if (SP == 1) {
    		Node rt = new Node(p_l.item);
    		return rt;
    	}/*
      else if (SP == 2) {
    		Node rt;
        if (!less(p_l.item, p_l.next.item)) {
          rt.next = new Node(p_l.next.item);
          rt = rt.next;
          rt.next = new Node(p_l.item);
        }
        else {
          rt.next = new Node(p_l.item);
          rt = rt.next;
          rt.next = new Node(p_l.next.item);
        }
    		return rt;
    	}*/
    	int spl = SP / 2; if ((SP % 2) != 0) { spl++; }
    	int spr = SP - spl;
    	Node p_r = p_l;
    	for (int i = 0; i < spl; i++) { p_r = p_r.next; }
    	Node left = halve(p_l, spl);
    	Node right = halve(p_r, spr);
    	return merge(left, right);
    }

   public Single_LinkedList merge_sort() {
     // list for return
     Single_LinkedList RT = new Single_LinkedList();

     // sorting
     RT.first = halve(first, n);
     RT.n = n;

     // return
     return RT;
   }

    public Single_LinkedList reverse() {
         Single_LinkedList RT = new Single_LinkedList();
         Node current = first;
         while (current != null) {
              RT.push_front(current.item);
              current = current.next;
         }
         return RT;
    }



    // main
    public static void main(String[] args) {
        //
        Single_LinkedList<Integer> sin = new Single_LinkedList<Integer>();
        //
        System.out.println("push >>");
        for (int i = 6; i > - 6; i--) {
          sin.push_back(i);
        }
        //
        System.out.println("print >>");
        sin.Print();
        //
        System.out.println("iteration >>");
        Iterator<Integer> it = sin.iterator();
        while (it.hasNext()) {
          System.out.print(it.next() + " ");
        }
        System.out.print("\n");
        //
        System.out.println("copy and pop >>");
        Single_LinkedList<Integer> copy = new Single_LinkedList<Integer>(sin);
        copy.push_front(666);
        System.out.print("origin ");
        sin.Print();
        System.out.print("  copy item: ");
        while (!copy.isEmpty()) {
          System.out.print(copy.pop_front() + " ");
        }
        if (copy.isEmpty()) {
          System.out.println("\ncopy is empty now");
        }
        //
        System.out.println("sort >>");
        //Single_LinkedList<Integer> st = sin.insertion_sort();
        Single_LinkedList<Integer> st = sin.merge_sort();
        st.Print();
        //
        System.out.println("reverse >>");
        Single_LinkedList<Integer> rv = st.reverse();
        rv.Print();
    }
}
