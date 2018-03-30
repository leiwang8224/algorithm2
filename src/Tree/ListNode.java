package Tree;

/**
 * Created by leiwang on 3/13/18.
 */
public class ListNode {
    private int val;
    public ListNode left;
    public ListNode right;

    public ListNode(int valInput) {
        val = valInput;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
