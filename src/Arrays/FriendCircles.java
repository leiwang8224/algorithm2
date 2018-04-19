package Arrays;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by leiwang on 4/14/18.
 */
public class FriendCircles {
//    There are N students in a class. Some of them are friends, while some are not.
//    Their friendship is transitive in nature. For example, if A is a direct friend of B,
//    and B is a direct friend of C, then A is an indirect friend of C. And we defined a
//    friend circle is a group of students who are direct or indirect friends.
//
//    Given a N*N matrix M representing the friend relationship between students in the class.
//    If M[i][j] = 1, then the ith and jth students are direct friends with each other, otherwise
//    not. And you have to output the total number of friend circles among all the students.
//    Input:
//            [[1,1,0],
//            [1,1,0],
//            [0,0,1]]
//    Output: 2
//    Explanation:The 0th and 1st students are direct friends, so they are in a friend circle.
//            The 2nd student himself is in a friend circle. So return 2.
    public static void main(String args[]) {
        int[][] nums = new int[][] {{1,1,0}, {1,1,0}, {0,0,1}};

        System.out.println("Find circle number = " + findCircleNum(nums));
        System.out.println("Find circle number 2 = " + findCircleNum2(nums));

    }

    /**
     * DFS method
     * @param M
     * @return
     */
    private static int findCircleNum(int[][] M) {
        int[] visited = new int[M.length];
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (visited[i] == 0) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }

    private static void dfs(int[][] M, int[] visited, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;
                dfs(M, visited, j);
            }
        }
    }

    /**
     * BFS method
     * @param M
     * @return
     */
    public static int findCircleNum2(int[][] M) {
        int count = 0;
        for (int i=0; i<M.length; i++)
            if (M[i][i] == 1) {
                count++;
                BFS(i, M);
            }
        return count;
    }

    public static void BFS(int student, int[][] M) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(student);
        while (queue.size() > 0) {
            int queueSize = queue.size();
            for (int i=0;i<queueSize;i++) {
                int j = queue.poll();
                M[j][j] = 2; // marks as visited
                for (int k=0;k<M[0].length;k++)
                    if (M[j][k] == 1 && M[k][k] == 1) queue.add(k);
            }
        }
    }
}
