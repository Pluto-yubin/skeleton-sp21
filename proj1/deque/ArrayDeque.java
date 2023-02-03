package deque;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * @package: deque
 * @className: ArrayDeque
 * @author: zyb
 * @description: cs61b 2021 proj1
 * @date: 2023/1/20 14:26
 * @version: 1.0
 */
public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;

    private int nextFirst, nextLast;

    private int size;

    private int getIndex(int index) {
        return (index + items.length) % items.length;
    }

    private void doResize(int newSize) {
        // initialize array
        if (items == null) {
            items = (T[]) new Object[newSize];
            nextLast = 1;
            return;
        }

        // point to first element
//        int index = getIndex(nextFirst + 1);
        int index = (nextFirst + 1) % newSize;
        T[] newItems = (T[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newItems[(index + i) % newItems.length] = items[getIndex(index + i)];
        }

        nextLast = (items.length + index) % newSize;
        items = newItems;

    }

    private void resize() {
        if (items == null) {
            doResize(1);
        } else if (items.length == size) {
            doResize(items.length * 2);
        } else if (size < items.length / 4) {
            doResize(items.length / 2);
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public void addFirst(T item) {
        resize();
        items[getIndex(nextFirst)] = item;
        nextFirst = getIndex(nextFirst - 1);
        size += 1;

    }

    @Override
    public void addLast(T item) {
        resize();
        items[getIndex(nextLast)] = item;
        nextLast = getIndex(nextLast + 1);
        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int index = nextFirst + 1;
        for (int i = 0; i < size; i++) {
            System.out.print(items[getIndex(i + index)]);
            System.out.print(" ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size > 0) {
            resize();
            nextFirst = getIndex(nextFirst + 1);
            T item = items[nextFirst];
            items[nextFirst] = null;
            size -= 1;
            return item;
        }

        return null;
    }

    @Override
    public T removeLast() {
        if (size > 0) {
            resize();
            nextLast = getIndex(nextLast - 1);
            T item = items[nextLast];
            items[nextLast] = null;
            size -= 1;
            return item;
        }

        return null;
    }

    @Override
    public T get(int index) {
        return items[getIndex(nextFirst + 1 + index)];
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


    private class ArrayDequeIterator implements Iterator<T> {
        int index = nextFirst + 1;

        @Override
        public boolean hasNext() {
            return size == 0;
        }

        @Override
        public T next() {
            return items[getIndex(index++)];
        }
    }

}
