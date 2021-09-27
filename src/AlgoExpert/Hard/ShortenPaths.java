package AlgoExpert.Hard;
import com.sun.xml.internal.ws.wsdl.writer.document.StartWithExtensionsType;

import java.util.*;
import java.util.stream.Collectors;
public class ShortenPaths {

    public String simplifyPathLeetCodeNoPassAlgoExpert(String path) {
        Deque<String> stack = new LinkedList<>();
        Set<String> stringsToSkip = new HashSet<>(Arrays.asList("..",".",""));
        for (String dirSection : path.split("/")) {
            if (dirSection.equals("..") && !stack.isEmpty())
                stack.pop();
            else if (!stringsToSkip.contains(dirSection))
                stack.push(dirSection);
        }
        String res = "";
        for (String dir : stack)
            res = "/" + dir + res;
        return res.isEmpty() ? "/" : res;
    }

    // O(n) time | O(n) space
    /**
     * Use ArrayList to leverage split and join Strings API
     * - convert string to array, then convert to list
     * - Remove empty strings and "." using stream API
     * - Determine if absolute path
     * - Iterate forward and put string into stack:
     * - if we see ".."
     *   - if the previous string is ".." or the string is empty (".." in beginning),
     *   put ".." in stack (../../.. or ../)
     *   - else if the last item on stack is not empty string, remove the
     *   last item on stack (pop to move up one dir)
     * - else add token to stack
     *
     * - if after split there is only an empty string in stack then return "/" (root dir)
     */
    private static String shortenPath(String path) {
        boolean isAbsolutePath = path.charAt(0) == '/';
        // split up string based on separator '/' and convert from array to list
        List<String> tokensList = Arrays.asList(path.split("/"));
        List<String> filteredTokens = // remove empty strings and "."
                tokensList.stream().
                        filter(token -> isImportantToken(token)).
                        collect(Collectors.toList()); // need to collect after filter
        List<String> stack = new ArrayList<>();
        if (isAbsolutePath) stack.add("");
        // if it's absolute path we want to keep the "/" in front (empty string)
        // so that when joining the string we get the "/" in front
        for (String token : filteredTokens) {
            if(token.equals("..")) {
                // essentially if starts with ".." or last item on stack is ".." (consecutive /)
                if (stack.size() == 0 || stack.get(stack.size() - 1).equals("..")) {
                    stack.add(token); // if relative path then add ".." to stack
                } else if (!stack.get(stack.size() - 1).equals("")) {
                    // not dealing with root directory (beginning of string is not "")
                    // pop item before ".." to move up a directory
                    // note we should not remove "" since it's an indicator for root dir
                    stack.remove(stack.size() - 1);
                }
            } else { // not ".." so add to stack
                stack.add(token);
            }
        }

        // only "" left in the stack then just return root dir
        if (stack.size() == 1 && stack.get(0).equals("")) return "/";
        return String.join("/", stack);
    }

    private static boolean isImportantToken(String token) {
        // getting rid of empty strings and . which represent cur dir (not useful)
        return token.length() > 0 && !token.equals(".");
    }


}
