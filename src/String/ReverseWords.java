package String;

public class ReverseWords {
    public String reverseWords(String s) {
        char[] charArray = s.toCharArray();
        for (int curCharIdx = 0; curCharIdx < charArray.length; curCharIdx++) {
            if (charArray[curCharIdx] != ' ') {   // when i is a non-space
                int endOfWordPos = curCharIdx;
                while (endOfWordPos + 1 < charArray.length && charArray[endOfWordPos + 1] != ' ')
                {
                    endOfWordPos++;
                } // move j to the end of the word
                reverse(charArray, curCharIdx, endOfWordPos);
                curCharIdx = endOfWordPos;
            }
        }
        return new String(charArray);
    }

    private void reverse(char[] ca, int i, int j) {
        for (; i < j; i++, j--) {
            char tmp = ca[i];
            ca[i] = ca[j];
            ca[j] = tmp;
        }
    }
}
