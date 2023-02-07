package deque;

import org.junit.Test;

/**
 * @package: deque
 * @className: ArrayDequeTest
 * @author: zyb
 * @description: TODO
 * @date: 2023/2/8 00:20
 * @version: 1.0
 */
public class ArrayDequeTest {
    @Test
    public void testArrayDeque() {
        ArrayDeque<Integer> arrayDeque = new ArrayDeque<>();
        arrayDeque.addFirst(0);
        System.out.println(arrayDeque.removeFirst());
        arrayDeque.addFirst(2);
        arrayDeque.addLast(3);
        arrayDeque.addFirst(4);
        arrayDeque.addLast(5);
        System.out.println(arrayDeque.get(3));
        arrayDeque.addLast(7);
        System.out.println(arrayDeque.get(3));
        arrayDeque.addFirst(9);
        arrayDeque.addFirst(10);
        arrayDeque.addFirst(11);
        arrayDeque.addLast(12);
        System.out.println(arrayDeque.removeLast());
        System.out.println(arrayDeque.removeLast());
        System.out.println(arrayDeque.removeLast());
        System.out.println(arrayDeque.removeLast());
        System.out.println(arrayDeque.removeFirst());
        System.out.println(arrayDeque.removeFirst());
        System.out.println(arrayDeque.removeLast());
    }
}