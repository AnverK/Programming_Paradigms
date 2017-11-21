public class LinkedQueue extends AbstractQueue implements Queue {
    private Node head, tail;

    protected void pop_front() {
        head = head.next;
    }

    protected void pushImpl(Object e) {
        Node element = tail;
        tail = new Node(null, e);
        if (size == 0) {
            head = tail;
        } else {
            element.next = tail;
        }
    }

    protected Object peekImpl() {
        return head.value;
    }

    protected void clearImpl() {
        head = null;
        tail = null;
    }

    protected LinkedQueue copyQueue() {
        LinkedQueue queue = new LinkedQueue();
        Node node = head;
        while (node != null) {
            queue.enqueue(node.value);
            node = node.next;
        }
        return queue;
    }

    private class Node {
        private Node next;
        private Object value;

        public Node(Node next, Object value) {
            assert value != null : "value is null";
            this.next = next;
            this.value = value;
        }
    }
}
