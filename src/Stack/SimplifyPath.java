package Stack;

import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Created by leiwang on 3/31/18.
 */
public class SimplifyPath {
    public static void main(String[] args) {
//        path = "/home/", => "/home"
//        path = "/a/./b/../../c/", => "/c"
        String path = "/home/";
        String path2 = "/a/./b/../../c/";
    }

    /**
     * The main idea is to push to the stack every valid file name (not in {"",".","…"}),
     * popping only if there’s smth to pop and we met “…”.
     * I don’t feel like the code below needs any additional comments.

     * @param path
     * @return
     */
    private static String simplifyPath(String path) {
        Deque<String> stack = new LinkedList<>();
        Set<String> skip = new HashSet<>(java.util.Arrays.asList("..",".",""));
        for (String dir : path.split("/")) {
            if (dir.equals("..") && !stack.isEmpty()) stack.pop();
            else if (!skip.contains(dir)) stack.push(dir);
        }
        String res = "";
        for (String dir : stack) res = "/" + dir + res;
        return res.isEmpty() ? "/" : res;
    }

}
