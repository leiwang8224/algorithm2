package Hash;

import java.util.HashMap;
import java.util.List;

/**
 * Created by leiwang on 4/5/18.
 */

/**
 * Given:
 * v1 = [1, 2]
 * v2 = [3, 4, 5, 6]
 * by calling next repeatedly until hasNext returns false,
 * the order of elements returned by next should be:
 * [1, 3, 2, 4, 5, 6]
 */
public class ZigzagIterator {
    private HashMap<Integer, List<Integer>> map;
    private int length = 0;
    private int size = 0;
    private int index = 0;

    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        map = new HashMap<Integer, List<Integer>>();
        if (v1 != null && v1.size() > 0) {
            map.put(size, v1);
            length += v1.size();
            size ++;
        }

        if (v2 != null && v2.size() > 0) {
            map.put(size, v2);
            length += v2.size();
            size ++;
        }

        if (length == 0) {
            return;
        }
    }

    public int next() {
        while (map.get(index).size() == 0) {
            index ++;
            if (index == size) {
                index = 0;
            }
        }

        int rst = map.get(index).get(0);
        map.get(index).remove(0);
        length --;
        index ++;
        if (index == size) {
            index = 0;
        }
        return rst;
    }

    public boolean hasNext() {
        return length > 0;
    }
}
