package prog09;
import java.util.Random;

public class SortTest<E extends Comparable<E>> {
  public void test (Sorter<E> sorter, E[] array) {
    E[] copy = array.clone();
    sorter.sort(copy);
    System.out.println(sorter);
    if(copy.length <= 100){
    	for (int i = 0; i < copy.length; i++)
    		System.out.print(copy[i] + " ");
    }
    System.out.println();
  }
  
  public static void main (String[] args) {
    Integer[] array = { 3, 1, 4, 1, 5, 9, 2, 6 };

    if (args.length > 0) {
      // Print out command line argument if there is one.
      System.out.println("args[0] = " + args[0]);

      // Create a random object to call random.nextInt() on.
      Random random = new Random(0);

      // Make array.length equal to args[0] and fill it with random
      // integers:
      array = new Integer[Integer.parseInt(args[0])];
      for(int i = 0; i < array.length; i++)
    	  array[i] = random.nextInt();
      if(array.length <= 100)
    	  System.out.println(array);
    }

    long start; 
    long end;
    SortTest<Integer> tester = new SortTest<Integer>();
//     start = System.currentTimeMillis();
//     tester.test(new InsertionSort<Integer>(), array);
//     end = System.currentTimeMillis();
//     System.out.println("Insertion Sort Time =" + (end-start));
//     start = System.currentTimeMillis();
//     tester.test(new HeapSort<Integer>(), array);
//     end = System.currentTimeMillis();
//     System.out.println("Heap Sort Time =" + (end-start));
     start = System.currentTimeMillis();
     tester.test(new QuickSort<Integer>(), array);
     end = System.currentTimeMillis();
     System.out.println("Quick Sort Time =" + (end-start));
     start = System.currentTimeMillis();
     tester.test(new MergeSort<Integer>(), array);
     end = System.currentTimeMillis();
     System.out.println("Merge Sort Time =" + (end-start));
     
     /*
      * args[0] = 1000
      * prog09.InsertionSort@6da264f1
	  *Insertion Sort Time =106
	  *prog09.HeapSort@7c1c8c58
	  *Heap Sort Time =34
	  *prog09.QuickSort@4b0d78ec
	  *Quick Sort Time =27
	  *prog09.MergeSort@3b706ad7
      */
     /*
      * args[0] = 10000
      * prog09.InsertionSort@40914272
      * Insertion Sort Time =162
      * prog09.HeapSort@36422510
      * Heap Sort Time =36
      * prog09.QuickSort@617df472
      * Quick Sort Time =36
      * prog09.MergeSort@517667bd
      * Merge Sort Time =45
     */
     /*
      * args[0] = 100000
      * prog09.InsertionSort@65979a36
      * Insertion Sort Time =26118
      * prog09.HeapSort@1667a232
      * Heap Sort Time =105
      * prog09.QuickSort@b2929b2
      * Quick Sort Time =66
      * prog09.MergeSort@60b99e4c
      * Merge Sort Time =171
     */
     /*
      * args[0] = 1000000
      * prog09.HeapSort@143b9a5f
      * Heap Sort Time =2228
      * prog09.QuickSort@16ad9f5d
      * Quick Sort Time =1304
      * prog09.MergeSort@76749ebc
      * Merge Sort Time =1883
      */
     /*
      * args[0] = 10000000
      * prog09.QuickSort@15664f1a
      * Quick Sort Time =7502
      * prog09.MergeSort@17e06b12
      * Merge Sort Time =8589
      */
     
  }
}

class InsertionSort<E extends Comparable<E>>
  implements Sorter<E> {
  public void sort (E[] array) {
    for (int n = 0; n < array.length; n++) {
      E data = array[n];
      int i = n;
      // while array[i-1] > data move array[i-1] to array[i] and
      // decrement i
      while(i > 0 && array[i-1].compareTo(data) >= 0) {
    	  array[i] = array[i-1];
    	  i--;
      }
      array[i] = data;
    }
  }
}

class HeapSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array;
  
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
  
  public void sort (E[] array) {
    this.array = array;
    
    for (int i = parent(array.length - 1); i >= 0; i--)
      swapDown(i, array.length - 1);
    
    for (int n = array.length - 1; n >= 0; n--) {
      swap(0, n);
      swapDown(0, n - 1);
    }
  }
  
  public void swapDown (int root, int end) {
    // Calculate the left child of root.
	  int leftChild = left(root);
	  int rightChild;
	  int biggerChild;
    // while the left child is still in the array
	  while(leftChild <= end) {
    //   calculate the right child
		  rightChild = right(root);
    //   if the right child is in the array and 
    //      it is bigger than than the left child		  
		  if(rightChild <= end && array[rightChild].compareTo(array[leftChild]) >= 0){
    //     bigger child is right child
			  biggerChild = rightChild;
    //   else
		  } else {
    //     bigger child is left child
			  biggerChild = leftChild;
		  }
    //   if the root is not less than the bigger child
	      if(root >= biggerChild){
   //     return	    	  
	    	  return;
	      }
    //   swap the root with the bigger child
	      swap(root, biggerChild);
    //   update root and calculate left child
	      root = biggerChild;
	      leftChild = left(root);
	  }
  }
  
  private int left (int i) { return 2 * i + 1; }
  private int right (int i) { return 2 * i + 2; }
  private int parent (int i) { return (i - 1) / 2; }
}

class QuickSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array;
  private void swap (int i, int j) {
    E data = array[i];
    array[i] = array[j];
    array[j] = data;
  }
  
  public void sort (E[] array) {
    this.array = array;
    sort(0, array.length-1);
  }
  
  private void sort(int left, int right) {
    if (left >= right)
      return;
    
    swap(left, (left + right) / 2);
    
    E pivot = array[left];
    int a = left + 1;
    int b = right;
    while (a <= b) {
      // Move a forward if array[a] <= pivot
    	if(array[a].compareTo(pivot) <= 0) {
    		a++;
      // Move b backward if array[b] > pivot
    	} else if(array[b].compareTo(pivot) > 0) {
    		b--;
    	} else {
      // Otherwise swap array[a] and array[b]
    		swap(a,b);
    	}
    }
    
    swap(left, b);
    
    sort(left, b-1);
    sort(b+1, right);
  }
}

class MergeSort<E extends Comparable<E>>
  implements Sorter<E> {
  
  private E[] array, array2;
  
  public void sort (E[] array) {
    this.array = array;
    array2 = array.clone();
    sort(0, array.length-1);
  }
  
  private void sort(int left, int right) {
    if (left >= right)
      return;
    
    int middle = (left + right) / 2;
    sort(left, middle);
    sort(middle+1, right);
    
    int i = left;
    int a = left;
    int b = middle+1;
    while (a <= middle || b <= right) {
      // If both a <= middle and b <= right
     // copy the smaller of array[a] or array[b] to array2[i] 
    	if(a > middle) {
    		array2[i] = array[b];
    		b++;
    		i++;
    	} else if(b > right) {
    		array2[i] = array[a];
    		a++;
    		i++;
    	} else if(array[a].compareTo(array[b]) >= 0) {
    			array2[i] = array[b];
    			b++;
    			i++;
    		} else {   			
    			array2[i] = array[a];
    			a++;
    			i++;
    		}
      // Otherwise just copy the remaining elements to array2
    }
    System.arraycopy(array2, left, array, left, right - left + 1);
  
}
}
