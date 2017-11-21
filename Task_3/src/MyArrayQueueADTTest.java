public class MyArrayQueueADTTest {
    public static void fill(ArrayQueueADT queue) {
        for (int i = 0; i < 10; i++) {
            queue.enqueue(queue, i);
        }
    }

    public static void dump(ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println(queue.size(queue) + " " +
                    queue.peek(queue) + " " + queue.remove(queue) + queue.element(queue));
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT stack = new ArrayQueueADT();
        fill(stack);
        dump(stack);
    }
}
