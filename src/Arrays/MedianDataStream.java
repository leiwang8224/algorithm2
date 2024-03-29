package Arrays;

import java.util.*;

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

        medianFinder2.addNum(1);
        System.out.println("median2 = " + medianFinder2.findMedian());
        medianFinder2.addNum(10);
        medianFinder2.addNum(100);
        medianFinder2.addNum(23);
        medianFinder2.addNum(3);
        System.out.println("median2 = " + medianFinder2.findMedian());
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
            Collections.sort(list);
            return list.size() % 2 == 0 ?
                    // take average of the two center points
                    (list.get(list.size() / 2 - 1) + list.get(list.size() / 2)) / 2 :
                    // take the midpoint
                    list.get(list.size() / 2);
        }
    }

    private double[] medianSlidingWindow(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        int j = 0;
        PriorityQueue<Integer> lo = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> hi = new PriorityQueue<>();

        for (int i = 0; i < nums.length; i++) {
            lo.add(nums[i]);
            hi.add(lo.poll());
            if(hi.size()>lo.size()) lo.add(hi.poll());
            if (lo.size() + hi.size() == k) {
                result[j]=lo.size()==hi.size()?(double)((long) lo.peek()+(long)hi.peek())/2:(double) lo.peek();
                if (!lo.remove(nums[j])) hi.remove(nums[j]);
                j++;
            }
        }
        return result;
    }

    private double[] medianSlidingWindowTreeSet(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        int start = 0;

        TreeSet<Integer> lo = new TreeSet<>((a, b) -> (nums[a] == nums[b] ? (a - b) :
                                                        Integer.compare(nums[a], nums[b])));
        TreeSet<Integer> hi = new TreeSet<>((a, b) -> (nums[a] == nums[b] ? (a - b) :
                                                        Integer.compare(nums[a], nums[b])));

        for (int i = 0; i < nums.length; i++) {
            lo.add(i);
            hi.add(lo.pollLast());
            if (lo.size()< hi.size()) lo.add(hi.pollFirst());
            if (lo.size() + hi.size() == k) {
                result[start]=lo.size()==hi.size()? nums[lo.last()]/2.0+ nums[hi.first()]/2.0: nums[lo.last()]/1.0;
                if (!lo.remove(start)) hi.remove(start);
                start++;
            }
        }
        return result;
    }

    /**
     * Time O(n)
     * Space O(n)
     * Use Binary search to find the index to insert the new value
     * so that it's always sorted after insertion
     */
    private static class MedianFinder2 {
        List<Integer> list = new ArrayList<>();

        public void addNum(int num) {
            list.add(num);
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
     * 2 heaps:
     * Max heap (lower half): has the max element at parent node in the tree
     * Min heap (higher half): has the min element at the parent node in the tree
     * Max heap:
     *      10 <- max element
     *     /  \
     *    8   9
     *   / \
     *  6  7
     * Min heap:
     *      11 <- min element
     *     /  \
     *    12  13
     *   / \
     *  14 15
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

    /**
     * Find median using BST
     */
    static class MedianFinderBST {
        /**
         * TreeNode keeps track of parent, left, right nodes
         */
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
                    // traverse to the right then all the way left
                    // finds the next greatest value
                    ret=right;
                    while(ret.left!=null)
                        ret=ret.left;
                }else{
                    // right node does not exist
                    // traverse to the parent node to find
                    // the next greatest value
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
                    // prev is the value that is less than the
                    // current value
                    // traverse left then all the way to right
                    ret=left;
                    while(ret.right!=null)
                        ret=ret.right;
                }else{
                    // left node is null so traverse up parent
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
