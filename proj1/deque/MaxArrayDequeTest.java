package deque;

import org.junit.Assert;
import org.junit.Test;

import java.util.Comparator;

/**
 * @package: deque
 * @className: MaxArrayDequeTest
 * @author: zyb
 * @description: TODO
 * @date: 2023/2/7 23:35
 * @version: 1.0
 */
public class MaxArrayDequeTest {
    @Test
    public void testMax() {
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        deque.addFirst(1);
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        deque.addFirst(5);
        Assert.assertEquals((long) 5, (long) deque.max());
    }
}