package Math;

/**
 * Created by leiwang on 4/6/18.
 */
public class ConvertStrToInt {
    public static void main(String args[]) {
        System.out.println(convertStrToInt("1"));
    }

    private static int convertStrToInt(String str) {
        int len = str.length();
        int num = 0;
        for (int i = 0; i < len; i++) {
            num = num + (str.charAt(i) - '0') * (int)Math.pow(10,(len - i -1));
        }
        return num;
    }
}
