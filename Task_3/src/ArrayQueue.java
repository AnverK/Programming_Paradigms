import java.util.Objects;
import java.util.Queue;

public class ArrayQueue {
    private int size = 0, left = 0, right = 0;
    private Object[] elements = new Object[10];

    // ] n = elements.length (just designation)

    // pre: elements.length > 0
    // post: x' = (x + 1) % n
    private int add(int x) {
        return add(x, 1);
    }

    // pre: elements.length > 0
    // post: x' = (x + i) % n
    private int add(int x, int i) {
        return (x + i) % elements.length;
    }

    // pre: elements.length > 0
    // post: x' = (x + n - i) % n
    private int dec(int x, int i) {
        return (x + elements.length - i) % elements.length;
    }

    // pre: elements.length > 0
    // post: x' = (x + n - 1) % n
    private int dec(int x) {
        return dec(x, 1);
    }

    // pre: true
    // post: size' = size + 1
    // if (size < n) right' = add(right) && left' = left
    // else left' = 0 && right' = size' && n *= 2
    // elements'[add(left', k)] = elements[add(left, k)] for each k: 0 <= k < size
    public void enqueue(Object element) {
        ensureCapacity(size + 1);
        elements[right] = element;
        right = add(right);
        size++;
    }

    // pre: true
    // post: if(capacity <= n) all parametres save their condition
    // else n *= 2 && elements'[k] = elements[add(left, k)] for each k: 0 <= k < size && left' = 0 && right' = size
    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }
        elements = toArray(2 * capacity);
        left = 0;
        right = size;
    }

    // pre: size > 0
    // post: size' = size && Result = elements[left] && elements' = elements
    public Object element() {
        assert size > 0;
        return elements[left];
    }

    // pre: size > 0
    // post: size' = size - 1 && Result = elements[left] && left' = add(left)
    // for each k: 1 <= k < size: elements'[add(left, k)] = elements[add(left, k)]
    public Object dequeue() {
        Object value = element();
        elements[left] = null;
        left = add(left);
        size--;
        return value;
    }

    // pre: true
    // post: Result = size && size' = size && elements' = elements && left' = left && right' = right
    public int size() {
        return size;
    }

    // pre: true
    // post: Result = (size == 0) && size' = size && elements' = elements && left' = left && right' = right
    public boolean isEmpty() {
        return size == 0;
    }

    // pre: true
    // post: size' = 0 && elements'[i] = null for each i: 0 <= i < n && left = 0 && right = 0
    public void clear() {
        left = 0;
        right = 0;
        size = 0;
        elements = new Object[10];
    }

    // pre: capacity >= n;
    // post: newElements[i] = elements[dec(left, i)] for each i: 0 <= i < size
    private Object[] toArray(int capacity) {
        Object[] newElements = new Object[capacity];
        System.arraycopy(elements, left, newElements, 0, elements.length - left);
        System.arraycopy(elements, 0, newElements, elements.length - left, right);
//        for (int i = 0; i < size; i++) {
//            newElements[i] = elements[add(left, i)];
//        }
        return newElements;
    }

//    // pre: true
//    // post: newElements[i] = elements[add(left, i)] for each i: 0 <= i < size
//    public Object[] toArray() {
//        return toArray(size);
//    }

    // pre: size > 0
    // post: size' = size && Result = elements[dec(right)] && elements' = elements
    public Object peek() {
        assert size > 0;
        return elements[dec(right)];
    }

    // pre: size > 0
    // post: size' = size - 1 && right' = dec(right) && Result = elements[right']
    // for each k: 0 <= k < size': elements'[add(left, k)] = elements[add(left, k)]
    public Object remove() {
        Object value = peek();
        right = dec(right);
        elements[right] = null;
        size--;
        return value;
    }

    // pre: size > 0
    // post: size' = size + 1 && left' = dec(left) && elements[left'] = element
    // for each k: 0 <= k < size: elements'[add(left, k)] = elements[add(left, k)]
    public void push(Object element) {
        ensureCapacity(size + 1);
        left = dec(left);
        elements[left] = element;
        size++;
    }
}
