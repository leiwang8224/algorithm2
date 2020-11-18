package DP;

//LC-673
public class NumberLongestIncSubsequence {
    public static void main(String[] args) {
        System.out.println(findNumberOfLIS(new int[]{1,3,5,4,7}));
        System.out.println(findNumberOfLIS2(new int[]{1,3,5,4,7}));
        System.out.println(findNumberOfLIS(new int[]{2,2,2,2,2}));
        System.out.println(findNumberOfLIS2(new int[]{2,2,2,2,2}));

    }

    // time O(N^2)
    // space O(N)
//
// for each element in the array or on in the tree, they all carry three fields :
//            1) the maximum increasing / decreasing length ends at the current element,
//            2) its own value ,
//            3) the total number of maximum length,
//    and each time when we visit a element, we will use its 2) to update 1) and 3),
//    the only difference is for array we use iteration while for tree we use recursion......
//    Also, for substring problem, we usually use only one for loop because for each index,
//    we only care about the relationship between its two neighbors, while for subsequence
//    problem, we use two for loops , because for each index, any other indexes can do something...
    // keep track of the length of the longest sequence
    // and the count of such sequences with the length
    public static int findNumberOfLIS(int[] nums) {
        int numsLength = nums.length;
        if (numsLength <= 1) return numsLength;
        // lengths[i] = length of longest ending in nums[i]
        int[] lengthsLongestEndingInI = new int[numsLength];
        //count[i] = number of longest ending in nums[i]
        int[] numberOfLongestEndingInI_DP = new int[numsLength];
        // initially there is 1 LIS and the number of LIS is 1
        // because it's always LIS up to index i plus 1
        java.util.Arrays.fill(numberOfLongestEndingInI_DP, 1);
        java.util.Arrays.fill(lengthsLongestEndingInI, 1);

        int longestLen = 1;
        for (int index = 1; index < numsLength; index++) {
            for (int indexUpTo = 0; indexUpTo < index; indexUpTo++) {
                if (nums[index] > nums[indexUpTo]) {
                    // check for two things
                    // - the element at index after is greater than the element at index before
                    // - the element at index after is equal to the element at index before
                    if (lengthsLongestEndingInI[indexUpTo] + 1 > lengthsLongestEndingInI[index]) {
                        //since greater we replace the element at index with the larger number
                        lengthsLongestEndingInI[index] = lengthsLongestEndingInI[indexUpTo] + 1;
                        //update the number of LIS
                        numberOfLongestEndingInI_DP[index] = numberOfLongestEndingInI_DP[indexUpTo];
                        // this is the case when there is a new longest candidate so we will need to add it
                    } else if (lengthsLongestEndingInI[index] == lengthsLongestEndingInI[indexUpTo] + 1) {
                        numberOfLongestEndingInI_DP[index] += numberOfLongestEndingInI_DP[indexUpTo];
                    }
                }
            }
            // find longestLength
            longestLen = Math.max(longestLen, lengthsLongestEndingInI[index]);
        }

        int numberOfLongestLen = 0;
        // given longestLength find the number of subsequence with longest length
        for (int i = 0; i < lengthsLongestEndingInI.length; ++i) {
            if (lengthsLongestEndingInI[i] == longestLen) {
                numberOfLongestLen += numberOfLongestEndingInI_DP[i];
            }
        }
        return numberOfLongestLen;
    }

//    Implementing Segment Trees is discussed in more detail here.
//    In this approach, we will attempt a variant of segment tree that doesn't
//    use lazy propagation.
//
//    First, let us call the "value" of an interval, the longest length of
//    an increasing subsequence, and the number of such subsequences in that interval.
//
//    Each node knows about the interval of nums values it is considering
//    [node.range_left, node.range_right], and it knows about node.val, which contains
//    information on the value of interval. Nodes also have node.left and node.right
//    children that represents the left and right half of the interval node considers.
//    These child nodes are created on demand as appropriate.
//
//    Now, query(node, key) will tell us the value of the interval specified
//    by node, except we'll exclude anything above key. When key is outside the
//    interval specified by node, we return the answer. Otherwise, we'll divide the
//    interval into two and query both intervals, then merge the result.
//
//    What does merge(v1, v2) do? Suppose two nodes specify adjacent intervals,
//    and have corresponding values v1 = node1.val, v2 = node2.val. What should
//    the aggregate value, v = merge(v1, v2) be? If there are longer subsequences
//    in one node, then v will just be that. If both nodes have longest subsequences
//    of equal length, then we should count subsequences in both nodes.
//    Note that we did not have to consider cases where larger subsequences were made,
//    since these were handled by insert.
//
//    What does insert(node, key, val) do? We repeatedly insert the key into the
//    correct half of the interval that node specifies (possibly a point), and after
//    such insertion this node's value could change, so we merge the values together again.
//
//    Finally, in our main algorithm, for each num in nums we query for the value
//    length, count of the interval below num, and we know it will lead to count
//    additional sequences of length length + 1. We then update our tree with that knowledge.
    // time O(NlogN)
    // space O(N)
    public static Value merge(Value v1, Value v2) {
        if (v1.length == v2.length) {
            if (v1.length == 0) return new Value(0, 1);
            return new Value(v1.length, v1.count + v2.count);
        }
        return v1.length > v2.length ? v1 : v2;
    }

    public static void insert(Node node, int key, Value val) {
        if (node.range_left == node.range_right) {
            node.val = merge(val, node.val);
            return;
        } else if (key <= node.getRangeMid()) {
            insert(node.getLeft(), key, val);
        } else {
            insert(node.getRight(), key, val);
        }
        node.val = merge(node.getLeft().val, node.getRight().val);
    }

    public static Value query(Node node, int key) {
        if (node.range_right <= key) return node.val;
        else if (node.range_left > key) return new Value(0, 1);
        else return merge(query(node.getLeft(), key), query(node.getRight(), key));
    }

    public static int findNumberOfLIS2(int[] nums) {
        if (nums.length == 0) return 0;
        int min = nums[0], max = nums[0];
        for (int num: nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        Node root = new Node(min, max);
        for (int num: nums) {
            Value v = query(root, num-1);
            insert(root, num, new Value(v.length + 1, v.count));
        }
        return root.val.count;
    }
}

class Node {
    int range_left, range_right;
    Node left, right;
    Value val;
    public Node(int start, int end) {
        range_left = start;
        range_right = end;
        left = null;
        right = null;
        val = new Value(0, 1);
    }
    public int getRangeMid() {
        return range_left + (range_right - range_left) / 2;
    }
    public Node getLeft() {
        if (left == null) left = new Node(range_left, getRangeMid());
        return left;
    }
    public Node getRight() {
        if (right == null) right = new Node(getRangeMid() + 1, range_right);
        return right;
    }
}

class Value {
    int length;
    int count;
    public Value(int len, int ct) {
        length = len;
        count = ct;
    }
}
