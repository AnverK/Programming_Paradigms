public class ArrayQueue extends AbstractQueue implements Queue {
    private int left = 0, right = 0;
    private Object[] elements = new Object[10];

    private int inc(int x) {
        return (x + 1) % elements.length;
    }

    protected void pop_front() {
        elements[left] = null;
        left = inc(left);
    }

    protected void pushImpl(Object e) {
        ensureCapacity(size + 1);
        elements[right] = e;
        right = inc(right);
    }

    protected Object peekImpl() {
        return elements[left];
    }

    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        }
        Object[] newElements = new Object[2 * capacity];
        System.arraycopy(elements, left, newElements, 0, elements.length - left);
        System.arraycopy(elements, 0, newElements, elements.length - left, right);
        elements = newElements;
        left = 0;
        right = size;
    }

    protected void clearImpl() {
        left = 0;
        right = 0;
        elements = new Object[10];
    }

    protected ArrayQueue copyQueue() {
        ArrayQueue queue = new ArrayQueue();
        queue.left = left;
        queue.right = right;
        queue.size = size;
        queue.elements = new Object[elements.length];
        System.arraycopy(elements, 0, queue.elements, 0, elements.length);
        return queue;
    }
}
