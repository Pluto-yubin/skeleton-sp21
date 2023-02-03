package deque;

import java.util.Iterator;
import java.util.Objects;

/**
 * @package: deque
 * @className: LinkedListDeque
 * @author: zyb
 * @description: TODO
 * @date: 2023/1/20 14:26
 * @version: 1.0
 */
public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {

    Node sentinel;
    int size;

    public LinkedListDeque() {
        sentinel = new Node(null);
        sentinel.before = sentinel;
        sentinel.next = sentinel;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    private class Node {
        T value;
        Node before;
        Node next;

        Node(T value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof LinkedListDeque)) {
                return false;
            }

            Node node = (Node) o;
            return Objects.equals(value, node.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, before, next);
        }
    }

    @Override
    public void addFirst(T item) {
        Node node = new Node(item);
        node.before = sentinel;
        node.next = sentinel.next;
        sentinel.next.before = node;
        sentinel.next = node;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node node = new Node(item);
        node.next = sentinel;
        node.before = sentinel.before;
        sentinel.before.next = node;
        sentinel.before = node;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return Math.max(size, 0);
    }

    @Override
    public void printDeque() {
        Node temp = sentinel.next;
        while (!temp.next.equals(sentinel)) {
            System.out.print(temp.value);
            System.out.print(" ");
            temp = temp.next;
        }
        System.out.println(temp.value);
    }

    @Override
    public T removeFirst() {
        Node first = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.before = sentinel;
        size -= 1;
        return first.value;
    }

    @Override
    public T removeLast() {
        Node last = sentinel.before;
        sentinel.before = sentinel.before.before;
        sentinel.before.next = sentinel;
        size -= 1;
        return last.value;
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index <= size) {
            Node temp = sentinel;
            while (index > 0) {
                temp = temp.next;
                index -= 1;
            }
            return temp.value;
        }
        return null;
    }

    private T getRecursiveHelper(int index, Node node) {
        if (index > 0) {
            return getRecursiveHelper(index - 1, node.next);
        }
        return node.value;
    }
    public T getRecursive(int index) {
        return getRecursiveHelper(index, sentinel);
    }

    private boolean isEqual(Deque<?> deque) {
        if (size() == deque.size()) {
            for (int i = 0; i < size; i++) {
                if (!get(i).equals(deque.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }

        if (o instanceof LinkedListDeque) {
            LinkedListDeque<?> that = (LinkedListDeque<?>) o;
            return isEqual(that);

        } else if (o instanceof ArrayDeque) {
            ArrayDeque<?> that = (ArrayDeque<?>) o;
            return isEqual(that);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentinel, size);
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node temp;

        LinkedListDequeIterator() {
            temp = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return !temp.equals(sentinel);
        }

        @Override
        public T next() {
            T value = temp.value;
            temp = temp.next;
            return value;
        }
    }

    public static void main(String[] args) {
        LinkedListDeque<Integer> integers = new LinkedListDeque<>();
        LinkedListDeque<Integer> integers1 = new LinkedListDeque<>();
        integers.equals(new ArrayDeque<Integer>());
        System.out.println(integers.equals(integers1));

    }
}
