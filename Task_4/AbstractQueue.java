import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractQueue implements Queue {
    protected int size = 0;

    public Object dequeue() {
        Object result = element();
        pop_front();
        size--;
        return result;
    }

    protected abstract void pop_front();

    public Object element() {
        assert size > 0 : "queue is empty";
        return peekImpl();
    }

    protected abstract Object peekImpl();

    public void enqueue(Object e) {
        pushImpl(e);
        size++;
    }

    protected abstract void pushImpl(Object e);

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    protected abstract void clearImpl();

    public void clear() {
        size = 0;
        clearImpl();
    }

    protected abstract AbstractQueue copyQueue();

    public AbstractQueue filter(Predicate<Object> predicate) {
        AbstractQueue queue = copyQueue();
        int size = queue.size;
        for (int i = 0; i < size; i++) {
            Object element = queue.dequeue();
            if (predicate.test(element)) {
                queue.enqueue(element);
            }
        }
        return queue;
    }

    public AbstractQueue map(Function<Object, Object> function) {
        AbstractQueue queue = copyQueue();
        int size = queue.size;
        for (int i = 0; i < size; i++) {
            Object element = queue.dequeue();
            queue.enqueue(function.apply(element));
        }
        return queue;
    }
}
