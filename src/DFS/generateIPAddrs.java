package DFS;

import java.util.ArrayList;
import java.util.Stack;

public class generateIPAddrs {
    public static void main(String[] args) {
//        ArrayList<String> result = generateIPAddrs("25525511135");
//        ArrayList<String> result2 = generateIPAddrsRecurse("25525511135");
        ArrayList<String> result3 = restoreIpAddresses("25525511135");
        ArrayList<String> result4 = restoreIpAddress("25525511135");
//        for (String word : result) {
//            System.out.println(word);
//        }
//
//        System.out.println(" start 2 ");
//        for (String word : result2) {
//            System.out.println(word);
//        }

        System.out.println(" start 3 ");
        for (String word : result3) {
            System.out.println(word);
        }

        System.out.println(" start 4 ");
        for (String word : result4) {
            System.out.println(word);
        }

    }

    private static ArrayList<String> generateIPAddrs(String input) {
        class IpLevelNode {
            public int ipSegment = 0;
            public String predecessor;
            public String successor;

            public IpLevelNode(int ipSegment, String ipToAppend, String pred, String successor) {
                this.ipSegment = ipSegment;
                this.successor = successor;
                if (ipSegment == 0) {
                    this.predecessor = ipToAppend;
                } else {
                    this.predecessor = pred + "." + ipToAppend;
                }
            }
        }

        ArrayList<String> out = new ArrayList<>();
        Stack<IpLevelNode> stack = new Stack<>();

        stack.push(new IpLevelNode(0, input.substring(0, 1), "", input.substring(1)));
        stack.push(new IpLevelNode(0, input.substring(0, 2), "", input.substring(2)));
        stack.push(new IpLevelNode(0, input.substring(0, 3), "", input.substring(3)));

        while (!stack.isEmpty()) {
            IpLevelNode node = stack.pop();
            //            System.out.println("current node ipSegment = " +
            //                               node.ipSegment +
            //                               " node successor = " +
            //                               node.successor +
            //                               " node pred = " +
            //                               node.predecessor);
            int curSegment = node.ipSegment;

            String pred = node.predecessor;
            String remain = node.successor;

            if (curSegment == 3 && remain.length() == 0) {
                out.add(node.predecessor);
                continue;
            }

            for (int index = 1; index <= 3; index++) {
                if (remain.length() < index) {
                    break;
                }
                String ipToAppend = remain.substring(0, index);
                String successor = remain.substring(index);

                if (ipToAppend.length() > 0) {
                    int numIpToAppend = Integer.parseInt(ipToAppend);

                    if (numIpToAppend <= 255) {
                        stack.push(new IpLevelNode(curSegment + 1, ipToAppend, pred, successor));
                    }
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

    //TODO: Still need work
    private static void generateIpAddrs2(String input,
                                         ArrayList<String> result,
                                         int level,
                                         String ipToAppend,
                                         String pred,
                                         String successor) {
        System.out.println("input = " +
                           input +
                           " ipSegment = " +
                           level +
                           " ipToAppend = " +
                           ipToAppend +
                           " pred = " +
                           pred +
                           " successor = " +
                           successor);
        if (level == 4 && successor.length() == 0) {
            result.add(pred);
            return;
        }

        String predecessor = level == 0 ? ipToAppend : pred + "." + ipToAppend;
        String remain = successor;

        for (int index = 1; index <= 3; index++) {
            if (remain.length() < index) {
                break;
            }
            if (ipToAppend.length() > 0) {
                int numIpToAppend = Integer.parseInt(ipToAppend);
                if (numIpToAppend <= 255) {
                    generateIpAddrs2(input,
                                     result,
                                     level + 1,
                                     input.substring(0, index),
                                     predecessor,
                                     remain.substring(index));
                }
            }
        }
    }

    private static ArrayList<String> restoreIpAddresses(String str) {
        ArrayList<String> result = new ArrayList<>();
        restoreIpRecurse(str, result, 0, "", 0);
        return result;
    }

    //    The 3 Keys To Backtracking: Our Choice: - We will take snippets of substrings 1-3 characters long
    //    Our Constraints:
    //    - We cannot have a value greater than 255 or less than 0 in any subsection.
    //    - We cannot have a leading 0 in any subsection Our Goal:
    //    - Get 4 valid subsections out of the raw string that we started with that will comprise the fully valid IP
    //    recomposition We can make progress through the string, slowly decoding it in all ways possible by taking a
    //    snippet, validating it, and then continuing on in the generation path. Since we will be using recursion,
    //    each recursive call will express all possible ways to arrange a certain subsection of the string that is...
    //    Until we reach the base case which is when we have all 4 segments filled out (and by default, our index pointer
    //    has progressed to the string's length).
    private static void restoreIpRecurse(String ip,
                                         ArrayList<String> result,
                                         int outputStrIndex,
                                         String restoredIp,
                                         int ipSegments) {
        if (ipSegments > 4) {
            return;
        }

        // at the end of the ip, 4th segment, output string index is equal to ip length
        if (ipSegments == 4 && outputStrIndex == ip.length()) {
            result.add(restoredIp);
            printArrayList(result);
        }
        System.out.println(getIndentation(ipSegments) +
                           "ip = " +
                           ip +
                           " index = " +
                           outputStrIndex +
                           " restoredIP = " +
                           restoredIp +
                           " ipSeg = " +
                           ipSegments);
//        printArrayList(result);

        // apply offset to the starting index to produce different permutations
        for (int indexOffset = 1; indexOffset < 4; indexOffset++) {
            System.out.println(getIndentation(ipSegments) +
                               "outputStrIndex = " +
                               outputStrIndex +
                               " indexOffset = " +
                               indexOffset +
                               " ip length = " +
                               ip.length());
            // constraint: output string index needs to be within the length of input string
            if (outputStrIndex + indexOffset > ip.length()) {
                break;
            }

            // take substring of the ip
            String substring = ip.substring(outputStrIndex, outputStrIndex + indexOffset);

            // need to filter out the cases where:
            // ip = 011.11.11.11 OR ip = 257.11.11.11
            if ((substring.startsWith("0") && substring.length() > 1) ||
                (indexOffset == 3 && Integer.parseInt(substring) >= 256)) {
                continue;
            }

//            if ((!substring.startsWith("0") && substring.length() <= 1) &&
//                (indexOffset != 3 && Integer.parseInt(substring) < 256)) {
                // recurse on index + offset and
                restoreIpRecurse(ip,
                                 result,
                                 outputStrIndex + indexOffset,
                                 // if ipSegments is 3, we are one segment away from end of the ip address,
                                 // so no period for the next segment (4)
                                 restoredIp + substring + (ipSegments == 3 ? "" : "."),
                                 ipSegments + 1);
//            }
        }
    }

    private static ArrayList<String> restoreIpAddress(String str) {
        ArrayList<String> result = new ArrayList<>();
        int[] path = new int[4];
        generateIpAddresses(result, str, 0, path, 0);
        return result;
    }

    private static void generateIpAddresses(ArrayList<String> result,
                                            String inputString,
                                            int index,
                                            int[] path,
                                            int segmentNumber) {
        if (segmentNumber == 4 && index == inputString.length()) {
            result.add(path[0] + "." + path[1] + "." + path[2] + "." + path[3]);
            return;
        } else if (segmentNumber == 4 || index == inputString.length()) {
            return;
        }

        for (int indexOffset = 1;
             indexOffset <= 3 && index + indexOffset <= inputString.length();
             indexOffset++) {
            int val = Integer.parseInt(inputString.substring(index, index + indexOffset));
            // range check
            if (val > 255 || indexOffset >= 2 && inputString.charAt(index) == '0') {
                break;
            }

            path[segmentNumber] = val;
            generateIpAddresses(result, inputString, index + indexOffset, path, segmentNumber + 1);
            //            path[segment] = -1; // for debugging?
        }
    }

    private static void printArrayList(ArrayList<String> list) {
        System.out.print("list: ");
        for (String word : list) {
            System.out.print(word + ",");
        }
        System.out.println();
    }

    private static java.lang.String getIndentation(int level) {
        StringBuilder str = new StringBuilder();
        for (int index = 0; index < level; index++) {
            str.append("    ");
        }
        return str.toString();
    }
}

