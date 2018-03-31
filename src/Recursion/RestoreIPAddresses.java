package Recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leiwang on 3/29/18.
 */
public class RestoreIPAddresses {
    public static void main(String args[]) {
        String givenString = "25525511135";
        findAllIP(givenString);
    }

//    3-loop divides the string s into 4 substring: s1, s2, s3, s4. Check if each substring
//    is valid.
//    In isValid:
//    strings whose length greater than 3 or equals to 0 is not valid;
//    or if the string’s length is longer than 1 and the first letter is ‘0’ then it’s invalid;
//    or the string whose integer representation greater than 255 is invalid.
    private static List<String> findAllIP(String givenString) {
        List<String> res = new ArrayList<String>();
        int len = givenString.length();
        for(int i = 1; i<4 && i<len-2; i++){
            for(int j = i+1; j<i+4 && j<len-1; j++){
                for(int k = j+1; k<j+4 && k<len; k++){
                    String s1 = givenString.substring(0,i),     // 1 to 4
                            s2 = givenString.substring(i,j),    //
                            s3 = givenString.substring(j,k),
                            s4 = givenString.substring(k,len);
                    if(isValid(s1) && isValid(s2) && isValid(s3) && isValid(s4)){
                        res.add(s1+"."+s2+"."+s3+"."+s4);
                    }
                }
            }
        }
        return res;
    }

    /**
     * DFS method
     * @param s
     * @return
     */
    private List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        doRestore(result, "", s, 0);
        return result;
    }

    private void doRestore(List<String> result, String path, String s, int k) {
        if (s.isEmpty() || k == 4) {
            if (s.isEmpty() && k == 4)
                result.add(path.substring(1));
            return;
        }
        for (int i = 1; i <= (s.charAt(0) == '0' ? 1 : 3) && i <= s.length(); i++) {
            // Avoid leading 0
            String part = s.substring(0, i);
            if (Integer.valueOf(part) <= 255)
                doRestore(result, path + "." + part, s.substring(i), k + 1);
        }
    }

    private static boolean isValid(String s) {
        if(s.length()>3 ||
                s.length()==0 ||
                (s.charAt(0)=='0' && s.length()>1) ||
                Integer.parseInt(s)>255)
            return false;
        return true;
    }


    private List<String> restoreIpAddresses2nd(String s) {
        List<String> result = new ArrayList<String>();
        if(s.length() < 4 || s.length() > 12)
            return result;
        restoreIpAddresses2(s, 0, 4, result, new StringBuilder());
        return result;
    }
    public void restoreIpAddresses2(String s, int index, int set, List<String>result, StringBuilder sb){
        if(index > s.length() || s.length() - index < set ||s.length() - index > set*3){
            return;
        }else if(index == s.length()){
            sb.setLength(sb.length()-1);
            result.add(sb.toString());
            return;
        }
        int len = sb.length();
        for(int i = 1; i <=3 ; i++){
            if(index+i <= s.length() &&
                    (i!=3 || Integer.parseInt(s.substring(index, index+i))< 256) &&
                    (i==1 || Integer.parseInt(s.substring(index, index+1))!= 0))
            {
                sb.append(s.substring(index, index+i)+".");
                restoreIpAddresses2(s, index+i, set-1, result, sb);
                sb.setLength(len);
            }
        }
    }
}
