package Arrays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by leiwang on 4/15/18.
 */
public class MedianDataStream {
    public static void main(String args[]) {

    }

    /**
     * Time O(nlog(n))
     * Space O(n)
     */
    private class MedianFinder {
        List<Integer> list = new ArrayList<>();

        public void addNum(int num) {
            list.add(num);
        }
        public double findMedian() {
            return list.size() % 2 == 0 ? (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2 : list.get(list.size() / 2);
        }
    }

    /**
     * Time O(n)
     * Space O(n)
     */
    private class MedianFinder2 {
        List<Integer> list = new ArrayList<>();

        public void addNum(int num) {
            Collections.sort(list);
            list.add(Collections.binarySearch(list,num), num);
        }

        public double findMedian() {
            return list.size() % 2 == 0 ? (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2 : list.get(list.size() / 2);
        }
    }

    /**
     * Time O(log(n))
     * Space O(n)
     */
    private class MedianFinder3 {
        List<Integer> list = new ArrayList<>();
        // max queue is always greater than or equal to min queue
        PriorityQueue<Integer> low = new PriorityQueue<>();
        PriorityQueue<Integer> high = new PriorityQueue<>(1000, Collections.reverseOrder());
        public void addNum(int num) {
            high.offer(num);
            low.offer(high.poll());
            if (high.size() < low.size()) {
                high.offer(low.poll());
            }
        }

        public int findMedian() {
            if (high.size() == low.size()) {
                return (high.peek() + low.peek()) / 2;
            } else {
                return high.peek();
            }
        }
    }

    class MedianFinderBST {
        class TreeNode{
            int val;
            TreeNode parent,left,right;
            TreeNode(int val, TreeNode p){
                this.val=val;
                this.parent=p;
                left=null;
                right=null;
            }
            void add(int num){
                if(num>=val){
                    if(right==null)
                        right=new TreeNode(num,this);
                    else
                        right.add(num);
                }else{
                    if(left==null)
                        left=new TreeNode(num,this);
                    else
                        left.add(num);
                }
            }
            TreeNode next(){
                TreeNode ret;
                if(right!=null){
                    ret=right;
                    while(ret.left!=null)
                        ret=ret.left;
                }else{
                    ret=this;
                    while(ret.parent.right==ret)
                        ret=ret.parent;
                    ret=ret.parent;
                }
                return ret;
            }
            TreeNode prev(){
                TreeNode ret;
                if(left!=null){
                    ret=left;
                    while(ret.right!=null)
                        ret=ret.right;
                }else{
                    ret=this;
                    while(ret.parent.left==ret)
                        ret=ret.parent;
                    ret=ret.parent;
                }
                return ret;
            }
        }
        int n;
        TreeNode root, curr;
        // Adds a number into the data structure.
    public void addNum(int num) {
        if(root==null){
            root = new TreeNode(num,null);
            curr=root;
            n=1;
        }else{
            root.add(num);
            n++;
            if(n%2==1){
                if(curr.val<=num)
                    curr=curr.next();
            }else
            if(curr.val>num)
                curr=curr.prev();
        }
    }

    // Returns the median of current data stream
    public double findMedian() {
        if(n%2==0){
            return ((double)curr.next().val+curr.val)/2;
        }else
            return curr.val;
    }
};
}
