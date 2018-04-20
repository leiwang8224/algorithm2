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
        MedianFinder medianFinder = new MedianFinder();
        MedianFinder2 medianFinder2 = new MedianFinder2();
        MedianFinderBST medianFinderBST = new MedianFinderBST();

        medianFinder.addNum(1);
        System.out.println("median = " + medianFinder.findMedian());
        medianFinder.addNum(10);
        medianFinder.addNum(100);
        medianFinder.addNum(23);
        medianFinder.addNum(3);
        System.out.println("median = " + medianFinder.findMedian());

//        medianFinder2.addNum(1);
//        System.out.println("median2 = " + medianFinder2.findMedian());
//        medianFinder2.addNum(10);
//        medianFinder2.addNum(100);
//        medianFinder2.addNum(23);
//        medianFinder2.addNum(3);
//        System.out.println("median2 = " + medianFinder2.findMedian());
        MedianFinder3 medianFinder3 = new MedianFinder3();

        medianFinder3.addNum(1);
        System.out.println("medianPriorityQueue = " + medianFinder3.findMedian());
        medianFinder3.addNum(10);
        medianFinder3.addNum(100);
        medianFinder3.addNum(23);
        medianFinder3.addNum(3);
        System.out.println("medianPriorityQueue = " + medianFinder3.findMedian());

        medianFinderBST.addNum(1);
        System.out.println("medianBST = " + medianFinderBST.findMedian());
        medianFinderBST.addNum(10);
        medianFinderBST.addNum(100);
        medianFinderBST.addNum(23);
        medianFinderBST.addNum(3);
        System.out.println("medianBST = " + medianFinderBST.findMedian());
    }

    /**
     * Time O(nlog(n))
     * Space O(n)
     */
    private static class MedianFinder {
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
    private static class MedianFinder2 {
        List<Integer> list = new ArrayList<>();

        public void addNum(int num) {
            Collections.sort(list);
            list.add(Collections.binarySearch(list,num), num);
        }

        public double findMedian() {
            return list.size() % 2 == 0 ?
                    (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2 :
                    list.get(list.size() / 2);
        }
    }

    /**
     * Time O(log(n))
     * Space O(n)
     */
    private static class MedianFinder3 {
        MedianFinder3() {
            System.out.println();
            System.out.println("starting PriorityQueue method");
        }
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
            System.out.println("after addNum high = " + high.toString() + " low = " + low.toString());
        }

        public int findMedian() {
            System.out.println("current high = " + high.toString());
            System.out.println("current low = " + low.toString());

            if (high.size() == low.size()) {
                System.out.println("high.size = low.size");
                return (high.peek() + low.peek()) / 2;
            } else {
                System.out.println("high.size != low.size");
                return high.peek();
            }
        }
    }

    static class MedianFinderBST {
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
