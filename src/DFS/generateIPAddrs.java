package DFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class generateIPAddrs {
    public static void main(String[] args) {
        ArrayList<String> result = generateIPAddrs("25525511135");
        ArrayList<String> result2 = generateIPAddrsRecurse("25525511135");

        for (String word : result) {
            System.out.println(word);
        }

        System.out.println(" start 2 ");
        for (String word : result2) {
            System.out.println(word);
        }
    }

    private static ArrayList<String> generateIPAddrs(String input) {
        class IpLevelNode {
            public int level = 0;
            public String predecessor;
            public String successor;

            public IpLevelNode(int level, String ipToAppend, String pred, String successor) {
                this.level = level;
                this.successor = successor;
                if (level == 0) this.predecessor = ipToAppend;
                else this.predecessor = pred + "." + ipToAppend;
            }
        }

        ArrayList<String> out = new ArrayList<>();
        Stack<IpLevelNode> stack = new Stack<>();

        stack.push(new IpLevelNode(0, input.substring(0, 1), "", input.substring(1)));
        stack.push(new IpLevelNode(0, input.substring(0, 2), "", input.substring(2)));
        stack.push(new IpLevelNode(0, input.substring(0, 3), "", input.substring(3)));

        while (!stack.isEmpty()) {
            IpLevelNode node = stack.pop();
            int curLevel = node.level;

            String pred = node.predecessor;
            String remain = node.successor;

            if (curLevel == 3 && remain.length() == 0) {
                out.add(node.predecessor);
                continue;
            }

            for (int index = 1; index <= 3; index++) {
                if (remain.length() < index) break;
                String ipToAppend = remain.substring(0, index);
                String successor = remain.substring(index);

                if (ipToAppend.length() > 0) {
                    int numIpToAppend = Integer.parseInt(ipToAppend);

                    if (numIpToAppend <= 255) stack.push(new IpLevelNode(curLevel + 1, ipToAppend, pred, successor));
                }
            }
        }
        return out;
    }

    private static ArrayList<String> generateIPAddrsRecurse(String input) {
        ArrayList<String> result = new ArrayList<>();
        generateIpAddrs2(input, result, 0, input.substring(0, 1), "", input.substring(1));
        return result;
    }

    private static void generateIpAddrs2(String input, ArrayList<String> result, int level, String ipToAppend, String pred, String successor) {
        if (level == 3 && successor.length() == 0) {
            result.add(pred);
            return;
        }


        String predecessor = level == 0 ? ipToAppend : pred + "." + ipToAppend;
        String remain = successor;

        for (int index = 1; index <= 3; index++) {
            if (remain.length() < index) break;
            if (ipToAppend.length() > 0) {
                int numIpToAppend = Integer.parseInt(ipToAppend);
                if (numIpToAppend <= 255) generateIpAddrs2(input, result, level+1,remain.substring(0,index),predecessor,remain.substring(index));
            }
        }


    }
}

