import prog08.Heap;

public class Main {
  public static void main (String[] args) {
    Heap heap = new Heap<Integer>();
    heap.offer(3);
    heap.offer(1);
    heap.offer(4);
    heap.offer(1);
    heap.offer(4);
    heap.offer(9);
    heap.offer(2);
    heap.offer(6);

    while (!heap.isEmpty())
      System.out.print(heap.poll() + " ");
    System.out.println();
  }
}

