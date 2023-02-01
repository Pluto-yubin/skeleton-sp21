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

        public Node(T value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(value, node.value) && Objects.equals(before, node.before) && Objects.equals(next, node.next);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedListDeque<?> that = (LinkedListDeque<?>) o;
        return size == that.size && Objects.equals(sentinel, that.sentinel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sentinel, size);
    }

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node temp;

        public LinkedListDequeIterator() {
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
}