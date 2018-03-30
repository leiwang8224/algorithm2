package Tree;

/**
 * Created by leiwang on 3/14/18.
 */
public class ListNodeString {
    private String val;
    public ListNodeString left;
    public ListNodeString right;

    public ListNodeString(String inputStr) {
        val = inputStr;
    }

    public String getVal() {
        return val;
    }
}
