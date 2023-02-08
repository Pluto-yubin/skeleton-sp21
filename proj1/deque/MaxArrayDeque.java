package deque;

import java.util.Comparator;

/**
 * @package: deque
 * @className: MaxArrayDeque
 * @author: zyb
 * @description: TODO
 * @date: 2023/2/7 23:17
 * @version: 1.0
 */
public class MaxArrayDeque<T> extends ArrayDeque<T> {
    Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public MaxArrayDeque() {}

    public T max(Comparator<T> comparator) {
        if (isEmpty()) {
            return null;
        }

        T result = get(0);
        for (int i = 0; i < size(); i++) {
            if (comparator.compare(result, get(i)) < 0) {
                result = get(i);
            }
        }
        return result;
    }

    public T max() {
        return max(comparator);
    }


}