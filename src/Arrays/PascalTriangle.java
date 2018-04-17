package Arrays;

//Given an index k, return the kth row of the Pascal's triangle.
//
//        For example, given k = 3,
//        Return [1,3,3,1].
//
//        Note:
//        Could you optimize your algorithm to use only O(k) extra space?
//           1
//          1 1
//         1 2 1
//        1 3 3 1
//       1 4 6 4 1
import java.util.ArrayList;
import java.util.List;

public class PascalTriangle {
    public static void main(String args[]) {

    }

    private List<Integer> getRow(int rowIndex) {
        List<Integer> ret = new ArrayList<Integer>();
        ret.add(1);
        for (int i = 1; i <= rowIndex; i++) {
            for (int j = i - 1; j >= 1; j--) {
                int tmp = ret.get(j - 1) + ret.get(j);
                ret.set(j, tmp);
            }
            ret.add(1);
        }
        return ret;
    }

    private List<Integer> getRow2(int rowIndex) {
        List<Integer> list = new ArrayList<Integer>();
        if (rowIndex < 0)
            return list;

        for (int i = 0; i < rowIndex + 1; i++) {
            list.add(0, 1);
            for (int j = 1; j < list.size() - 1; j++) {
                list.set(j, list.get(j) + list.get(j + 1));
            }
        }
        return list;
    }

    private List<Integer> getRow3(int rowIndex) {
        List<Integer> res = new ArrayList<Integer>();
        for(int i = 0;i<rowIndex+1;i++) {
            res.add(1);
            for(int j=i-1;j>0;j--) {
                res.set(j, res.get(j-1)+res.get(j));
            }
        }
        return res;
    }
}
