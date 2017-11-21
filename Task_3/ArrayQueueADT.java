import java.util.Objects;

public class ArrayQueueADT {
    private static int size = 0, left = 0, right = 0;
    private static Object[] elements = new Object[10];

    // ] n = elements.length (just designation)

    // pre: elements.length > 0
    // post: x' = (x + 1) % n
    private int add(ArrayQueueADT queue, int x) {
        return queue.add(queue, x, 1);
    }

    // pre: elements.length > 0 && queue != null
    // post: x' = (x + i) % n
    private static int add(ArrayQueueADT queue, int x, int i) {
        return (x + i) % queue.elements.length;
    }

    // pre: elements.length > 0 && queue != null
    // post: x' = (x + n - i) % n
    private static int dec(ArrayQueueADT queue, int x, int i) {
        return (x + queue.elements.length - i) % queue.elements.length;
    }

    // pre: elements.length > 0 && queue != null
    // post: x' = (x + n - 1) % n
    private static int dec(ArrayQueueADT queue, int x) {
        return queue.dec(queue, x, 1);
    }

    // pre: queue != null
    // post: size' = size + 1
    // if (size < n) right' = add(right) && left' = left
    // else left' = 0 && right' = size' && n *= 2
    // elements'[add(left', k)] = elements[add(left, k)] for each k: 0 <= k < size
    public static void enqueue(ArrayQueueADT queue, Object element) {
        ensureCapacity(queue, queue.size + 1);
        queue.elements[right] = element;
        queue.right = queue.add(queue, queue.right);
        queue.size++;
    }

    // pre: queue != null
    // post: if(capacity <= n) all parametres save their condition
    // else n *= 2 && elements'[k] = elements[add(left, k)] for each k: 0 <= k < size && left' = 0 && right' = size
    private static void ensureCapacity(ArrayQueueADT queue, int capacity) {
        if (capacity <= queue.elements.length) {
            return;
        }
        queue.elements = queue.toArray(queue, 2 * capacity);
        queue.left = 0;
        queue.right = queue.size;
    }

    // pre: size > 0 && queue != null
    // post: size' = size && Result = elements[left] && elements' = elements
    public static Object element(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[queue.left];
    }

    // pre: size > 0 && queue != null
    // post: size' = size - 1 && Result = elements[left] && left' = add(left)
    // for each k: 1 <= k < size: elements'[add(left, k)] = elements[add(left, k)]
    public static Object dequeue(ArrayQueueADT queue) {
        Object value = queue.element(queue);
        queue.elements[queue.left] = null;
        queue.left = queue.add(queue, queue.left);
        queue.size--;
        return value;
    }

    // pre: queue != null
    // post: Result = size && size' = size && elements' = elements && left' = left && right' = right
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }

    // pre: queue != null
    // post: Result = (size == 0) && size' = size && elements' = elements && left' = left && right' = right
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }

    // pre: queue != null
    // post: size' = 0 && elements'[i] = null for each i: 0 <= i < n && left = 0 && right = 0
    public static void clear(ArrayQueueADT queue) {
        queue.left = 0;
        queue.right = 0;
        queue.size = 0;
        queue.elements = new Object[10];
    }

    // pre: capacity >= n && queue != null
    // post: newElements[i] = elements[dec(left, i)] for each i: 0 <= i < size
    private static Object[] toArray(ArrayQueueADT queue, int capacity) {
        Object[] newElements = new Object[capacity];
        System.arraycopy(queue.elements, queue.left, newElements, 0, queue.elements.length - queue.left);
        System.arraycopy(queue.elements, 0, newElements, queue.elements.length - queue.left, queue.right);
//        for (int i = 0; i < size; i++) {
//            newElements[i] = elements[add(left, i)];
//        }
        return newElements;
    }

//    // pre: true
//    // post: newElements[i] = elements[add(left, i)] for each i: 0 <= i < size
//    public static Object[] toArray() {
//        return toArray(size);
//    }

    // pre: size > 0 && queue != null
    // post: size' = size && Result = elements[dec(right)] && elements' = elements
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[dec(queue, queue.right)];
    }

    // pre: size > 0 && queue != null
    // post: size' = size - 1 && right' = dec(right) && Result = elements[right']
    // for each k: 0 <= k < size': elements'[add(left, k)] = elements[add(left, k)]
    public static Object remove(ArrayQueueADT queue) {
        Object value = queue.peek(queue);
        queue.right = dec(queue, queue.right);
        queue.elements[queue.right] = null;
        queue.size--;
        return value;
    }

    // pre: size > 0 && queue != null
    // post: size' = size + 1 && left' = dec(left) && elements[left'] = element
    // for each k: 0 <= k < size: elements'[add(left, k)] = elements[add(left, k)]
    public static void push(ArrayQueueADT queue, Object element) {
        queue.ensureCapacity(queue, queue.size + 1);
        queue.left = queue.dec(queue, queue.left);
        queue.elements[queue.left] = element;
        queue.size++;
    }
}
