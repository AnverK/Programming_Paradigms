public class MyArrayQueueModuleTest {
    public static void fill(ArrayQueueModule queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }
    }

    public static void dump(ArrayQueueModule queue) {
        while (!queue.isEmpty()) {
            System.out.println(queue.size() + " " +
                    queue.peek() + " " + queue.remove() + " " + queue.element());
        }
    }

    public static void main(String[] args) {
        ArrayQueueModule stack = new ArrayQueueModule();
        fill(stack);
        dump(stack);
    }
}
