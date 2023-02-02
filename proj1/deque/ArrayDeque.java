package deque;

import jh61b.junit.In;

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
    T[] items;

    int nextFirst, nextLast;

    int size;

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
        int index = getIndex(nextFirst + 1);
        T[] newItems = (T[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            newItems[(index + i) % newItems.length] = items[getIndex(index + i)];
        }
        // stay nextFirst location, move nextLast
        nextLast = (nextLast + items.length) % newSize;
        items = newItems;

    }

    private void resize() {
        if (items == null) {
            doResize(8);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArrayDeque<?> that = (ArrayDeque<?>) o;
        return nextFirst == that.nextFirst && nextLast == that.nextLast
                && size == that.size && Arrays.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(nextFirst, nextLast, size);
        result = 31 * result + Arrays.hashCode(items);
        return result;
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
