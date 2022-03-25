/*
Auther: John Blue
Time: 2022/3
Platform: ATOM with atom-ide-ui, ide-java, and script
SDK: java SE 8 SDK
Object: generic sorting
selection sorting
insertion sorting
merge sorting
quick sorting(partition sort)
Reference: ...
*/

public class Sort {
    ////// !!! This class should not be instantiated.
    private Sort() { }



    ////// helper functions
    // is v < w ?
    private static boolean less(Comparable v, Comparable w) {
        if (v == w) return false;
        return v.compareTo(w) < 0;
    }

    // exchange a[i] and a[j]
    private static void exch(Object[] a, int i, int j) {
        Object swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // print array to standard output
    private static void show(Comparable[] a) {
        System.out.println("sequece:");
        for (int i = 0; i < a.length; i++) {
            System.out.print(" " + a[i]);
        }
        System.out.println("");
    }



    ////// Selection sorting
    public static void selection_sort(Comparable[] a) {
      int n = a.length;
      for (int i = 0; i < n; i++) {
          int min = i;
          for (int j = i+1; j < n; j++) {
              if (less(a[j], a[min])) {
                min = j;
              }
          }
          exch(a, i, min);
      }
    }



    ////// Insertion sorting
    public static void insertion_sort(Comparable[] a) {
      int n = a.length;
      for (int i = 1; i < n; i++) {
        for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
          exch(a, j, j - 1);
        }
      }
    }



    ////// Quick sorting
    //// privot from front
    public static void quick_sort_front(Comparable[] a) {
        quick_front(a, 0, a.length - 1);
    }
    // Sortsort the subarray from a[lo] to a[hi]
    private static void quick_front(Comparable[] a, int lo, int hi) {
        if (hi <= lo) {
          return;
        }
        int j = partition_front(a, lo, hi);
        quick_front(a, lo, j - 1);
        quick_front(a, j + 1, hi);
    }
    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition_front(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            // find item on lo to swap
            while (less(a[++i], v)) {
                if (i == hi) {
                  break;
                }
            }
            // find item on hi to swap
            while (less(v, a[--j])) {
                if (j == lo) {// redundant since a[lo] acts as sentinel
                  break;
                }
            }
            // check if pointers cross
            if (i >= j) {
              break;
            }
            exch(a, i, j);
        }
        // put partitioning item v at a[j]
        exch(a, lo, j);
        // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
        return j;
    }

    //// privot from back
    public static void quick_sort_back(Comparable[] a) {
        quick_back(a, 0, a.length - 1);
    }
    // Sortsort the subarray from a[lo] to a[hi]
    private static void quick_back(Comparable[] a, int lo, int hi) {
    	if (lo < hi) {
      	    int j = partition_back(a, lo, hi);
      	    quick_back(a, lo, j - 1);
      	    quick_back(a, j + 1, hi);
    	}
    }
    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition_back(Comparable[] a, int lo, int hi) {
      Comparable pivot = a[hi];
    	int i = lo;
      // method for:
    	for (int j = lo; j < hi; j++) {
        if (i++ == j) {
          continue;
        }
      	if (less(a[j], pivot)) {
              exch(a, i++, j);
        }
      }
      /*
      // method while:
      int j = lo;
      while(j++ < hi) {
          if (i++ == j) {
              continue;
          }
          if (less(a[j], pivot)) {
              exch(a, i++, j);
          }
      }
      */
      exch(a, i, hi);
      return i;
    }



    ////// merge sorting
    private void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        // copy to aux[]
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        // merge back to a[]
        int i = lo;
        int j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
              a[k] = aux[j++];
            }
            else if (j > hi) {
              a[k] = aux[i++];
            }
            else if (less(aux[j], aux[i])) {
              a[k] = aux[j++];
            }
            else {
              a[k] = aux[i++];
            }
        }
    }
    private void halve(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) {
          return;
        }
        int mid = lo + (hi - lo) / 2;
        halve(a, aux, lo, mid);
        halve(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }
    public void merge_sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        halve(a, aux, 0, a.length - 1);
    }
    public void mergeBU_sort(Comparable[] a) {
        int n = a.length;
        Comparable[] aux = new Comparable[n];
        for (int len = 1; len < n; len *= 2) {
            for (int lo = 0; lo < n - len; lo += len * 2) {
                int mid  = lo + len - 1;
                int hi = Math.min(lo + len * 2 - 1, n - 1);
                merge(a, aux, lo, mid, hi);
            }
        }
    }



    public static void main(String[] args) {
        System.out.println("Selection Sort >>");
        Integer sel[] = { 1, - 20, 300, - 4, 50 };
        show(sel);
        Sort.selection_sort(sel);
        show(sel);
        System.out.println("");

        System.out.println("Insertion Sort >>");
        Integer ins[] = { 1, - 20, 300, - 4, 50 };
        show(ins);
        Sort.insertion_sort(ins);
        show(ins);
        System.out.println("");

        System.out.println("Quick Sort(front) >>");
        Integer qf[] = { 1, - 20, 300, - 4, 50 };
        show(qf);
        Sort.quick_sort_front(qf);
        show(qf);
        System.out.println("");

        System.out.println("Quick Sort(back) >>");
        Integer qb[] = { 1, - 20, 300, - 4, 50 };
        show(qb);
        Sort.quick_sort_front(qb);
        show(qb);
        System.out.println("");

        System.out.println("Merge Sort >>");
        Integer m[] = { 1, - 20, 300, - 4, 50 };
        show(m);
        Sort.quick_sort_front(m);
        show(m);
        System.out.println("");
    }

}
