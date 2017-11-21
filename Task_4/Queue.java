import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {
    // inv: size >= 0 && queue[i] != null for 0 <= i < size (all elements)

    // pre: element != null
    // post: queue.size' = queue.size + 1 && element is in the end of the queue && the other part of queue is not changed
    void enqueue(Object element);

    // pre: queue.size > 0
    // post: Result = the first element in the queue
    Object element();

    // pre: queue.size > 0
    // post: Result = element() && queue.size' = queue.size - 1 && the first element of the queue is removed
    Object dequeue();

    // pre: true
    // post: Result = queue.size
    int size();

    // pre: true
    // post: Result = (size() == 0)
    boolean isEmpty();

    // pre: true
    // post: queue.size' = 0 && all queue elements are removed
    void clear();

    // pre: true
    // post: R = queue': queue' contains those only elements from queue which match the predicate
    // the order of the queue' elements is the same as in the queue
    Object filter(Predicate<Object> predicate);

    // pre: true
    // post: R = queue': queue'.size == queue.size && each of queue' element = function(queue element)
    // the order of the function(element) in queue' is the same as for the queue elements
    Object map(Function<Object, Object>  function);

}
