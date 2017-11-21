public class MyArrayQueueTest {
    public static void fill(ArrayQueue queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " +
                    queue.peek() + " " + queue.remove() + " " + queue.element());
        }
    }

    public static void main(String[] args) {
        ArrayQueue stack = new ArrayQueue();
        fill(stack);
        dump(stack);
    }
}
