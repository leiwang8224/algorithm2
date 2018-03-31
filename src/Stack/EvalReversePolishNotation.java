package Stack;

import java.util.Stack;

/**
 * Created by leiwang on 3/31/18.
 */
public class EvalReversePolishNotation {
//    ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
//    ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
    public static void main(String[] args)
    {
        String[] expression = {"2", "1", "+", "3", "*"};
        evalExpression(expression);
    }

    private static int evalExpression(String[] expression) {
        int a,b;
        Stack<Integer> S = new Stack<Integer>();
        for (String s : expression) {
            if(s.equals("+")) {
                S.add(S.pop()+S.pop());
            }
            else if(s.equals("/")) {
                b = S.pop();
                a = S.pop();
                S.add(a / b);
            }
            else if(s.equals("*")) {
                S.add(S.pop() * S.pop());
            }
            else if(s.equals("-")) {
                b = S.pop();
                a = S.pop();
                S.add(a - b);
            }
            else {
                S.add(Integer.parseInt(s));
            }
        }
        return S.pop();
    }
}
